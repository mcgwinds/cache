package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.handler.CacheHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by mcg on 2017/12/12.
 */

public class CacheAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheAspect.class);

    protected CacheHandler cacheHandler;

    protected Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        //获取参数的类型
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        return pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), method.getParameterTypes());

    }

    protected Object[] getArgs(ProceedingJoinPoint pjp) {
        return pjp.getArgs();
    }

    protected Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch(Throwable e) {
            LOGGER.warn("process error");
            throw e;
        }
    }
}
