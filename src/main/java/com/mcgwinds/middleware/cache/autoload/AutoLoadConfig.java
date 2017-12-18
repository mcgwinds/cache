package com.mcgwinds.middleware.cache.autoload;

import com.mcgwinds.middleware.cache.type.CompareType;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcg on 2017/12/18.
 */

public class AutoLoadConfig {

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
