package i8n.api.map.v1;

import java.util.function.Function;

public interface DummyMap<K, V> {

   V get(K key);

   /**
    * Returns previous value
    */
   V put(K key, V value);

   /**
    * Name of map
    */
   String getName();

   /**
    * Factory
    */
   interface Factory<K, V> extends Function<Object, DummyMap<K, V>> {}

}
