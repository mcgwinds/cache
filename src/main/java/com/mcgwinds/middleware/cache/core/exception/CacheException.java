package com.mcgwinds.middleware.cache.core.exception;

/**
 * Created by mcg on 2018/2/4.
 */
public class CacheException extends RuntimeException {


    public  CacheException(String msg) {
        super(msg);
    }

   public  CacheException(Throwable cause) {
        super(cause);
    }

    public  CacheException(String msg,Throwable cause) {
        super(msg,cause);
    }
}
