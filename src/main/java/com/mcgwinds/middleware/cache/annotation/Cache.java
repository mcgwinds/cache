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

     BaseCache baseCache();

     /**
      * 缓存的操作类型：默认是READ_WRITE，先缓存取数据，如果没有数据则从DAO中获取并写入缓存；如果是WRITE则从DAO取完数据后，写入缓存
      * @return CacheOpType
      */
     CacheOPType getOPType() default CacheOPType.RE_WR;

     /**
      * 当autoload为true时，缓存数据在 requestTimeout 秒之内没有使用了，就不进行自动加载数据,如果requestTimeout为0时，会一直自动加载
      * @return long 请求过期
      */
     long requestTimeout() default 36000L;

     int expire() default 200;

     int alarmTime() default 0;

    /**
     * 是否启用自动加载缓存
     * @return boolean
     */
    boolean autoLoad() default false;


}
