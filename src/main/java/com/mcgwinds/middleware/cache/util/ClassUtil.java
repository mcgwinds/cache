package com.mcgwinds.middleware.cache.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.asm.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mcg on 2018/1/23.
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

    public static String getKey(Method method, Class targetClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append('.');
        sb.append(method.getName());
        sb.append(Type.getMethodDescriptor(method));
        if (targetClass != null) {
            sb.append('_');
            sb.append(targetClass.getName());
        }
        return sb.toString();
    }


}
