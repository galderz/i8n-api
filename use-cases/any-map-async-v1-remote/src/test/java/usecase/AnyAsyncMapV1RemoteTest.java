package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.async.v1.ApiMap;
import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.JoiningCompletionStage;
import i8n.api.map.async.v1.TestPublisher;
import org.junit.Test;
import org.reactivestreams.Publisher;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class AnyAsyncMapV1RemoteTest {

   @Test
   public void test000() {
      final DummyAsyncMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      // Execute put...
      final CompletionStage<Void> put1 =
         map.put(1, "Bulbasaur");

      // Then execute a another put...
      final CompletionStage<Void> put2 =
         put1.thenCompose(x -> map.put(4, "Charmander"));

      // Then execute a get...
      final CompletionStage<String> get1 =
         put2.thenCompose(x -> map.get(1));

      final CompletionStage<Void> events = get1.thenAccept(x -> {
         assertEquals(
         "[map-async-v1-remote] PUT key=1,value=Bulbasaur\n" +
            "[map-async-v1-remote] PUT key=4,value=Charmander\n" +
            "[map-async-v1-remote] GET key=1"
            , map.toString()
         );
      });

      final JoiningCompletionStage assertion =
         new JoiningCompletionStage(events);
      assertion.join(2, TimeUnit.SECONDS);
   }

   @Test
   public void test001() {
      final DummyAsyncMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      final Publisher<Map.Entry<Integer, String>> values = TestPublisher
         .fromArray(
            new AbstractMap.SimpleEntry<>(7, "Squirtle")
            , new AbstractMap.SimpleEntry<>(8, "Wartortle")
            , new AbstractMap.SimpleEntry<>(9, "Blastoise")
         );

      final CompletionStage<Void> putMany = map.putMany(values);

      final CompletionStage<Void> getMany = putMany.thenAccept(x -> {
         final Publisher<Integer> keys = TestPublisher
            .fromArray(7, 8, 9);

         map.getMany(keys);
      });

      final CompletionStage<Void> events = getMany.thenAccept(x -> {
         assertEquals(
            "[map-async-v1-remote] PUT_MANY entry=7=Squirtle\n" +
               "[map-async-v1-remote] PUT_MANY entry=8=Wartortle\n" +
               "[map-async-v1-remote] PUT_MANY entry=9=Blastoise\n" +
               "[map-async-v1-remote] PUT_MANY complete\n" +
               "[map-async-v1-remote] GET_MANY key=7\n" +
               "[map-async-v1-remote] GET_MANY key=8\n" +
               "[map-async-v1-remote] GET_MANY key=9\n" +
               "[map-async-v1-remote] GET_MANY complete"
            , map.toString()
         );
      });

      final JoiningCompletionStage assertion =
         new JoiningCompletionStage(events);
      assertion.join(2, TimeUnit.SECONDS);
   }

}
