package i8n.api.search.v1;

import java.util.List;

public interface DummySearch {

   String getName();

   DummyQuery createQuery(String queryString);

   interface DummyQuery {

      <T> List<T> execute();

   }

}
