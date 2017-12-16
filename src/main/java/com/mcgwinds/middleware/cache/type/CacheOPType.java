package com.mcgwinds.middleware.cache.type;

/**
 * Created by mcg on 2017/12/16.
 */
public enum CacheOPType {

    //从数据源加载
    LOAD,

    //只从缓存中读，用于只读场景
    READ_ONLY,

    //从缓存中加载最新的数据，写入缓存
    WRITE_ONLY,

    //读写缓存操:如果缓存中有数据，则使用缓存中的数据，如果缓存中没有数据，则加载数据，并写入缓存。
    READ_WRITE;
}
