package com.mcgwinds.middleware.cache.core.cacheresult;

/**
 * Created by mcg on 2018/2/4.
 */
public enum CacheResultCode {

    SUCCESS(100000,"success"), FAIL(1000001,"fail"), NOT_EXISTS(1000002,"not exists"), EXISTS(1000003,"exist"), EXPIRED(1000004,"expired");

    public final int code;

    public final String message;

    CacheResultCode(int code,String message) {
        this.code=code;
        this.message=message;
    }

}
