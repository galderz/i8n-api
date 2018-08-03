package i8n.api.search.v1;

import i8n.api.common.Api;

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
   public <T> Class<T> classApi() {
      return (Class<T>) DummySearch.class;
   }

}
