package com.mcgwinds.middleware.cache.type;

/**
 * Created by mcg on 2017/12/16.
 */
public enum CacheOPType {

    //只从缓存中读，用于只读场景
    RE_ONLY,

    //读写缓存操:如果缓存中有数据，则使用缓存中的数据，如果缓存中没有数据，则加载数据，并写入缓存。
    RE_WR;
}
