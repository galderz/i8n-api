package i8n.api.common;

import java.util.ServiceLoader;

public final class Infinispan {

   public static <T> T get(Api<T> api, Object cfg) {
      final ServiceLoader<T> svcloader = ServiceLoader.load(api.classApi());
      return svcloader.iterator().next();
   }

}
