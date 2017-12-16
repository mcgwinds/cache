package com.mcgwinds.middleware.cache.annotation;

/**
 * Created by mcg on 2017/12/16.
 */
public @interface ParameterKey {

    String value() default "" ;
}
