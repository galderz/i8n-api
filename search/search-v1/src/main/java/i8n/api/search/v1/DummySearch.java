package i8n.api.search.v1;

import java.util.List;
import java.util.function.Function;

public interface DummySearch {

   String getName();

   DummyQuery createQuery(String queryString);

   /**
    * Unwrap the underlying data container (map, counter, multi map...)
    */
   <T> T unwrap();

   interface DummyQuery {

      <T> List<T> execute();

   }

   /**
    * Factory
    */
   interface Factory extends Function<Object, DummySearch> {}

}
