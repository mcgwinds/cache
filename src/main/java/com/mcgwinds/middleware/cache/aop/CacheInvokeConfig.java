package com.mcgwinds.middleware.cache.aop;

/**
 * Created by mcg on 2018/1/23.
 */
public class CacheInvokeConfig {

    private String name;

    private String namespace;

    private boolean  isCache;

    private Long expireAfterWriteInMillis;

    private String cacheType;

    private String serialPolicy;

    private boolean isStat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public Long getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(Long expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }

    public String getSerialPolicy() {
        return serialPolicy;
    }

    public void setSerialPolicy(String serialPolicy) {
        this.serialPolicy = serialPolicy;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }


    public boolean isStat() {
        return isStat;
    }

    public void setStat(boolean stat) {
        isStat = stat;
    }
}
