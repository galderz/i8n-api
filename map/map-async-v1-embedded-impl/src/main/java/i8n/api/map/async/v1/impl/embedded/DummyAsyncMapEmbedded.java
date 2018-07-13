package i8n.api.map.async.v1.impl.embedded;

import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.TestPublisher;
import org.kohsuke.MetaInfServices;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyAsyncMapEmbedded<K, V> implements DummyAsyncMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();
   private final Map<K, V> data = new HashMap<>();

   final String name;

   // For service loader
   public DummyAsyncMapEmbedded() {
      this.name = "map-async-v1-embedded";
   }

   // For delegate use
   public DummyAsyncMapEmbedded(String name) {
      this.name = name;
   }

   public CompletionStage<V> get(K key) {
      queue.offer(String.format("[%s] GET key=%s", name, key));
      return CompletableFuture.completedFuture(data.get(key));
   }

   @Override
   public Publisher<V> getMany(Publisher<K> keys) {
      List<V> vs = new ArrayList<>();

      keys.subscribe(new Subscriber<K>() {
         @Override public void onSubscribe(Subscription s) {}

         @Override
         public void onNext(K k) {
            queue.offer(String.format("[%s] GET_MANY key=%s", name, k));
            vs.add(data.get(k));
         }

         @Override public void onError(Throwable t) {}

         @Override
         public void onComplete() {
            queue.offer(String.format("[%s] GET_MANY complete", name));
         }
      });

      final V[] vsArray = (V[]) vs.toArray();
      return TestPublisher.fromArray(vsArray);
   }

   public CompletionStage<Void> put(K key, V value) {
      queue.offer(String.format("[%s] PUT key=%s,value=%s", name, key, value));
      data.put(key, value);
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public CompletionStage<Void> putMany(Publisher<Map.Entry<K, V>> pairs) {
      pairs.subscribe(new Subscriber<Map.Entry<K, V>>() {
         @Override
         public void onSubscribe(Subscription s) {}

         @Override
         public void onNext(Map.Entry<K, V> entry) {
            queue.offer(String.format("[%s] PUT_MANY entry=%s", name, entry));
            data.put(entry.getKey(), entry.getValue());
         }

         @Override public void onError(Throwable t) {}

         @Override
         public void onComplete() {
            queue.offer(String.format("[%s] PUT_MANY complete", name));
         }
      });

      return CompletableFuture.completedFuture(null);
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
