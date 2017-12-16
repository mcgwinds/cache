package com.mcgwinds.middleware.cache.cachemanage.redis;

import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.cachekey.serializer.Serializer;
import com.mcgwinds.middleware.cache.cachekey.serializer.StringSerializer;
import com.mcgwinds.middleware.cache.cachemanage.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by mcg on 2017/12/12.
 */

@Component
public class RedisCacheManager implements CacheManager{

    private final static Serializer<String> KEYSERIALIZER=new StringSerializer();

    @Resource
    private JedisTemplate jedisTemplate;

    public CacheWrapper<Object> getDataOfCache(CacheKey cacheKe) {
        return null;
    }
}
