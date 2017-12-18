package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.annotation.Cache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */
public class KeyCacheAspect extends CacheAspect {

    public Object getData(ProceedingJoinPoint pjp) throws Throwable {

         Method method = getMethod(pjp);
         if(method.isAnnotationPresent(Cache.class)) {
             Cache keyCache = method.getAnnotation(Cache.class);
             return cacheHandler.proceed(keyCache,pjp);
         }
        return proceed(pjp);

    }

    //用于先更新数据库然后删除缓存的场景
    public void deleteCache(ProceedingJoinPoint pjp) throws Throwable {

        Method method = getMethod(pjp);
        if(method.isAnnotationPresent(Cache.class)) {
             Cache keyCache = method.getAnnotation(Cache.class);
             cacheHandler.proceed(keyCache,pjp);
        }
        proceed(pjp);

    }


}
