package usecase;

import i8n.api.common.Infinispan;
import i8n.api.map.v1.ApiMap;
import i8n.api.map.v1.DummyMap;

public class AnyMapV1EmbeddedUseCase {

   public static void main(String[] args) {
      final DummyMap<Integer, String> map = Infinispan.get(
         ApiMap.instance(), new Object()
      );

      map.put(1, "Bulbasaur");
      map.put(4, "Charmander");
      map.get(1);

      System.out.println(map.toString());
   }

}
