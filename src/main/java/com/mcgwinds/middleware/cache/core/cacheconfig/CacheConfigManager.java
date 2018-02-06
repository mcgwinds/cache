package com.mcgwinds.middleware.cache.core.cacheconfig;

import com.mcgwinds.middleware.cache.annotation.AnnoCacheConfig;
import com.mcgwinds.middleware.cache.annotation.Cacheable;
import com.mcgwinds.middleware.cache.aop.CacheInvokeConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mcg on 2018/1/29.
 */
@Component
public class CacheConfigManager {

    @Resource
    private GlobalCacheConfig globalCacheConfig;


    private ConcurrentHashMap<String, CacheInvokeConfig> cacheConfigMap=new ConcurrentHashMap<String, CacheInvokeConfig>();

    public AnnoCacheConfig parseCacheConfig(Method m) {
        Cacheable anno = m.getAnnotation(Cacheable.class);
        if (anno == null) {
            return null;
        }
        AnnoCacheConfig annoCacheConfig = new AnnoCacheConfig();
        annoCacheConfig.setName(anno.name());
        annoCacheConfig.setNamespace(anno.namespace());
        annoCacheConfig.setCacheType(anno.cacheType());
        annoCacheConfig.setCache(anno.isCache());
        annoCacheConfig.setExpireAfterWriteInMillis(anno.expireAfterWriteInMillis());
        annoCacheConfig.setSerialPolicy(anno.serialPolicy());
        annoCacheConfig.setStat(anno.isStat());
        return annoCacheConfig;
    }

    public CacheInvokeConfig parseCacheInvokeConfig(Method method) {

        AnnoCacheConfig annoCacheConfig=parseCacheConfig(method);
        CacheInvokeConfig cacheInvokeConfig=new CacheInvokeConfig();
        if(globalCacheConfig!=null) {

            cacheInvokeConfig.setCache(globalCacheConfig.isCache());
            cacheInvokeConfig.setCacheType(globalCacheConfig.getCacheType());
            cacheInvokeConfig.setExpireAfterWriteInMillis(globalCacheConfig.getExpireAfterWriteInMillis());
            cacheInvokeConfig.setSerialPolicy( globalCacheConfig.getSerialPolicy());
            cacheInvokeConfig.setName(globalCacheConfig.getName());
            cacheInvokeConfig.setNamespace(globalCacheConfig.getNamespace());
            cacheInvokeConfig.setStat(globalCacheConfig.isStat());
        }

        if(annoCacheConfig!=null) {

            if((annoCacheConfig.isCache() != null)) cacheInvokeConfig.setCache(annoCacheConfig.isCache());

            if((annoCacheConfig.getCacheType() != null)) cacheInvokeConfig.setCacheType( annoCacheConfig.getCacheType());

            if((annoCacheConfig.getExpireAfterWriteInMillis() != null)) cacheInvokeConfig.setExpireAfterWriteInMillis(annoCacheConfig.getExpireAfterWriteInMillis());

            if(!StringUtils.isEmpty(annoCacheConfig.getSerialPolicy())) cacheInvokeConfig.setSerialPolicy(annoCacheConfig.getSerialPolicy());

           if(!StringUtils.isEmpty(annoCacheConfig.getName()))cacheInvokeConfig.setName(annoCacheConfig.getName());

           if(!StringUtils.isEmpty(annoCacheConfig.getNamespace())) cacheInvokeConfig.setNamespace(annoCacheConfig.getNamespace());

           if(annoCacheConfig.getStat()!=null)cacheInvokeConfig.setStat(annoCacheConfig.getStat());

        }
        return cacheInvokeConfig;
    }

    public CacheInvokeConfig get(String key) {

       return cacheConfigMap.get(key);
    }

    public void put(String key,CacheInvokeConfig cacheInvokeConfig) {
        cacheConfigMap.put(key,cacheInvokeConfig);
    }


}
