package com.mcgwinds.middleware.cache.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mcg on 2017/12/16.
 */
public class ClassUtil {

    public static Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        return pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), method.getParameterTypes());

    }

    public static Object[] getArgs(ProceedingJoinPoint pjp) {

        return pjp.getArgs();
    }

    public static Parameter[] getParameter(ProceedingJoinPoint pjp) throws NoSuchMethodException{
        return getMethod(pjp).getParameters();
    }

    public static Class getReturnType(Method method) {
        return method.getReturnType();
    }



}
