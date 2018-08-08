package i8n.api.search.v1;

import i8n.api.common.Api;

import java.util.function.Function;

public class ApiSearch implements Api<DummySearch> {

   private static final ApiSearch INSTANCE = new ApiSearch();

   private ApiSearch() {
   }

   public static <K, V> ApiSearch instance() {
      return INSTANCE;
   }

   @Override
   public Location location() {
      return Location.ANYWHERE;
   }

   @Override
   public <F extends Function<Object, DummySearch>> Class<F> classApi() {
      return (Class) DummySearch.Factory.class;
   }

}
