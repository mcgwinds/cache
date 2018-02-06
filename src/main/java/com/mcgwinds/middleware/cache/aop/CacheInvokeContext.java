package com.mcgwinds.middleware.cache.aop;


import com.mcgwinds.middleware.cache.core.cache.Cache;
import com.mcgwinds.middleware.cache.core.cachekey.CacheKey;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Created by mcg on 2018/1/23.
 */
public class CacheInvokeContext {

    private Invoker invoker;
    private Method method;
    private Object[] args;
    private CacheInvokeConfig cacheInvokeConfig;
    private Object result;

    Function<CacheInvokeContext, Cache> cacheFunction;

    Function<CacheInvokeContext, CacheKey> cacheKeyFunction;

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public CacheInvokeConfig getCacheInvokeConfig() {
        return cacheInvokeConfig;
    }

    public void setCacheInvokeConfig(CacheInvokeConfig cacheInvokeConfig) {
        this.cacheInvokeConfig = cacheInvokeConfig;
    }

    public Function<CacheInvokeContext, Cache> getCacheFunction() {
        return cacheFunction;
    }

    public void setCacheFunction(Function<CacheInvokeContext, Cache> cacheFunction) {
        this.cacheFunction = cacheFunction;
    }

    public Object getResult() {
            return result;
        }

    public void setResult(Object result) {
            this.result = result;
        }

    public Function<CacheInvokeContext, CacheKey> getCacheKeyFunction() {
        return cacheKeyFunction;
    }

    public void setCacheKeyFunction(Function<CacheInvokeContext, CacheKey> cacheKeyFunction) {
        this.cacheKeyFunction = cacheKeyFunction;
    }
}
