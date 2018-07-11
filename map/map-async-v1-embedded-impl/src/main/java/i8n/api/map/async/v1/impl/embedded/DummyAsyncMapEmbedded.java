package i8n.api.map.async.v1.impl.embedded;

import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.TestPublisher;
import org.kohsuke.MetaInfServices;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyAsyncMapEmbedded<K, V> implements DummyAsyncMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public CompletionStage<V> get(K key) {
      queue.offer("[map-async-v1-embedded] GET key=" + key);
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public Publisher<V> getMany(Publisher<K> keys) {
      keys.subscribe(new Subscriber<K>() {
         @Override public void onSubscribe(Subscription s) {}

         @Override
         public void onNext(K k) {
            queue.offer("[map-async-v1-embedded] GET_MANY key=" + k);
         }

         @Override public void onError(Throwable t) {}

         @Override
         public void onComplete() {
            queue.offer("[map-async-v1-embedded] GET_MANY complete");
         }
      });

      return TestPublisher.fromArray();
   }

   public CompletionStage<Void> put(K key, V value) {
      queue.offer("[map-async-v1-embedded] PUT key=" + key + ",value=" + value);
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public CompletionStage<Void> putMany(Publisher<Map.Entry<K, V>> pairs) {
      pairs.subscribe(new Subscriber<Map.Entry<K, V>>() {
         @Override
         public void onSubscribe(Subscription s) {}

         @Override
         public void onNext(Map.Entry<K, V> entry) {
            queue.offer("[map-async-v1-embedded] PUT_MANY entry=" + entry);
         }

         @Override public void onError(Throwable t) {}

         @Override
         public void onComplete() {
            queue.offer("[map-async-v1-embedded] PUT_MANY complete");
         }
      });

      return CompletableFuture.completedFuture(null);
   }

   @Override
   public String getName() {
      return "map-async-v1-embedded";
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
