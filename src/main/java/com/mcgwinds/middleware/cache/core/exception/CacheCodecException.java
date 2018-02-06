package com.mcgwinds.middleware.cache.core.exception;

/**
 * Created by mcg on 2018/2/5.
 */
public class CacheCodecException extends CacheException {

    public CacheCodecException(String msg) {
        super(msg);
    }

    public CacheCodecException(Throwable cause) {
        super(cause);
    }

    public CacheCodecException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
