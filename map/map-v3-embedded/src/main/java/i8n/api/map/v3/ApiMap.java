package i8n.api.map.v3;

import i8n.api.common.Api;

public class ApiMap<K, V> implements Api<DummyMap<K, V>> {

   private static final ApiMap INSTANCE = new ApiMap();

   private ApiMap() {
   }

   public static <K, V> ApiMap<K, V> instance() {
      return INSTANCE;
   }

   @Override
   public Location location() {
      return Location.ANYWHERE;
   }

   @Override
   public <T> Class<T> classApi() {
      return (Class<T>) DummyMap.class;
   }

}
