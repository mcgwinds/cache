package com.mcgwinds.middleware.cache.bean;

import com.mcgwinds.middleware.cache.annotation.Cache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.io.Serializable;

/**
 * Created by mcg on 2017/12/18.
 */
public class AutoLoadWrapper implements Serializable {

    /**
     * 缓存注解
     */
    private final Cache cache;

    /**
     * 缓存时长
     */
    private int expire;

    /**
     * 缓存Key
     */
    private final CacheKey cacheKey;

    /**
     * 上次从DAO加载数据时间
     */
    private long lastLoadTime=0L;

    /**
     * 上次请求数据时间
     */
    private long lastRequestTime=0L;

    /**
     * 第一次请求数据时间
     */
    private long firstRequestTime=0L;

    /**
     * 请求数据次数
     */
    private long requestTimes=0L;

    private volatile boolean loading=false;

    /**
     * 加载次数
     */
    private long loadCnt=0L;

    /**
     * 从DAO中加载数据，使用时间的总和
     */
    private long useTotalTime=0L;

    private ProceedingJoinPoint proceedingJoinPoint;


    public AutoLoadWrapper(CacheKey cacheKey, Cache cache,ProceedingJoinPoint pjp,  int expire) {
        this.cacheKey=cacheKey;
        this.cache=cache;
        this.proceedingJoinPoint=pjp;
        this.expire=expire;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public AutoLoadWrapper setLastRequestTime(long lastRequestTime) {
        synchronized(this) {
            this.lastRequestTime=lastRequestTime;
            if(firstRequestTime == 0) {
                firstRequestTime=lastRequestTime;
            }
            requestTimes++;
        }
        return this;
    }

    public long getFirstRequestTime() {
        return firstRequestTime;
    }

    public long getRequestTimes() {
        return requestTimes;
    }

    public Cache getCache() {
        return cache;
    }

    public long getLastLoadTime() {
        return lastLoadTime;
    }

    /**
     * @param lastLoadTime last load time
     * @return this
     */
    public AutoLoadWrapper setLastLoadTime(long lastLoadTime) {
        if(lastLoadTime > this.lastLoadTime) {
            this.lastLoadTime=lastLoadTime;
        }
        return this;
    }

    public CacheKey getCacheKey() {
        return cacheKey;
    }

    public boolean isLoading() {
        return loading;
    }

    /**
     * @param loading 是否正在加载
     * @return this
     */
    public AutoLoadWrapper setLoading(boolean loading) {
        this.loading=loading;
        return this;
    }

    public long getLoadCnt() {
        return loadCnt;
    }

    public long getUseTotalTime() {
        return useTotalTime;
    }

    public ProceedingJoinPoint getProceedingJoinPoint() {
        return proceedingJoinPoint;
    }

    public void setProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        this.proceedingJoinPoint = proceedingJoinPoint;
    }

    /**
     * 记录用时
     * @param useTime 用时
     * @return this
     */
    public AutoLoadWrapper addUseTotalTime(long useTime) {
        synchronized(this) {
            this.loadCnt++;
            this.useTotalTime+=useTotalTime;
        }
        return this;
    }

    /**
     * 平均用时
     * @return long 用时
     */
    public long getAverageUseTime() {
        if(loadCnt == 0) {
            return 0;
        }
        return this.useTotalTime / this.loadCnt;
    }

    public int getExpire() {
        return expire;
    }

    /**
     * @param expire expire
     * @return this
     */
    public AutoLoadWrapper setExpire(int expire) {
        this.expire=expire;
        return this;
    }


}
