package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.ApiMap;
import i8n.api.map.v2.DummyMap;
import org.junit.Test;

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

}
