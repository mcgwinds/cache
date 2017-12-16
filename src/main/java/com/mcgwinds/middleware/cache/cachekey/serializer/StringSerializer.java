package com.mcgwinds.middleware.cache.cachekey.serializer;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by mcg on 2017/12/16.
 */
public class StringSerializer implements Serializer<String> {

    private final String charsetName;

    public StringSerializer() {
        this("UTF8");
    }

    public StringSerializer(String charsetName) {
        this.charsetName=charsetName;
    }

    public byte[] serialize(String obj) throws Exception {

        return(obj == null ? null : obj.getBytes(charsetName));
    }

    public String deserialize(byte[] bytes, Type returnType) throws Exception {
        return(bytes == null ? null : new String(bytes, charsetName));
    }
}
