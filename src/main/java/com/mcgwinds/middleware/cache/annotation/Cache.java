package com.mcgwinds.middleware.cache.annotation;

import com.mcgwinds.middleware.cache.type.CacheOPType;

import java.lang.annotation.*;

/**
 * Created by mcg on 2017/12/12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Cache {

     /**
      * 是否是进行缓存操作
      * @return boolean
      */
     boolean isCache() default false;

     /**
      * 缓存的操作类型：默认是READ_WRITE，先缓存取数据，如果没有数据则从DAO中获取并写入缓存；如果是WRITE则从DAO取完数据后，写入缓存
      * @return CacheOpType
      */
     CacheOPType getOPType() default CacheOPType.READ_WRITE;

     /**
      * 设置缓存key的缓存头
      * @return String
      */
     String getCacheKeyHeader() default "";

}
