package com.mcgwinds.middleware.cache.annotation;

import java.lang.annotation.*;

/**
 * Created by mcg on 2017/12/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface ParameterKey {

    String value() default "" ;
}
