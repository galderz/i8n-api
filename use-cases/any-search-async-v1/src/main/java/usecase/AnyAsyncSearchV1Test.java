package usecase;

import i8n.api.common.Infinispan;
import i8n.api.search.async.v1.ApiAsyncSearch;
import i8n.api.search.async.v1.DummyAsyncSearch;
import i8n.api.search.async.v1.DummyAsyncSearch.DummyAsyncQuery;
import i8n.api.search.async.v1.TestSubscriber;
import org.junit.Test;
import org.reactivestreams.Publisher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyAsyncSearchV1Test {

   @Test
   public void test000() {
      final DummyAsyncSearch search = Infinispan.get(
         ApiAsyncSearch.instance(), new Object()
      );

      final DummyAsyncQuery query = search.createQuery("FROM Pokemon p where p.shiny == TRUE");
      final Publisher<Object> results = query.execute();

      TestSubscriber<Object> observer = new TestSubscriber<>();
      results.subscribe(observer);

      observer.awaitTerminalEvent(2, SECONDS);
      observer.assertNoErrors();
      observer.assertComplete();
      observer.assertNoValues();

      assertEquals(
      "[" + search.getName() + "] CREATE_QUERY query=FROM Pokemon p where p.shiny == TRUE\n" +
         "[" + search.getName() + "] EXEC_QUERY query=FROM Pokemon p where p.shiny == TRUE"
         , search.toString()
      );
   }


}
