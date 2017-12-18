package com.mcgwinds.middleware.cache.cachekey.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by mcg on 2017/12/17.
 */
public class FastJsonSerializer implements Serializer{

    private final String charsetName;

    public FastJsonSerializer() {
        this("UTF8");
    }

    public FastJsonSerializer(String charsetName) {
        this.charsetName=charsetName;
    }


    @Override
    public byte[] serialize(Object obj) throws Exception {
        if(obj == null) {
            return null;
        }
        String json= JSON.toJSONString(obj);
        return json.getBytes(charsetName);
    }

    @Override
    public Object deserialize(byte[] bytes, TypeReference returnType) throws Exception {
        if(null == bytes || bytes.length == 0) {
            return null;
        }
        String json=new String(bytes, charsetName);

        return JSON.parseObject(json, returnType);

    }
}
