package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v2.ApiMap;
import i8n.api.map.v2.DummyMap;

public class AnyMapV2EmbeddedUseCase {

   public static void main(String[] args) {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      map.get(1);
      map.getAndPut(7, "Squirtle");

      System.out.println(map.toString());
   }

}
