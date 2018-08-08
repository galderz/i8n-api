package i8n.api.map.rx.v1.impl.embedded;

import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.impl.embedded.DummyAsyncMapEmbedded;
import i8n.api.map.rx.v1.DummyRxMap;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.kohsuke.MetaInfServices;

import java.util.Map;
import java.util.concurrent.CompletionStage;

public class DummyRxMapEmbedded<K, V> implements DummyRxMap<K, V> {

   final DummyAsyncMap<K, V> asyncMap = new DummyAsyncMapEmbedded<>("map-rx-v1-embedded");

   @Override
   public Maybe<V> get(K key) {
      return Maybe.create(source -> {
         final CompletionStage<V> completion = asyncMap.get(key);
         completion.whenComplete(
            (x, t) -> {
               if (t != null)
                  source.onError(t);
               else if (x != null)
                  source.onSuccess(x);
               else
                  source.onComplete();
            }
         );
      });
   }

   @Override
   public Flowable<V> getMany(Flowable<K> keys) {
      return Flowable.fromPublisher(asyncMap.getMany(keys));
   }

   @Override
   public Completable put(K key, V value) {
      return Completable.create(source -> {
         final CompletionStage<Void> completion = asyncMap.put(key, value);
         completion.whenComplete(
            (x, t) -> {
               if (t != null)
                  source.onError(t);
               else
                  source.onComplete();
            }
         );
      });
   }

   @Override
   public Completable putMany(Flowable<Map.Entry<K, V>> pairs) {
      return Completable.create(source -> {
         final CompletionStage<Void> completion = asyncMap.putMany(pairs);
         completion.whenComplete(
            (x, t) -> {
               if (t != null)
                  source.onError(t);
               else
                  source.onComplete();
            }
         );
      });
   }

   @Override
   public String getName() {
      return asyncMap.getName();
   }

   @Override
   public String toString() {
      return asyncMap.toString();
   }

   @MetaInfServices
   public final static class FactoryImpl<K, V> implements DummyRxMap.Factory<K, V> {

      @Override
      public DummyRxMap<K, V> apply(Object o) {
         return new DummyRxMapEmbedded<>();
      }

   }

}
