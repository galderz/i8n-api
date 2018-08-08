package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.DummyMap;
import i8n.api.search.v1.ApiSearch;
import i8n.api.search.v1.DummySearch;
import i8n.api.search.v1.DummySearch.DummyQuery;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class AnySearchV1Test {

   @Test
   public void test000() {
      final DummySearch search = Infinispan.get(
         ApiSearch.instance(), new Object()
      );

      final DummyMap<Integer, String> map = search.unwrap();
      map.put(13, "Weedle");
      map.put(14, "Kakuna");
      map.put(15, "Beedrill");

      final DummyQuery query = search.createQuery("FROM Pokemon p where p.type == bug");

      final List<String> actualList =
         query.<String>execute().sorted().collect(Collectors.toList());

      assertEquals(3, actualList.size());

      List<String> expected = Arrays.asList("Weedle", "Kakuna", "Beedrill");
      Collections.sort(expected);

      assertEquals(expected, actualList);

      assertEquals(
         "[" + map.getName() + "] PUT key=13,value=Weedle\n" +
            "[" + map.getName() + "] PUT key=14,value=Kakuna\n" +
            "[" + map.getName() + "] PUT key=15,value=Beedrill\n" +
            "[" + map.getName() + "] VALUES"
         , map.toString()
      );
      assertEquals(
      "[" + search.getName() + "] CREATE_QUERY query=FROM Pokemon p where p.type == bug\n" +
         "[" + search.getName() + "] EXEC_QUERY query=FROM Pokemon p where p.type == bug"
         , search.toString()
      );
   }


}
