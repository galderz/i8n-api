package i8n.api.map.v1.impl.embedded;

import i8n.api.map.v1.Map;
import org.kohsuke.MetaInfServices;

import java.util.LinkedList;
import java.util.Queue;

@MetaInfServices
public class MapEmbedded<K, V> implements Map<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public V get(K key) {
      queue.offer("GET key=" + key);
      return null;
   }

   public void put(K key, V value) {
      queue.offer("PUT key=" + key + ",value=" + value);
   }

   @Override
   public String toString() {
      return queue.toString();
   }

}
