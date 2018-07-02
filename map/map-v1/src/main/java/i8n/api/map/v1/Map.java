package i8n.api.map.v1;

public interface Map<K, V> {

   V get(K key);

   void put(K key, V value);

}
