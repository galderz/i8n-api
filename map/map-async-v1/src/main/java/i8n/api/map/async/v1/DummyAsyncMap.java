package i8n.api.map.async.v1;

import org.reactivestreams.Publisher;

import java.util.Map;
import java.util.concurrent.CompletionStage;

public interface DummyAsyncMap<K, V> {

   CompletionStage<V> get(K key);

   Publisher<V> getMany(Publisher<K> keys);

   CompletionStage<Void> put(K key, V value);

   CompletionStage<Void> putMany(Publisher<Map.Entry<K, V>> pairs);

   Publisher<V> values();

   /**
    * Name of map
    */
   String getName();

}
