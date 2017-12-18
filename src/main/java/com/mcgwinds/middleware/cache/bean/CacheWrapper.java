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

    /**
     * 最后加载时间
     */
    private long lastLoadTime;

    /**
     * 缓存时长
     */
    private int expire;

    public T getCacheObject() {
        return cacheObject;
    }

    public void setCacheObject(T cacheObject,int expire) {
        this.cacheObject = cacheObject;
        this.expire=expire;
    }

    /**
     * 判断缓存是否已经过期
     * @return boolean
     */
    public boolean isExpired() {
        if(expire > 0) {
            return (System.currentTimeMillis() - lastLoadTime) > expire * 1000;
        }
        return false;
    }

}
