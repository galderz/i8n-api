package i8n.api.map.rx.v1;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import java.util.Map;

public interface DummyRxMap<K, V> {

   /**
    * FAO Clement: Maybe<V> vs Single<Optional<V>> ?
    */
   Maybe<V> get(K key);

   Flowable<V> getMany(Flowable<K> keys);

   Completable put(K key, V value);

   Completable putMany(Flowable<Map.Entry<K, V>> pairs);

   /**
    * Name of map
    */
   String getName();

}
