package com.mcgwinds.middleware.cache.handler;

import com.alibaba.fastjson.JSON;
import com.mcgwinds.middleware.cache.annotation.BaseCache;
import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.annotation.DeleteCache;
import com.mcgwinds.middleware.cache.annotation.UpdateCache;
import com.mcgwinds.middleware.cache.autoload.AutoLoader;
import com.mcgwinds.middleware.cache.bean.AutoLoadWrapper;
import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.cachemanage.CacheManager;
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
    private CacheManager cacheManager;

    @Resource
    private CacheKeyFactory cacheKeyFactory;

    @Resource
    private AutoLoader autoLoader;

    public Object getDataProcess(Cache cache, ProceedingJoinPoint pjp) throws Throwable {

        LOGGER.info("getData is starting processing...");
        // 如果不进行缓存，则直接从数据源读
        BaseCache baseCache=cache.baseCache();
        if(baseCache==null|!baseCache.isCache()) {
            return getDataOfDataSource(pjp);
        }
        Method method= ClassUtil.getMethod(pjp);
        Object [] arguments=ClassUtil.getArgs(pjp);
        Parameter[] parameters=ClassUtil.getParameter(pjp);
        CacheKey cacheKey=getCacheKey(baseCache,method,arguments,parameters);
        CacheOPType opType=cache.getOPType();
        CacheWrapper<Object> cacheWrapper=null;
        try {
            cacheWrapper=this.getDataOfCache(cacheKey, method, arguments);// 从缓存中获取数据
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        LOGGER.warn("Cache key:{}, Cache data is null {} ", cacheKey, null == cacheWrapper);

        if(opType == CacheOPType.RE_ONLY) {

        if(cache.autoLoad()&&null != cacheWrapper && !cacheWrapper.isExpired()) {
            AutoLoadWrapper autoLoadWrapper=autoLoader.putIfAbsent(cacheKey, cache, pjp,cacheWrapper);
            if(null != autoLoadWrapper) {// 同步最后加载时间
                autoLoadWrapper.setLastRequestTime(System.currentTimeMillis())//
                        .setLastLoadTime(cacheWrapper.getLastLoadTime())// 同步加载时间
                        .setExpire(cacheWrapper.getExpire());// 同步过期时间
            } else {// 如果缓存快要失效，则自动刷新
                //refreshHandler.doRefresh(pjp, cache, cacheKey, cacheWrapper);
            }

        }
            return  null == cacheWrapper ? null : cacheWrapper.getCacheObject();
        }

        CacheWrapper<Object> newCacheWrapper=null;
        long loadDataUseTime=0;
        try {
            long loadDataStartTime=System.currentTimeMillis();
            Object loadData=pjp.proceed(arguments);
            loadDataUseTime=System.currentTimeMillis() - loadDataStartTime;
            newCacheWrapper=new CacheWrapper<Object>(loadData, cache.expire());
        } catch(Throwable e) {
            throw e;
        }
        //写入缓存
        writeCache(pjp, pjp.getArgs(), cache, cacheKey, newCacheWrapper);

        if(cache.autoLoad()) {
            AutoLoadWrapper autoLoadWrapper = autoLoader.putIfAbsent(cacheKey, cache, pjp, newCacheWrapper);
            try {
                if (null != autoLoadWrapper) {// 同步最后加载时间
                    autoLoadWrapper.setLastRequestTime(System.currentTimeMillis())//
                            .setLastLoadTime(newCacheWrapper.getLastLoadTime())// 同步加载时间
                            .setExpire(newCacheWrapper.getExpire())// 同步过期时间
                            .addUseTotalTime(loadDataUseTime);// 统计用时
                }
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

        return newCacheWrapper.getCacheObject();

    }

    public void updateCacheProcess(UpdateCache updateCache, ProceedingJoinPoint pjp) throws Throwable{

        BaseCache baseCache=updateCache.baseCache();
        if(baseCache==null|!baseCache.isCache()) {
            return ;
        }
        Method method= ClassUtil.getMethod(pjp);
        Object [] arguments=ClassUtil.getArgs(pjp);
        Parameter[] parameters=ClassUtil.getParameter(pjp);
        CacheKey cacheKey=getCacheKey(baseCache,method,arguments,parameters);
    }

    public void deleteCacheProcess(DeleteCache deleteCache, ProceedingJoinPoint pjp)throws Throwable {
        BaseCache baseCache=deleteCache.baseCache();
        if(baseCache==null|!baseCache.isCache()) {
            return ;
        }
        Method method= ClassUtil.getMethod(pjp);
        Object [] arguments=ClassUtil.getArgs(pjp);
        Parameter[] parameters=ClassUtil.getParameter(pjp);
        CacheKey cacheKey=getCacheKey(baseCache,method,arguments,parameters);
    }



    private void writeDataToCache(CacheKey cacheKey, Method method, Object[] arguments) {
        if(null==cacheKey) {
            LOGGER.warn("the cache key is null");
            return;
        }
        cacheManager.deleteDataOfCache(cacheKey,method,arguments);
        //自动加载关闭


    }

    public CacheWrapper<Object> getDataOfCache(CacheKey cacheKey, Method method, Object[] arguments) {
        if(null==cacheKey) {
            LOGGER.warn("the cache key is null");
            return null;
        }
        LOGGER.info("get data from cache...,the cache key:{}; method:{}", JSON.toJSONString(cacheKey), JSON.toJSONString(method));
        return cacheManager.getDataOfCache(cacheKey,method,arguments);
    }

    private void deleteDataOfCache(Cache cache,CacheKey cacheKey, Method method, Object[] arguments) {
        if(null==cacheKey) {
            LOGGER.warn("the cache key is null");
            return;
        }
        LOGGER.info("delete data from cache...,the cache key:{}; method:{}", JSON.toJSONString(cacheKey), JSON.toJSONString(method));
        boolean autoLoad=true;
        //对于删除失败的执行自动加载，防止缓存的脏数据
        autoLoad=cacheManager.deleteDataOfCache(cacheKey,method,arguments);
        //删除成功以及不开启自动加载模式
        if(!cache.autoLoad()&!autoLoad) {
             return;
         }
        //开启自动加载


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

    private CacheKey getCacheKey(BaseCache cache, Method method,Object [] arguments,Parameter[] parameters) {
        return cacheKeyFactory.getCacheKey(cache,method,arguments,parameters);
    }

    public void writeCache(ProceedingJoinPoint pj, Object[] arguments, Cache cache, CacheKey cacheKey, CacheWrapper<Object> newCacheWrapper) {


    }



}
