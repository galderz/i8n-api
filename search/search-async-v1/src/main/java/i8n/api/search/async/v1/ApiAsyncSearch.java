package i8n.api.search.async.v1;

import i8n.api.common.Api;

import java.util.function.Function;

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
   public <F extends Function<Object, DummyAsyncSearch>> Class<F> classApi() {
      return (Class) DummyAsyncSearch.Factory.class;
   }

}
