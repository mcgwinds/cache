package com.mcgwinds.middleware.cache.core.cachebuilder;

import com.mcgwinds.middleware.cache.core.cache.Cache;
import com.mcgwinds.middleware.cache.core.cache.ProxyCache;
import com.mcgwinds.middleware.cache.core.cachecodec.CacheCodec;
import com.mcgwinds.middleware.cache.core.cacheconfig.CacheContextConfig;
import com.mcgwinds.middleware.cache.core.exception.CacheConfigException;

import java.util.function.Function;

/**
 * Created by mcg on 2018/1/30.
 */
public class AbstractCacheBuilder implements CacheBuilder {


    protected Function<CacheContextConfig,Cache> cachefunction;

    private CacheContextConfig cacheContextConfig;

    public CacheContextConfig getConfig() {
        if (cacheContextConfig == null) {
            cacheContextConfig = new CacheContextConfig();
        }
        return cacheContextConfig;
    }


    protected void beforeBuild() {
    }


    public final <K, V> Cache<K, V> buildCache() {
        if (cachefunction == null) {
            throw new CacheConfigException("cacheContextConfig is null");
        }
        beforeBuild();

        Cache<K, V> cache = cachefunction.apply(cacheContextConfig);

        if(cacheContextConfig.isStat()) {

            cache=new ProxyCache<K,V>(cache);
        }

        return cache;
    }

    public void setExpireAfterWriteInMillis(long expireAfterWriteInMillis) {
        getConfig().setExpireAfterWriteInMillis(expireAfterWriteInMillis);
    }

    public void setSerialPolicy(CacheCodec cacheCodec) {
        getConfig().setCacheCodec(cacheCodec);
    }


    public CacheContextConfig getCacheContextConfig() {
        return cacheContextConfig;
    }

    public void setCacheContextConfig(CacheContextConfig cacheContextConfig) {
        this.cacheContextConfig = cacheContextConfig;
    }

    public void setIsStat(boolean isStat) {
        getConfig().setStat(isStat);
    }
}
