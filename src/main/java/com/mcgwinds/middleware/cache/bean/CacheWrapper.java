package com.mcgwinds.middleware.cache.bean;

import java.io.Serializable;

/**
 * Created by mcg on 2017/12/16.
 */
public class CacheWrapper<T> implements Serializable, Cloneable {

    /**
     * 缓存数据
     */
    private T cacheObject;

    public T getCacheObject() {
        return cacheObject;
    }

    public void setCacheObject(T cacheObject) {
        this.cacheObject = cacheObject;
    }
}
