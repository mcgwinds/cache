package com.mcgwinds.middleware.cache.autoload;

import com.mcgwinds.middleware.cache.type.CompareType;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcg on 2017/12/18.
 */

public class AutoLoadConfig {

    /**
     * 自动加载最小过期时间
     */
    public static final int AUTO_LOAD_MIN_EXPIRE=120;

    //触发自动加载时间
    private int expire;

    //自动加载线程数
    private int autoLoadThreads;

    //是否采用线程池方式
    private ExecutorService executorService;

    //自动加载队列大小
    private int queneSize;

    //自动加载队列比较
    private CompareType compareType;

    /**
     * 单个线程中执行自动加载的时间间隔
     */
    private int autoLoadPeriod=50;

    //在加载数据之前检查缓存是否已经被更新
    private boolean isBeforeLoadCheckFromCache=true;

    public boolean isBeforeLoadCheckFromCache() {
        return isBeforeLoadCheckFromCache;
    }

    public void setBeforeLoadCheckFromCache(boolean beforeLoadCheckFromCache) {
        isBeforeLoadCheckFromCache = beforeLoadCheckFromCache;
    }

    public int getAutoLoadPeriod() {
        return autoLoadPeriod;
    }

    public void setAutoLoadPeriod(int autoLoadPeriod) {
        this.autoLoadPeriod = autoLoadPeriod;
    }

    public int getQueneSize() {
        return queneSize;
    }

    public void setQueneSize(int queneSize) {
        this.queneSize = queneSize;
    }

    public int getAutoLoadThreads() {
        return autoLoadThreads;
    }

    public void setAutoLoadThreads(int autoLoadThreads) {
        this.autoLoadThreads = autoLoadThreads;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }
}
