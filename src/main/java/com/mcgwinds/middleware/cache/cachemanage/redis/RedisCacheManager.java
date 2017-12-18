package com.mcgwinds.middleware.cache.cachemanage.redis;

import com.alibaba.fastjson.TypeReference;
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
        try {
            String cacheValue = jedisTemplate.get(cacheKey.getCacheKey());
            resCacheWrapper=(CacheWrapper<Object>)serializer.deserialize(cacheValue.getBytes(),new TypeReference<CacheWrapper<Object>>(){});
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
    public boolean deleteDataOfCache(CacheKey cacheKey, Method method, Object[] arguments) {
        boolean delSuccess=true;
        if(StringUtils.isEmpty(cacheKey.getCacheKey()))
            return delSuccess;
        if(null==jedisTemplate) {
            return delSuccess;
        }
        try {
            jedisTemplate.delete(cacheKey.getCacheKey());
        }
        catch(CacheException e) {
            LOGGER.error(e.getMessage());
            delSuccess= false;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            delSuccess= false;
        }
        finally {
            return delSuccess;
        }


    }
}
