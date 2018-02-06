package com.mcgwinds.middleware.cache.core.cacheconfig;


import com.mcgwinds.middleware.cache.core.cachecodec.CacheCodec;

/**
 * Created by mcg on 2018/1/23.
 */
public class CacheContextConfig {

    private long expireAfterWriteInMillis;

    private CacheCodec cacheCodec;

    private boolean isStat;

    public long getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(long expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }

    public CacheCodec getCacheCodec() {
        return cacheCodec;
    }

    public void setCacheCodec(CacheCodec cacheCodec) {
        this.cacheCodec = cacheCodec;
    }

    public boolean isStat() {
        return isStat;
    }

    public void setStat(boolean stat) {
        isStat = stat;
    }
}
