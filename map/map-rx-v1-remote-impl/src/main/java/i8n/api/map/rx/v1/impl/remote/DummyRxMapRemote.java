package i8n.api.map.rx.v1.impl.remote;

import i8n.api.map.async.v1.DummyAsyncMap;
import i8n.api.map.async.v1.impl.remote.DummyAsyncMapRemote;
import i8n.api.map.rx.v1.DummyRxMap;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.kohsuke.MetaInfServices;

import java.util.Map;
import java.util.concurrent.CompletionStage;

@MetaInfServices
public class DummyRxMapRemote<K, V> implements DummyRxMap<K, V> {

   final DummyAsyncMap<K, V> asyncMap = new DummyAsyncMapRemote<>("map-rx-v1-remote");

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

}
