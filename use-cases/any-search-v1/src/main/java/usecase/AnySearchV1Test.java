package usecase;

import i8n.api.common.Infinispan;
import i8n.api.search.v1.ApiSearch;
import i8n.api.search.v1.DummySearch;
import i8n.api.search.v1.DummySearch.DummyQuery;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnySearchV1Test {

   @Test
   public void test000() {
      final DummySearch search = Infinispan.get(
         ApiSearch.instance(), new Object()
      );

      final DummyQuery query = search.createQuery("xyz");
      final List<Object> results = query.execute();
      assertTrue(results.isEmpty());

      assertEquals(
      "[" + search.getName() + "] CREATE_QUERY query=xyz\n" +
         "[" + search.getName() + "] EXEC_QUERY query=xyz"
         , search.toString()
      );
   }


}
