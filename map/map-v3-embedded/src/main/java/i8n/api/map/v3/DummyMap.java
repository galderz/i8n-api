package i8n.api.map.v3;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface DummyMap<K, V> {

   V get(K key);

   /**
    * No previous value returned
    */
   void put(K key, V value);

   /**
    * For users that want previous value
    */
   V getAndPut(K key, V value);


   /**
    * Embedded-only lambda-based operation
    */
   V compute(
      K key
      , BiFunction<? super K, ? super V, ? extends V> remappingFunction
   );

   /**
    * Factory
    */
   interface Factory<K, V> extends Function<Object, DummyMap<K, V>> {}

}
