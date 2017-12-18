package com.mcgwinds.middleware.cache.cachemanage;

import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.exception.CacheException;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */
public interface CacheManager {

    <T> CacheWrapper<T> getDataOfCache(CacheKey cacheKey, Method method, Object[] arguments);

    boolean deleteDataOfCache(CacheKey cacheKey, Method method, Object[] arguments);
}
