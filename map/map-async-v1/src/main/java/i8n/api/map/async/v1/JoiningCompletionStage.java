package i8n.api.map.async.v1;

import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO Temporary location, it should live somewhere else
public class JoiningCompletionStage {

   private final CountDownLatch latch = new CountDownLatch(1);
   private final CompletionStage<?> completion;
   private volatile Throwable failure;

   public JoiningCompletionStage(CompletionStage<?> completion) {
      this.completion = completion;
   }

   public void join(long timeout, TimeUnit unit) {
      completion.whenComplete(
         (x, t) -> {
            if (t == null)
               latch.countDown();
            else
               failure = t;
         }
      );

      long duration = unit.toMillis(timeout);
      long failTime = System.currentTimeMillis() + duration;

      while (System.currentTimeMillis() < failTime) {
         if (failure != null)
            throw new CompletionException(failure);

         try {
            boolean success = latch.await(100, TimeUnit.MILLISECONDS);
            if (success)
               return;

         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
         }
      }

      throw new CompletionException(new TimeoutException("Timed out waiting"));

   }

}
