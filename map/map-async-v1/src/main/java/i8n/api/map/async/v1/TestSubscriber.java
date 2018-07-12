package i8n.api.map.async.v1;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// TODO Temporary class
public final class TestSubscriber<T> implements Subscriber<T> {

   final CountDownLatch done = new CountDownLatch(1);
   boolean timeout;

   final List<T> values = new ArrayList<>();
   final List<Throwable> errors = new ArrayList<>();
   long completions;

   @Override
   public void onSubscribe(Subscription s) {
      // TODO: Customise this generated block
   }

   @Override
   public void onNext(T t) {
      values.add(t);
   }

   @Override
   public void onError(Throwable t) {
      errors.add(t);
      done.countDown();
   }

   @Override
   public void onComplete() {
      completions++;
      done.countDown();
   }

   public boolean awaitTerminalEvent(long duration, TimeUnit unit) {
      try {
         return await(duration, unit);
      } catch (InterruptedException ex) {
         Thread.currentThread().interrupt();
         return false;
      }
   }

   public boolean await(long time, TimeUnit unit) throws InterruptedException {
      boolean d = done.getCount() == 0 || (done.await(time, unit));
      timeout = !d;
      return d;
   }

   public void assertNoErrors() {
      int s = errors.size();
      if (s != 0)
         throw new AssertionError("Error(s) present: " + errors);
   }

   public void assertComplete() {
      long c = completions;
      if (c == 0)
         throw new AssertionError("Not completed");
      else if (c > 1)
         throw new AssertionError("Multiple completions: " + c);
   }

   public void assertValueSet(Collection<? extends T> expected) {
      if (expected.isEmpty())
         assertNoValues();

      final int expectedSize = expected.size();
      final int actualSize = values.size();
      if (expectedSize != actualSize)
         throw new AssertionError(
            String.format(
               "Expected %d elements, but got %d: %s"
               , expectedSize, actualSize, values
            ));

      for (T v : this.values) {
         if (!expected.contains(v)) {
            throw new AssertionError(
               "Value not in the expected collection: " + valueAndClass(v));
         }
      }
   }

   public void assertNoValues() {
      assertValueCount(0);
   }

   public void assertValueCount(int count) {
      int s = values.size();
      if (s != count)
         throw new AssertionError(
            "Value counts differ; Expected: " + count + ", Actual: " + s);
   }

   public static String valueAndClass(Object o) {
      if (o != null)
         return o + " (class: " + o.getClass().getSimpleName() + ")";
      
      return "null";
   }

}
