package com.mcgwinds.middleware.cache.core.cachebuilder;


import com.mcgwinds.middleware.cache.core.cache.Cache;

/**
 * Created by mcg on 2018/1/30.
 */
public interface CacheBuilder {

     <K,V> Cache<K,V> buildCache();
}
