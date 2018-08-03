package i8n.api.map.v2;

import java.util.stream.Stream;

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
    * Stream of values
    */
   Stream<V> values();

   /**
    * Name of map
    */
   String getName();

}
