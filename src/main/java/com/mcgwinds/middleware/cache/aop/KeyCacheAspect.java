package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.annotation.KeyCache;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */
public class KeyCacheAspect extends CacheAspect {

    public Object process(ProceedingJoinPoint pjp) throws Throwable {

         Method method = getMethod(pjp);
         if(method.isAnnotationPresent(KeyCache.class)) {
             KeyCache keyCache = method.getAnnotation(KeyCache.class);
             proceed(keyCache,pjp);
         }
        return proceed(pjp);

    }

    public Object proceed(KeyCache cache,ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments=pjp.getArgs();
        return null;
    }

}
