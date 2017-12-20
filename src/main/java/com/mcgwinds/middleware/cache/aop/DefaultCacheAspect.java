package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.annotation.DeleteCache;
import com.mcgwinds.middleware.cache.annotation.UpdateCache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */
public class DefaultCacheAspect extends CacheAspect {

    public Object getData(ProceedingJoinPoint pjp) throws Throwable {

         Method method = getMethod(pjp);
         if(method.isAnnotationPresent(Cache.class)) {
             Cache cache = method.getAnnotation(Cache.class);
             return cacheHandler.getDataProcess(cache,pjp);
         }
        return proceed(pjp);

    }

    //监听binlog
    public void updateCache(ProceedingJoinPoint pjp) throws Throwable {

        Method method = getMethod(pjp);
        if(method.isAnnotationPresent(UpdateCache.class)) {
            UpdateCache updateCache = method.getAnnotation(UpdateCache.class);
            cacheHandler.updateCacheProcess(updateCache,pjp);
        }
            proceed(pjp);

    }


    //用于先更新数据库然后删除缓存的场景
    public void deleteCache(ProceedingJoinPoint pjp) throws Throwable {

        Method method = getMethod(pjp);
        if(method.isAnnotationPresent(DeleteCache.class)) {
            DeleteCache deleteCache = method.getAnnotation(DeleteCache.class);
             cacheHandler.deleteCacheProcess(deleteCache,pjp);
        }
             proceed(pjp);

    }


}
