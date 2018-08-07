package i8n.api.search.async.v1.impl.remote;

import i8n.api.search.async.v1.DummyAsyncSearch;
import i8n.api.search.async.v1.TestPublisher;
import org.kohsuke.MetaInfServices;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

@MetaInfServices
public class DummyAsyncSearchRemote implements DummyAsyncSearch {

   private final Queue<String> queue = new LinkedList<>();
   // TODO search on what? map? multimap? counter?

   final String name;

   // For service loader
   @SuppressWarnings("unused")
   public DummyAsyncSearchRemote() {
      this.name = "search-async-v1-remote";
   }

   // For delegate use
   public DummyAsyncSearchRemote(String name) {
      this.name = name;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public DummyAsyncQuery createQuery(String queryString) {
      queue.offer(String.format("[%s] CREATE_QUERY query=%s", name, queryString));
      return new DummyQueryEmbedded(queryString);
   }

   private final class DummyQueryEmbedded implements DummyAsyncQuery {

      final String queryString;

      private DummyQueryEmbedded(String queryString) {
         this.queryString = queryString;
      }

      @Override
      public <T> Publisher<T> execute() {
         queue.offer(String.format("[%s] EXEC_QUERY query=%s", name, queryString));
         return Subscriber::onComplete;
      }

   }

   @Override
   public String toString() {
      return queue.stream().collect(Collectors.joining("\n"));
   }

}
