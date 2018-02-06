package com.mcgwinds.middleware.cache.core.event;


import com.mcgwinds.middleware.cache.core.cache.Cache;

/**
 * Created by mcg on 2018/1/29.
 */
public class CacheGetEvent extends CacheEvent {


    public CacheGetEvent(Cache cache) {
        super(cache);
    }
}
