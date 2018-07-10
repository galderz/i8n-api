package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.async.v1.ApiMap;
import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.JoiningCompletionStage;
import org.junit.Test;

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

}
