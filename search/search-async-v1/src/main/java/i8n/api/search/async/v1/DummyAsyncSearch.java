package i8n.api.search.async.v1;

import org.reactivestreams.Publisher;

public interface DummyAsyncSearch {

   String getName();

   DummyAsyncQuery createQuery(String queryString);

   interface DummyAsyncQuery {

      <T> Publisher<T> execute();

   }

}
