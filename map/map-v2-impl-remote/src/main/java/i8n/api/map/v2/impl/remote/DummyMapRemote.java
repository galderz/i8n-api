package i8n.api.map.v2.impl.remote;

import i8n.api.map.v2.DummyMap;
import org.kohsuke.MetaInfServices;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyMapRemote<K, V> implements DummyMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();

   public V get(K key) {
      queue.offer("[map-v2-remote] GET key=" + key);
      return null;
   }

   public void put(K key, V value) {
      queue.offer("[map-v2-remote] PUT key=" + key + ",value=" + value);
   }

   @Override
   public V getAndPut(K key, V value) {
      queue.offer("[map-v2-remote] GET_PUT key=" + key + ",value=" + value);
      return null;
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
