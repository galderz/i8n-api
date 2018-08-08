package i8n.api.map.v1;

import i8n.api.common.Api;

import java.util.function.Function;

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
   public <F extends Function<Object, DummyMap<K, V>>> Class<F> classApi() {
      return (Class) DummyMap.Factory.class;
   }

}
