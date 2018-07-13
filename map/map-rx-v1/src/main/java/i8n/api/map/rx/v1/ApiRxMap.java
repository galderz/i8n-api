package i8n.api.map.rx.v1;

import i8n.api.common.Api;

public class ApiRxMap<K, V> implements Api<DummyRxMap<K, V>> {

   private static final ApiRxMap INSTANCE = new ApiRxMap();

   private ApiRxMap() {
   }

   public static <K, V> ApiRxMap<K, V> instance() {
      return INSTANCE;
   }

   @Override
   public Location location() {
      return Location.ANYWHERE;
   }

   @Override
   public <T> Class<T> classApi() {
      return (Class<T>) DummyRxMap.class;
   }

}
