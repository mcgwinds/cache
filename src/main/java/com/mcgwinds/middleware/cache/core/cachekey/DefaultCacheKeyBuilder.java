package com.mcgwinds.middleware.cache.core.cachekey;


import com.mcgwinds.middleware.cache.aop.CacheInvokeContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.function.Function;

/**
 * Created by mcg on 2018/2/4.
 */
public class DefaultCacheKeyBuilder implements CacheKeyBuilder {

    private Function<CacheInvokeContext, CacheKey> cacheKeyFunction;

    private CacheInvokeContext cacheInvokeContext;

    private String fullCacheName;

    public CacheInvokeContext getCacheInvokeContext() {
        return cacheInvokeContext;
    }

    public void setCacheInvokeContext(CacheInvokeContext cacheInvokeContext) {
        this.cacheInvokeContext = cacheInvokeContext;
    }

    public String getFullCacheName() {
        return fullCacheName;
    }

    public void setFullCacheName(String fullCacheName) {
        this.fullCacheName = fullCacheName;
    }

    public DefaultCacheKeyBuilder() {
        cacheKeyFunction = (cacheInvokeContext) -> {

            KeyMap<String, String> paramMap = new KeyMap<String, String>();
            Method method = cacheInvokeContext.getMethod();
            Parameter[] parameters = method.getParameters();
            Object[] args = cacheInvokeContext.getArgs();
            List listParam = null;
            //获取参数中的list参数
            String listKey = null;
            int listIndex = 0;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation anno : annotations) {
                    if (anno instanceof ParamKey) {
                        String key = ((ParamKey) anno).value();
                        String value = args[i].toString();
                        paramMap.put(key, value);
                    } else if (anno instanceof ListParamKey) {
                        listParam = (List) args[i];
                        listKey = ((ListParamKey) anno).value();
                        listIndex = i;
                    }
                }
            }
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(fullCacheName);
            stringBuilder.append(paramMap.toString());
            CacheKey cacheKey=new CacheKey(stringBuilder.toString());

            return cacheKey;

        };

    }

    @Override
    public  CacheKey buildCacheKey() {

        return cacheKeyFunction.apply(cacheInvokeContext);

    }

}

