package com.mcgwinds.middleware.cache.aop;

/**
 * Created by mcg on 2018/1/29.
 */
public interface Invoker {

    Object invoke() throws Throwable;
}
