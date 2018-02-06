package com.mcgwinds.middleware.cache.core.cachekey;

/**
 * Created by mcg on 2018/2/4.
 */
public class CacheKey<T> {

    private T key;

    CacheKey(T key) {
        this.key=key;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}
