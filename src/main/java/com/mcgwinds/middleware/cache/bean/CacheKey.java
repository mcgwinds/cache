package com.mcgwinds.middleware.cache.bean;

import java.io.Serializable;

/**
 * Created by mcg on 2017/12/14.
 */
public final class CacheKey implements Serializable {

    private final String cachekey;// 缓存Key

    public CacheKey(String cachekey) {
        this.cachekey=cachekey;

    }

}
