package com.mcgwinds.middleware.cache.cachemanage;

import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;

/**
 * Created by mcg on 2017/12/12.
 */
public interface CacheManager {

    CacheWrapper<Object> getDataOfCache(CacheKey cacheKe);
}
