package com.mcgwinds.middleware.cache.core.event;


import com.mcgwinds.middleware.cache.core.cache.Cache;

/**
 * Created by mcg on 2018/2/4.
 */
public class CacheEvent {

    protected Cache cache;

    public CacheEvent(Cache cache) {
        this.cache = cache;
    }

    public Cache getCache() {
        return cache;
    }
}
