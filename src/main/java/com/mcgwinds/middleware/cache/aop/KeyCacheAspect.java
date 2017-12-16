package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.annotation.Cache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */
public class KeyCacheAspect extends CacheAspect {

    public Object process(ProceedingJoinPoint pjp) throws Throwable {

         Method method = getMethod(pjp);
         if(method.isAnnotationPresent(Cache.class)) {
             Cache keyCache = method.getAnnotation(Cache.class);
             return cacheHandler.proceed(keyCache,pjp);
         }
        return proceed(pjp);

    }

}
