package com.mcgwinds.middleware.cache.core.redis;


import com.mcgwinds.middleware.cache.core.cacheconfig.CacheContextConfig;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * Created by mcg on 2018/2/5.
 */
public class RedisCacheContextConfig extends CacheContextConfig {

    private Pool<Jedis> jedisPool;

    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }
}
