package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.async.v1.ApiMap;
import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.JoiningCompletionStage;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class AnyAsyncMapV1EmbeddedTest {

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
         "[map-async-v1-embedded] PUT key=1,value=Bulbasaur\n" +
            "[map-async-v1-embedded] PUT key=4,value=Charmander\n" +
            "[map-async-v1-embedded] GET key=1"
            , map.toString()
         );
      });

      final JoiningCompletionStage assertion =
         new JoiningCompletionStage(events);
      assertion.join(2, TimeUnit.SECONDS);
   }

}
