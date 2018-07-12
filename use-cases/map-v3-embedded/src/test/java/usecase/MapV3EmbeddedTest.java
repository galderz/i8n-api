package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v3.ApiMap;
import i8n.api.map.v3.DummyMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MapV3EmbeddedTest {

   @Test
   public void test000() {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      assertEquals("Bulbasaur", map.get(1));
      assertNull(map.getAndPut(7, "Squirtle"));
      assertEquals("Venusaur", map.compute(3, (k, oldV) -> {
         if (oldV == null)
            return "Venusaur";

         return null;
      }));

      assertEquals(
      "[map-v3-embedded] PUT key=1,value=Bulbasaur\n" +
         "[map-v3-embedded] PUT key=4,value=Charmander\n" +
         "[map-v3-embedded] GET key=1\n" +
         "[map-v3-embedded] GET_PUT key=7,value=Squirtle\n" +
         "[map-v3-embedded] COMPUTE key=3"
         , map.toString()
      );
   }

}
