package com.mcgwinds.middleware.cache.cachemanage.redis;

import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.cachekey.serializer.Serializer;
import com.mcgwinds.middleware.cache.cachemanage.CacheManager;
import com.mcgwinds.middleware.cache.exception.CacheException;
import com.mcgwinds.middleware.cache.util.ClassUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/17.
 */
@Component
public class RedisCacheManager implements CacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheManager.class);

    @Resource
    private  Serializer<Object> serializer;

    @Resource
    private JedisTemplate jedisTemplate;

    public CacheWrapper<Object> getDataOfCache(CacheKey cacheKey, Method method, Object[] arguments)  {
        if(StringUtils.isEmpty(cacheKey.getCacheKey()))
            return null;
        if(null==jedisTemplate) {
            return null;
        }
        CacheWrapper<Object> resCacheWrapper=null;
        Class returnType= ClassUtil.getReturnType(method);
        try {
            String cacheValue = jedisTemplate.get(cacheKey.getCacheKey());
            Object cacheObject=serializer.deserialize(cacheValue.getBytes(),returnType);
            resCacheWrapper.setCacheObject(cacheObject,0);
        }
        catch(CacheException e) {
            LOGGER.error(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return resCacheWrapper;

    }


    @Override
    public void deleteDataOfCache(CacheKey cacheKey, Method method, Object[] arguments) {
        if(StringUtils.isEmpty(cacheKey.getCacheKey()))
            return;
        if(null==jedisTemplate) {
            return;
        }
        try {
            jedisTemplate.delete(cacheKey.getCacheKey());
        }
        catch(CacheException e) {
            LOGGER.error(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }


    }
}
