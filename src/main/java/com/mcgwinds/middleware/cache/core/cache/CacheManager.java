package com.mcgwinds.middleware.cache.core.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mcg on 2018/1/29.
 */

@Component
public class CacheManager {

    private ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap();

    public Cache getCache(String key) {
        return caches.get(key);
    }

    public void addCache(String key, Cache cache) {
        if(cache==null) {
            return;
        }
        caches.put(key, cache);
    }
}
