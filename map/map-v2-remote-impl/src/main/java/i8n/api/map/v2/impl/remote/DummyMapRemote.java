package i8n.api.map.v2.impl.remote;

import i8n.api.map.v2.DummyMap;
import org.kohsuke.MetaInfServices;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummyMapRemote<K, V> implements DummyMap<K, V> {

   private final Queue<String> queue = new LinkedList<>();
   private final Map<K, V> data = new HashMap<>();

   public V get(K key) {
      queue.offer("[map-v2-remote] GET key=" + key);
      return data.get(key);
   }

   public void put(K key, V value) {
      queue.offer("[map-v2-remote] PUT key=" + key + ",value=" + value);
      data.put(key, value);
   }

   @Override
   public V getAndPut(K key, V value) {
      queue.offer("[map-v2-remote] GET_PUT key=" + key + ",value=" + value);
      return data.put(key, value);
   }

   @Override
   public Stream<V> values() {
      queue.offer("[map-v2-remote] VALUES");
      return data.values().stream();
   }

   @Override
   public String getName() {
      return "map-v2-remote";
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

   @MetaInfServices
   public final static class FactoryImpl<K, V> implements DummyMap.Factory<K, V> {

      @Override
      public DummyMap<K, V> apply(Object o) {
         return new DummyMapRemote<>();
      }

   }

}
