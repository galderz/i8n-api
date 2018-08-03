package i8n.api.search.v1.impl.embedded;

import i8n.api.search.v1.DummySearch;
import org.kohsuke.MetaInfServices;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummySearchEmbedded implements DummySearch {

   private final Queue<String> queue = new LinkedList<>();
   // TODO search on what? map? multimap? counter?

   @Override
   public String getName() {
      return "search-v1-embedded";
   }

   @Override
   public DummyQuery createQuery(String queryString) {
      queue.offer("[search-v1-embedded] CREATE_QUERY query=" + queryString);
      return new DummyQueryEmbedded(queryString);
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
         queue.offer("[search-v1-embedded] EXEC_QUERY query=" + queryString);
         return Collections.emptyList();
      }

   }

}
