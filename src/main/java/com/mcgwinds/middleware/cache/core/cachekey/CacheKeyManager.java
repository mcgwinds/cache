package com.mcgwinds.middleware.cache.core.cachekey;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mcg on 2018/2/4.
 */

@Component
public class CacheKeyManager {

    private ConcurrentHashMap<String, CacheKey> cacheKeys=new ConcurrentHashMap<String, CacheKey>();

    public CacheKey getCacheKey(String fullName) {
        return cacheKeys.get(fullName);
    }

    public void addCacheKey(String fullName,CacheKey cacheKey) {
        cacheKeys.put(fullName,cacheKey);
    }
}
