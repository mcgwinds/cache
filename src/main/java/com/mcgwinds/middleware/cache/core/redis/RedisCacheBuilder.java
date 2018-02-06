package com.mcgwinds.middleware.cache.core.redis;


import com.mcgwinds.middleware.cache.core.cachebuilder.AbstractCacheBuilder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * redisCacheBuilder
 * Created by mcg on 2018/1/30.
 */
@Service("redis")
public class RedisCacheBuilder extends AbstractCacheBuilder {

    private Pool<Jedis> jedisPool;

    public  RedisCacheBuilder() {

        this.cachefunction = cacheContextConfig -> {
            RedisCacheContextConfig redisCacheContextConfig=new RedisCacheContextConfig();
            redisCacheContextConfig.setCacheCodec(cacheContextConfig.getCacheCodec());
            redisCacheContextConfig.setExpireAfterWriteInMillis(cacheContextConfig.getExpireAfterWriteInMillis());
            redisCacheContextConfig.setJedisPool(jedisPool);
            RedisCache redisCache = new RedisCache(redisCacheContextConfig);
            return redisCache;

        };

    }

    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

}
