package i8n.api.map.v1;

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

}
