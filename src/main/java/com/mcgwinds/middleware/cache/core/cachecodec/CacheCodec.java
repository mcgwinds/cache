package com.mcgwinds.middleware.cache.core.cachecodec;


import com.mcgwinds.middleware.cache.core.exception.CacheCodecException;

/**
 * 缓存值的序列化
 * Created by mcg on 2018/2/5.
 */
public interface CacheCodec {

    byte[] encode(Object t) throws CacheCodecException;

    Object decode(byte[] bytes) throws CacheCodecException;
}
