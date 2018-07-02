package i8n.api.map.v1.impl.embedded;

import i8n.api.map.v1.Map;
import org.kohsuke.MetaInfServices;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class MapEmbedded<K, V> implements Map<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public V get(K key) {
      queue.offer("[map-embedded] GET key=" + key);
      return null;
   }

   public void put(K key, V value) {
      queue.offer("[map-embedded] PUT key=" + key + ",value=" + value);
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
