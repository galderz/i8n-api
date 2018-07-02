package i8n.api.map.v1.impl.remote;

import i8n.api.map.v1.Map;
import org.kohsuke.MetaInfServices;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class MapRemote<K, V> implements Map<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public V get(K key) {
      queue.offer("[map-remote] GET key=" + key);
      return null;
   }

   public void put(K key, V value) {
      queue.offer("[map-remote] PUT key=" + key + ",value=" + value);
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
