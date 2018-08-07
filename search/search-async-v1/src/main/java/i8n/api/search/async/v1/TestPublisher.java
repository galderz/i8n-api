package i8n.api.search.async.v1;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

// TODO Temporary class
public class TestPublisher {

   public static <T> Publisher<T> fromArray(T... items) {
      return new PublisherFromArray<>(items);
   }

   private static final class PublisherFromArray<T> implements Publisher<T> {

      final T[] array;

      private PublisherFromArray(T[] array) {
         this.array = array;
      }

      @Override
      public void subscribe(Subscriber<? super T> s) {
         for (T t : array)
            s.onNext(t);

         s.onComplete();
      }

   }

}
