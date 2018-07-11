package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v1.ApiMap;
import i8n.api.map.v1.DummyMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnyMapV1Test {

   @Test
   public void test000() {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      map.get(1);

      assertEquals(
      "[" + map.getName() + "] PUT key=1,value=Bulbasaur\n" +
         "[" + map.getName() + "] PUT key=4,value=Charmander\n" +
         "[" + map.getName() + "] GET key=1"
         , map.toString()
      );
   }


}
