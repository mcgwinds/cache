package com.mcgwinds.middleware.cache.handler;

import com.alibaba.fastjson.JSON;
import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.cachemanage.CacheManager;
import com.mcgwinds.middleware.cache.datasourcemanager.DataSourceManager;
import com.mcgwinds.middleware.cache.cachekey.keymanager.CacheKeyFactory;
import com.mcgwinds.middleware.cache.type.CacheOPType;
import com.mcgwinds.middleware.cache.util.ClassUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mcg on 2017/12/12.
 */
@Component
public class CacheHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheHandler.class);

    @Resource
    private DataSourceManager dataSourceManager;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private CacheKeyFactory cacheKeyFactory;

    public Object proceed(Cache cache, ProceedingJoinPoint pjp) throws Throwable {

        LOGGER.info("cacheHandler is starting processing...");
        // 如果不进行缓存，则直接从数据源读
        if(!cache.isCache()) {
            return getDataOfDataSource(pjp);
        }
        CacheOPType opType=cache.getOPType();

        if(CacheOPType.WR_ONLY==opType) {

        }
        Method method= ClassUtil.getMethod(pjp);
        Object [] arguments=ClassUtil.getArgs(pjp);
        Parameter[] parameters=ClassUtil.getParameter(pjp);
        CacheKey cacheKey=getCacheKey(cache,method,arguments,parameters);
        if(CacheOPType.DEL==opType) {
            this.deleteDataOfCache(cacheKey, method, arguments); //删除缓存
        }
        CacheWrapper<Object> cacheWrapper=null;
        try {
            cacheWrapper=this.getDataOfCache(cacheKey, method, arguments);// 从缓存中获取数据
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        LOGGER.warn("Cache key:{}, Cache data is null {} ", cacheKey, null == cacheWrapper);

        if(opType == CacheOPType.RE_ONLY) {
            return null == cacheWrapper ? null : cacheWrapper.getCacheObject();
        }




        return null;

    }

    private CacheWrapper<Object> getDataOfCache(CacheKey cacheKey, Method method, Object[] arguments) {
        if(null==cacheKey) {
            LOGGER.warn("the cache key is null");
            return null;
        }
        LOGGER.info("get data from cache...,the cache key:{}; method:{}", JSON.toJSONString(cacheKey), JSON.toJSONString(method));
        return cacheManager.getDataOfCache(cacheKey,method,arguments);
    }

    private void deleteDataOfCache(CacheKey cacheKey, Method method, Object[] arguments) {
        if(null==cacheKey) {
            LOGGER.warn("the cache key is null");
            return;
        }
        LOGGER.info("delete data from cache...,the cache key:{}; method:{}", JSON.toJSONString(cacheKey), JSON.toJSONString(method));
         cacheManager.deleteDataOfCache(cacheKey,method,arguments);
         //自动加载
    }

    //从dao中读取数据
    private Object getDataOfDataSource(ProceedingJoinPoint pjp) throws Throwable {

        try {
            long startTime=System.currentTimeMillis();
            Object result=pjp.proceed();
            long useTime=System.currentTimeMillis() - startTime;
            LOGGER.info("get data from dataSource is used time {}" ,useTime);
            return result;
        } catch(Throwable e) {
            throw e;
        }
    }

    private CacheKey getCacheKey(Cache cache, Method method,Object [] arguments,Parameter[] parameters) {
        return cacheKeyFactory.getCacheKey(cache,method,arguments,parameters);
    }

}
