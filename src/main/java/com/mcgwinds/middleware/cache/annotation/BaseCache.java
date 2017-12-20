package com.mcgwinds.middleware.cache.annotation;

import com.mcgwinds.middleware.cache.type.CacheOPType;

import java.lang.annotation.*;

/**
 * Created by mcg on 2017/12/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BaseCache {

    /**
     * 是否是进行缓存操作
     * @return boolean
     */
    boolean isCache() default false;

    /**
     * 命名空间可以作为不同的业务
     * @return boolean
     */
    String namespace();

    /**
     * 设置缓存key的缓存头
     * @return String
     */
    String getCacheKeyHeader() default "mcg";


}
