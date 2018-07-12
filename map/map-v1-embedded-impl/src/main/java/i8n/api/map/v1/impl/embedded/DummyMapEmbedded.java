package i8n.api.map.v1.impl.embedded;

import i8n.api.map.v1.DummyMap;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyMapEmbedded<K, V> implements DummyMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();
   private final Map<K, V> data = new HashMap<>();

   public V get(K key) {
      queue.offer("[map-v1-embedded] GET key=" + key);
      return data.get(key);
   }

   public V put(K key, V value) {
      queue.offer("[map-v1-embedded] PUT key=" + key + ",value=" + value);
      return data.put(key, value);
   }

   @Override
   public String getName() {
      return "map-v1-embedded";
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
