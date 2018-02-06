package com.mcgwinds.middleware.cache.core.cachekey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mcg on 2018/2/4.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ListParamKey {
    String value();
}
