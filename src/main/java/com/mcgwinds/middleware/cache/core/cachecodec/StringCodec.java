package com.mcgwinds.middleware.cache.core.cachecodec;


import com.mcgwinds.middleware.cache.core.exception.CacheCodecException;

import java.nio.charset.Charset;

/**
 * String序列化方式
 * Created by mcg on 2018/2/5.
 */

public class StringCodec implements CacheCodec {

    private final Charset charset;

    public StringCodec() {

        this(Charset.forName("UTF8"));
    }

    public StringCodec(Charset charset) {

        this.charset=charset;
    }

    @Override
    public byte[] encode(Object s) throws CacheCodecException {
        return (s == null ? null : ((String)s).getBytes(charset));
    }

    @Override
    public Object decode(byte[] bytes) throws CacheCodecException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
