package com.mcgwinds.middleware.cache.core.cachecodec;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mcgwinds.middleware.cache.core.exception.CacheCodecException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * FastJson序列化方式
 * Created by mcg on 2018/2/5.
 */
@Component
@Service("fastJson")
public class FastJsonCodec implements CacheCodec {

    FastJsonCodec() {

    }

    @Override
    public byte[] encode(Object value) throws CacheCodecException {
        return JSON.toJSONBytes(value, SerializerFeature.WriteClassName);
    }

    @Override
    public Object decode(byte[] bytes) throws CacheCodecException {
        return JSON.parse(bytes);
    }
}
