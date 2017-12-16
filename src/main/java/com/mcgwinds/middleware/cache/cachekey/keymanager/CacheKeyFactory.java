package com.mcgwinds.middleware.cache.cachekey.keymanager;

import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.bean.CacheKey;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mcg on 2017/12/14.
 */
public interface CacheKeyFactory {

    /**
     * 获取缓存key
     * @return CacheKey
     */
    CacheKey getCacheKey(Cache cache, Method method, Object [] arguments,Parameter[] parameters);

}
