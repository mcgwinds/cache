package com.mcgwinds.middleware.cache.core.cache;



import com.mcgwinds.middleware.cache.core.cacheconfig.CacheContextConfig;
import com.mcgwinds.middleware.cache.core.cacheresult.CacheResult;
import com.mcgwinds.middleware.cache.core.exception.CacheInvokeException;

import java.util.concurrent.TimeUnit;

/**
 * Created by mcg on 2018/1/30.
 */
public abstract class AbstractCache<K,V> implements Cache<K,V>{

    public abstract CacheContextConfig config();

    public CacheResult<V> get(K key) throws CacheInvokeException {
        return doGet(key);

    }

    public CacheResult<V> put(K key, V value) throws CacheInvokeException{
        return doPut(key, value, config().getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS);
    }

    public  CacheResult<V> putIfAbsent(K key, V value) throws CacheInvokeException {
        return do_Put_If_Absent(key, value, config().getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS);

    }

}
