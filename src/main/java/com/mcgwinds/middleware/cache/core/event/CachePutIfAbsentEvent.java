package com.mcgwinds.middleware.cache.core.event;


import com.mcgwinds.middleware.cache.core.cache.Cache;

/**
 * Created by mcg on 2018/2/5.
 */
public class CachePutIfAbsentEvent extends CacheEvent {
    public CachePutIfAbsentEvent(Cache cache) {
        super(cache);
    }
}
