package com.mcgwinds.middleware.cache.util;

import redis.clients.jedis.Jedis;

/**
 * Created by mcg on 2017/12/18.
 */
public class Test<T> {
    public static  void main(String [] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("key1","hello world");
        jedis.expire("key1",60);


    }


}
