package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.ApiMap;
import i8n.api.map.v2.DummyMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnyMapV2RemoteTest {

   @Test
   public void test000() {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      map.get(1);
      map.getAndPut(7, "Squirtle");

      assertEquals(
         "[map-v2-remote] PUT key=1,value=Bulbasaur\n" +
         "[map-v2-remote] PUT key=4,value=Charmander\n" +
         "[map-v2-remote] GET key=1\n" +
         "[map-v2-remote] GET_PUT key=7,value=Squirtle"
         , map.toString()
      );
   }

}
