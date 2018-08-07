package i8n.api.search.async.v1;

import i8n.api.common.Api;

public class ApiAsyncSearch implements Api<DummyAsyncSearch> {

   private static final ApiAsyncSearch INSTANCE = new ApiAsyncSearch();

   private ApiAsyncSearch() {
   }

   public static ApiAsyncSearch instance() {
      return INSTANCE;
   }

   @Override
   public Location location() {
      return Location.ANYWHERE;
   }

   @Override
   public <T> Class<T> classApi() {
      return (Class<T>) DummyAsyncSearch.class;
   }

}
