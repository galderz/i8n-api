package i8n.api.map.async.v1.impl.remote;

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
public class DummyAsyncMapRemote<K, V> implements DummyAsyncMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();
   private final Map<K, V> data = new HashMap<>();

   final String name;

   // For service loader
   @SuppressWarnings("unused")
   public DummyAsyncMapRemote() {
      this.name = "map-async-v1-remote";
   }

   // For delegate use
   public DummyAsyncMapRemote(String name) {
      this.name = name;
   }

   public CompletionStage<V> get(K key) {
      queue.offer(String.format("[%s] GET key=%s", name, key));
      return CompletableFuture.completedFuture(data.get(key));
   }

   @Override
   public Publisher<V> getMany(Publisher<K> keys) {
      return subscriber -> {
         keys.subscribe(new Subscriber<K>() {
            @Override
            public void onSubscribe(Subscription s) {
               s.request(Long.MAX_VALUE);
               subscriber.onSubscribe(s);
            }

            @Override
            public void onNext(K k) {
               queue.offer(String.format("[%s] GET_MANY key=%s", name, k));
               final V v = data.get(k);
               subscriber.onNext(v);
            }

            @Override
            public void onError(Throwable t) {
               subscriber.onError(t);
            }

            @Override
            public void onComplete() {
               queue.offer(String.format("[%s] GET_MANY complete", name));
               subscriber.onComplete();
            }
         });
      };
   }

   public CompletionStage<Void> put(K key, V value) {
      queue.offer(String.format("[%s] PUT key=%s,value=%s", name, key, value));
      data.put(key, value);
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public CompletionStage<Void> putMany(Publisher<Map.Entry<K, V>> pairs) {
      CompletableFuture<Void> completed = new CompletableFuture<>();

      pairs.subscribe(new Subscriber<Map.Entry<K, V>>() {
         @Override
         public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
         }

         @Override
         public void onNext(Map.Entry<K, V> entry) {
            queue.offer(String.format("[%s] PUT_MANY entry=%s", name, entry));
            data.put(entry.getKey(), entry.getValue());
         }

         @Override public void onError(Throwable t) {}

         @Override
         public void onComplete() {
            queue.offer(String.format("[%s] PUT_MANY complete", name));
            completed.complete(null);
         }
      });

      return completed;
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
