package i8n.api.common;

import java.util.ServiceLoader;
import java.util.function.Function;

public final class Infinispan {

   public static <A> A get(Api<A> api, Object cfg) {
      final Class<Function<Object, A>> factory = api.classApi();
      final ServiceLoader<Function<Object, A>> svcloader = ServiceLoader.load(factory);
      return svcloader.iterator().next().apply(cfg);
   }

}
