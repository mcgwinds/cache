package com.mcgwinds.middleware.cache.core.cache;


import com.mcgwinds.middleware.cache.aop.CacheInvokeConfig;
import com.mcgwinds.middleware.cache.aop.CacheInvokeContext;
import com.mcgwinds.middleware.cache.core.cachebuilder.AbstractCacheBuilder;
import com.mcgwinds.middleware.cache.core.cachecodec.CacheCodec;
import com.mcgwinds.middleware.cache.core.cacheconfig.GlobalCacheConfig;
import com.mcgwinds.middleware.cache.core.cachekey.CacheKey;
import com.mcgwinds.middleware.cache.core.cachekey.CacheKeyManager;
import com.mcgwinds.middleware.cache.core.cachekey.DefaultCacheKeyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by mcg on 2018/1/29.
 */
@Component
public class CacheContext implements ApplicationContextAware {

    private static final Logger logger=LoggerFactory.getLogger(CacheContext.class);

    @Resource
    private GlobalCacheConfig globalCacheConfig;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private CacheKeyManager cacheKeyManager;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
    }

    public CacheInvokeContext createCacheInvokeContext() {

        CacheInvokeContext c = new CacheInvokeContext();
        c.setCacheFunction((invokeContext) -> {
            CacheInvokeConfig cacheInvokeConfig = invokeContext.getCacheInvokeConfig();
            String cacheName=cacheInvokeConfig.getName();
            String namespace= cacheInvokeConfig.getNamespace();
            String fullCacheName = namespace + "_" + cacheName;
            //to fix
            return createOrGetCache(cacheInvokeConfig, fullCacheName);
        });
        c.setCacheKeyFunction((invokeContext)->{
            CacheInvokeConfig cacheInvokeConfig = invokeContext.getCacheInvokeConfig();
            String cacheName=cacheInvokeConfig.getName();
            String namespace= cacheInvokeConfig.getNamespace();
            String fullCacheName = namespace + "_" + cacheName;
            //to fix
            return createOrGetCachekey(invokeContext, fullCacheName);

        });
        return c;
    }

    private CacheKey createOrGetCachekey(CacheInvokeContext invokeContext, String fullCacheName) {
        CacheKey cachekey = cacheKeyManager.getCacheKey(fullCacheName);
        if (cachekey == null) {
            synchronized (this) {
                cachekey = cacheKeyManager.getCacheKey(fullCacheName);
                if (cachekey == null) {
                    cachekey = buildCacheKey(invokeContext,fullCacheName);
                    cacheKeyManager.addCacheKey(fullCacheName, cachekey);
                }
            }
        }
        return cachekey;


    }

    private CacheKey buildCacheKey(CacheInvokeContext invokeContext,String fullCacheName) {
        DefaultCacheKeyBuilder cacheKeyBuilder=new DefaultCacheKeyBuilder();
        cacheKeyBuilder.setCacheInvokeContext(invokeContext);
        cacheKeyBuilder.setFullCacheName(fullCacheName);
        return cacheKeyBuilder.buildCacheKey();
    }

    private Cache createOrGetCache(CacheInvokeConfig cacheInvokeConfig, String fullCacheName) {
        Cache cache = cacheManager.getCache(fullCacheName);
        if (cache == null) {
            synchronized (this) {
                cache = cacheManager.getCache(fullCacheName);
                if (cache == null) {
                    cache = buildCache(cacheInvokeConfig);
                    cacheManager.addCache(fullCacheName, cache);
                }
            }
        }
        return cache;

    }

    protected Cache buildCache(CacheInvokeConfig cacheInvokeConfig) {
        String cacheType=cacheInvokeConfig.getCacheType();
        AbstractCacheBuilder cacheBuilder= (AbstractCacheBuilder) applicationContext.getBean(cacheType);
        if(cacheBuilder==null) {
            logger.warn("the cacheBuilder bean of ({}) is not found ",cacheType);
            return null;
        }
        cacheBuilder.setExpireAfterWriteInMillis(cacheInvokeConfig.getExpireAfterWriteInMillis());

        CacheCodec cacheCodec= (CacheCodec) applicationContext.getBean(cacheInvokeConfig.getSerialPolicy());
        if(cacheCodec==null) {
            logger.warn("the CacheCodec bean of ({}) is not found ",cacheInvokeConfig.getSerialPolicy());
           return null;
        }
        cacheBuilder.setSerialPolicy(cacheCodec);
        Cache cache = cacheBuilder.buildCache();

        return cache;
    }



}
