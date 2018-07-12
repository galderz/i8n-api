package i8n.api.map.v3.impl.embedded;

import i8n.api.map.v3.DummyMap;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyMapEmbedded<K, V> implements DummyMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();
   private final Map<K, V> data = new HashMap<>();

   public V get(K key) {
      queue.offer("[map-v3-embedded] GET key=" + key);
      return data.get(key);
   }

   public void put(K key, V value) {
      queue.offer("[map-v3-embedded] PUT key=" + key + ",value=" + value);
      data.put(key, value);
   }

   @Override
   public V getAndPut(K key, V value) {
      queue.offer("[map-v3-embedded] GET_PUT key=" + key + ",value=" + value);
      return data.put(key, value);
   }

   @Override
   public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      queue.offer("[map-v3-embedded] COMPUTE key=" + key);
      return data.compute(key, remappingFunction);
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
