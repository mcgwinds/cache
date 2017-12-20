package com.mcgwinds.middleware.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by mcg on 2017/12/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DeleteCache {
    BaseCache baseCache();
}
