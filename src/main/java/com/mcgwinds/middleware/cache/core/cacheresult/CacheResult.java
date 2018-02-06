package com.mcgwinds.middleware.cache.core.cacheresult;

import java.io.Serializable;

/**
 * Created by mcg on 2018/1/29.
 */
public class CacheResult<V> implements Serializable {


    private boolean success;

    private  int code;

    private String message;

    private V value;


    public CacheResult() {
        this.code = CacheResultCode.SUCCESS.code;
        this.success = true;
        this.message = CacheResultCode.SUCCESS.message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setErrorMessage(int code, String message) {
        this.code=code;
        this.success = false;
        this.message=message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
