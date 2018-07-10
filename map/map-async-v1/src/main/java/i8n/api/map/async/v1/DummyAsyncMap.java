package i8n.api.map.async.v1;

import java.util.concurrent.CompletionStage;

public interface DummyAsyncMap<K, V> {

   CompletionStage<V> get(K key);

   CompletionStage<Void> put(K key, V value);

}
