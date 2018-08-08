package i8n.api.search.v1.impl.embedded;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.ApiMap;
import i8n.api.map.v2.DummyMap;
import i8n.api.search.v1.DummySearch;
import org.kohsuke.MetaInfServices;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class DummySearchEmbedded implements DummySearch {

   private final Queue<String> queue = new LinkedList<>();

   // Dummy assumption that the underlying structure is a map,
   // but could be something else (counter, multi map...etc)
   private final DummyMap<?, ?> map = Infinispan.get(
      ApiMap.instance(), new Object()
   );

   private String name = "search-v1-embedded";

   @Override
   public String getName() {
      return name;
   }

   @Override
   public DummyQuery createQuery(String queryString) {
      queue.offer(String.format("[%s] CREATE_QUERY query=%s", name, queryString));
      return new DummyQueryEmbedded(queryString);
   }

   @Override
   public <T> T unwrap() {
      return (T) map;
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

   private final class DummyQueryEmbedded implements DummyQuery {

      final String queryString;

      private DummyQueryEmbedded(String queryString) {
         this.queryString = queryString;
      }

      @Override
      public <T> List<T> execute() {
         queue.offer(String.format("[%s] EXEC_QUERY query=%s", name, queryString));
         return (List<T>) map.values().collect(Collectors.toList());
      }

   }

   @MetaInfServices
   public final static class FactoryImpl implements DummySearch.Factory {

      @Override
      public DummySearch apply(Object o) {
         return new DummySearchEmbedded();
      }

   }

}
