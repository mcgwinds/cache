package com.mcgwinds.middleware.cache.core.cache;

import java.io.Serializable;

/**
 * Created by mcg on 2018/2/5.
 */
public class CacheValueHolder<V> implements Serializable {
    private static final long serialVersionUID = 1L;
    private V value;
    private long expireTime;
    private long accessTime;

    //反序列化
    public CacheValueHolder() {

    }

    public CacheValueHolder(V value, long expireAfterWrite) {
        this.value = value;
        this.accessTime = System.currentTimeMillis();
        this.expireTime = accessTime + expireAfterWrite;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }
}

