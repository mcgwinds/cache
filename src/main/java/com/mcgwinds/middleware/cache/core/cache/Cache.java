package com.mcgwinds.middleware.cache.core.cache;


import com.mcgwinds.middleware.cache.core.cacheresult.CacheResult;
import com.mcgwinds.middleware.cache.core.exception.CacheInvokeException;

import java.util.concurrent.TimeUnit;

/**
 * Created by mcg on 2018/1/29.
 */
public interface Cache<K, V> {

    public CacheResult<V> get(K key) throws CacheInvokeException;

    public CacheResult<V> doGet(K key) throws CacheInvokeException;

    public CacheResult<V> put(K key, V value) throws CacheInvokeException;

    public  CacheResult<V> doPut(K key, V value, long expireAfterWrite, TimeUnit timeUnit) throws CacheInvokeException;

    public CacheResult<V> putIfAbsent(K key, V value) throws CacheInvokeException;

    public CacheResult<V> do_Put_If_Absent(K key, V value, long expireAfterWrite, TimeUnit timeUnit) throws CacheInvokeException;

}


