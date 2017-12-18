package com.mcgwinds.middleware.cache.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by mcg on 2017/12/14.
 */
public final class CacheKey implements Serializable {

    private final String cacheKey;// 缓存Key

    private String cacheKeyHead; //缓存key头

    private String namespace; //命名空间

    public CacheKey(String namespace,String cacheKeyHead,String cacheKey) {
        this.namespace = namespace;
        this.cacheKeyHead = cacheKeyHead;
        this.cacheKey = cacheKey;

    }


    public String getCacheKey() {
        StringBuilder cacheKey=new StringBuilder();
        if(!StringUtils.isEmpty(namespace)) {
            cacheKey.append(namespace).append(":");

        }
        cacheKey.append(cacheKeyHead).append(":").append(cacheKey);
        return this.cacheKey;
    }
}
