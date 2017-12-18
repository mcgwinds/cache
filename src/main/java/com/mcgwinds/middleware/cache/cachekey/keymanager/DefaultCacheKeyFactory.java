package com.mcgwinds.middleware.cache.cachekey.keymanager;

import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.annotation.ParameterKey;
import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.cachekey.compress.Compressor;
import com.mcgwinds.middleware.cache.util.Constants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by mcg on 2017/12/14.
 */
public class DefaultCacheKeyFactory implements CacheKeyFactory {

    /**
     *获取缓存key
     * @return CacheKey
     */
    public CacheKey getCacheKey(Cache cache, Method method,Object [] arguments,Parameter[] parameters) {
        String namespace=cache.namespace();
        String cacheKeyHeader=cache.getCacheKeyHeader();
        KeyMap<String,String> paramKeyMap=new KeyMap<String,String>();
        parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation parameterAnnotation : annotations) {
                if (parameterAnnotation instanceof ParameterKey) {
                    String key = ((ParameterKey) parameterAnnotation).value();
                    Object object = arguments[i];
                    if (object == null) {
                        object = Constants.Nil;
                    }
                    String value = object.toString();
                    paramKeyMap.put(key, value);
                }
            }
        }
        CacheKey cacheKey=new CacheKey(namespace,cacheKeyHeader,paramKeyMap.toString());
        return cacheKey;
    }
}
