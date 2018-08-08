package i8n.api.map.async.v1;

import i8n.api.common.Api;

import java.util.function.Function;

public class ApiAsyncMap<K, V> implements Api<DummyAsyncMap<K, V>> {

   private static final ApiAsyncMap INSTANCE = new ApiAsyncMap();

   private ApiAsyncMap() {
   }

   public static <K, V> ApiAsyncMap<K, V> instance() {
      return INSTANCE;
   }

   @Override
   public Location location() {
      return Location.ANYWHERE;
   }

   @Override
   public <F extends Function<Object, DummyAsyncMap<K, V>>> Class<F> classApi() {
      return (Class) DummyAsyncMap.Factory.class;
   }

}
