package com.mcgwinds.middleware.cache.core.cacheresult;

/**
 * Created by mcg on 2018/2/5.
 */
public class CacheResults {

    public static <T> CacheResult<T> success(T data) {
        CacheResult<T> result = new CacheResult();
        result.setValue(data);
        return result;
    }

    public static <T> CacheResult<T> fail(int code, String error) {
        CacheResult<T> result = new CacheResult();
        result.setErrorMessage(code, error);
        return result;
    }
}
