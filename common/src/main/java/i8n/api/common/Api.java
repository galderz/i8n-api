package i8n.api.common;

public interface Api<T> {

   // TODO What to use it for?
   Location location();

   <T> Class<T> classApi();

   enum Location {
      EMBEDDED,
      REMOTE,
      ANYWHERE;
   }

}
