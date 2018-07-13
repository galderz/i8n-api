package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.rx.v1.ApiRxMap;
import i8n.api.map.rx.v1.DummyRxMap;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public class AnyRxMapV1Test {

   @Test
   public void test000() {
      final DummyRxMap<Integer, String> map = Infinispan.get(
         ApiRxMap.instance(), new Object()
      );

      // Execute put...
      final Completable put1 = map.put(1, "Bulbasaur");

      // Then execute a another put...
      final Completable put2 = put1.andThen(map.put(4, "Charmander"));

      // Then execute a get...
      final Maybe<String> get1 = put2.andThen(map.get(1));

      await("Bulbasaur", get1);

      assertEquals(
         "[" + map.getName() + "] PUT key=1,value=Bulbasaur\n" +
         "[" + map.getName() + "] PUT key=4,value=Charmander\n" +
         "[" + map.getName() + "] GET key=1"
         , map.toString()
      );
   }

   @Test
   public void test001() {
      final DummyRxMap<Integer, String> map = Infinispan.get(
         ApiRxMap.instance(), new Object()
      );

      final Flowable<Map.Entry<Integer, String>> values =
         Flowable.fromArray(
            new AbstractMap.SimpleEntry<>(7, "Squirtle")
            , new AbstractMap.SimpleEntry<>(8, "Wartortle")
            , new AbstractMap.SimpleEntry<>(9, "Blastoise")
         );

      final Completable putMany = map.putMany(values);

      final Flowable<String> getMany =
         putMany.andThen(
            map.getMany(Flowable.fromArray(7, 8, 9))
         );

      await(Arrays.asList("Squirtle", "Wartortle", "Blastoise"), getMany);

      assertEquals(
         "[" + map.getName() + "] PUT_MANY entry=7=Squirtle\n" +
            "[" + map.getName() + "] PUT_MANY entry=8=Wartortle\n" +
            "[" + map.getName() + "] PUT_MANY entry=9=Blastoise\n" +
            "[" + map.getName() + "] PUT_MANY complete\n" +
            "[" + map.getName() + "] GET_MANY key=7\n" +
            "[" + map.getName() + "] GET_MANY key=8\n" +
            "[" + map.getName() + "] GET_MANY key=9\n" +
            "[" + map.getName() + "] GET_MANY complete"
         , map.toString()
      );
   }

   public static <T> void await(T expected, Maybe<T> m) {
      TestObserver<T> observer = new TestObserver<>();
      m.subscribe(observer);

      observer.awaitTerminalEvent(2, SECONDS);
      observer.assertNoErrors();
      observer.assertComplete();
      observer.assertValueCount(1);
      observer.assertValue(expected);
   }

   public static <T> void await(Collection<? extends T> expected, Flowable<T> f) {
      TestSubscriber<T> observer = new TestSubscriber<>();
      f.subscribe(observer);

      observer.awaitTerminalEvent(2, SECONDS);
      observer.assertNoErrors();
      observer.assertComplete();
      observer.assertValueCount(expected.size());
      observer.assertValueSet(expected);
   }

}
