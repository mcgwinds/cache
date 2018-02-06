package com.mcgwinds.middleware.cache.core.exception;

/**
 * Created by mcg on 2018/1/29.
 */
public class CacheInvokeException extends CacheException {


    public CacheInvokeException(String msg) {
        super(msg);
    }

    public CacheInvokeException(Throwable cause) {
        super(cause);
    }

    public CacheInvokeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
