package com.mcgwinds.middleware.cache.cachekey.serializer;

import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;

/**
 * Created by mcg on 2017/12/16.
 */
public interface Serializer<T> {

    /**
     * 序列化.
     * @param obj
     * @return byte[]
     * @throws Exception
     */
    byte[] serialize(final T obj) throws Exception;

    /**
     * 反序列化
     * @param bytes
     * @param returnType 返回类型
     * @return T
     * @throws Exception
     */
    T deserialize(final byte[] bytes, final TypeReference returnType) throws Exception;
}
