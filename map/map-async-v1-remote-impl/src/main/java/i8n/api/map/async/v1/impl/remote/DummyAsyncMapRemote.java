package i8n.api.map.async.v1.impl.embedded;

import i8n.api.map.async.v1.DummyAsyncMap;
import org.kohsuke.MetaInfServices;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyAsyncMapRemote<K, V> implements DummyAsyncMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public CompletionStage<V> get(K key) {
      queue.offer("[map-async-v1-remote] GET key=" + key);
      return CompletableFuture.completedFuture(null);
   }

   public CompletionStage<Void> put(K key, V value) {
      queue.offer("[map-async-v1-remote] PUT key=" + key + ",value=" + value);
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
