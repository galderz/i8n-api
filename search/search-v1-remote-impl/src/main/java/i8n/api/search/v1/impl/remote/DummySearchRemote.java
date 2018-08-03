package i8n.api.search.v1.impl.remote;

import i8n.api.search.v1.DummySearch;
import org.kohsuke.MetaInfServices;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummySearchRemote<K, V> implements DummySearch {

   private final Queue<String> queue = new LinkedList<>();

   @Override
   public String getName() {
      return "search-v1-remote";
   }

   @Override
   public DummyQuery createQuery(String queryString) {
      queue.offer("[search-v1-remote] CREATE_QUERY query=" + queryString);
      return new DummyQueryRemote(queryString);
   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

   private final class DummyQueryRemote implements DummyQuery {

      final String queryString;

      private DummyQueryRemote(String queryString) {
         this.queryString = queryString;
      }

      @Override
      public <T> List<T> execute() {
         queue.offer("[search-v1-remote] EXEC_QUERY query=" + queryString);
         return Collections.emptyList();
      }

   }

}
