package i8n.api.map.v2;

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
    * Name of map
    */
   String getName();

}
