package com.mcgwinds.middleware.cache.annotation;

/**
 * Created by mcg on 2018/1/23.
 */
public class AnnoCacheConfig {

    private String name;

    private String namespace;

    private Boolean  isCache;

    private Long expireAfterWriteInMillis;

    private String cacheType;

    private String serialPolicy;

    private Boolean isStat;

    public Boolean getStat() {
        return isStat;
    }

    public void setStat(Boolean stat) {
        isStat = stat;
    }

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

    public Boolean isCache() {
        return isCache;
    }

    public void setCache(Boolean cache) {
        isCache = cache;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public String getSerialPolicy() {
        return serialPolicy;
    }

    public void setSerialPolicy(String serialPolicy) {
        this.serialPolicy = serialPolicy;
    }

    public Boolean getCache() {
        return isCache;
    }

    public Long getExpireAfterWriteInMillis() {
        return expireAfterWriteInMillis;
    }

    public void setExpireAfterWriteInMillis(Long expireAfterWriteInMillis) {
        this.expireAfterWriteInMillis = expireAfterWriteInMillis;
    }

}
