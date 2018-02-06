package com.mcgwinds.middleware.cache.core.cache;

import com.mcgwinds.middleware.cache.core.cacheresult.CacheResult;
import com.mcgwinds.middleware.cache.core.exception.CacheInvokeException;

import java.util.concurrent.TimeUnit;

/**
 * Created by mcg on 2018/2/5.
 */
public class ProxyCache<K,V>  implements Cache<K,V> {

    private Cache<K,V> cache;

    public ProxyCache(Cache<K,V> cache) {
        this.cache=cache;
    }


    @Override
    public CacheResult<V> get(K key) throws CacheInvokeException {
        return cache.get(key);
    }

    @Override
    public CacheResult<V> doGet(K key) {
        return cache.doGet(key);
    }

    @Override
    public CacheResult<V> put(K key, V value) throws CacheInvokeException {
        return cache.put(key,value);
    }

    @Override
    public CacheResult<V> putIfAbsent(K key, V value) throws CacheInvokeException {
        return cache.putIfAbsent(key,value);
    }

    @Override
    public CacheResult<V> doPut(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return cache.doPut(key,value,expireAfterWrite,timeUnit);
    }

    @Override
    public CacheResult<V> do_Put_If_Absent(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return cache.do_Put_If_Absent(key, value, expireAfterWrite, timeUnit);
    }
}
