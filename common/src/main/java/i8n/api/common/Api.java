package i8n.api.common;

public interface Api<T> {

   Location location();

   <T> Class<T> classApi();

   enum Location {
      EMBEDDED,
      REMOTE,
      ANYWHERE;
   }

}
