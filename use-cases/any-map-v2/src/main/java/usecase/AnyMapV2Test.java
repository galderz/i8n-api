package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.ApiMap;
import i8n.api.map.v2.DummyMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AnyMapV2Test {

   @Test
   public void test000() {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      assertEquals("Bulbasaur", map.get(1));
      assertNull(map.getAndPut(7, "Squirtle"));

      assertEquals(
      "[" + map.getName() + "] PUT key=1,value=Bulbasaur\n" +
         "[" + map.getName() + "] PUT key=4,value=Charmander\n" +
         "[" + map.getName() + "] GET key=1\n" +
         "[" + map.getName() + "] GET_PUT key=7,value=Squirtle"
         , map.toString()
      );
   }

   @Test
   public void test001() {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      List<String> expected = Arrays.asList("Ivysaur", "Charmaleon", "Wartotle");
      Collections.sort(expected);

      map.put(2, "Ivysaur");
      map.put(5, "Charmaleon");
      map.put(8, "Wartotle");

      final List<String> actual = map.values().collect(Collectors.toList());
      Collections.sort(actual);

      assertEquals(expected, actual);
   }

}
