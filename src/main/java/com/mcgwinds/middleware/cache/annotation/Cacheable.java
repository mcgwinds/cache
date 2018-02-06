package com.mcgwinds.middleware.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by mcg on 2018/1/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface Cacheable {

    /**
     * 是否是进行缓存操作
     * @return boolean
     */
    boolean isCache() default true;

    /**
     * 命名空间可以作为不同的业务
     * @return String
     */
    String namespace() default "sam";

    /**
     * 缓存类型 默认redis
     * @return String
     */
    String cacheType() default "redis";

    /**
     * 缓存名字
     * @return String
     */
    String name()  default "demo";

    /**
     * 序列化策略
     * @return String
     */
    String serialPolicy() default "fastJson";

    /**
     * 失效时间
     * @return long
     */
    long expireAfterWriteInMillis() default 1000l;

    /**
     * 性能统计
     * @return boolean
     */
    boolean isStat() default false;


}
