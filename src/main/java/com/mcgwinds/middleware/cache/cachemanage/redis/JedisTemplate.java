package com.mcgwinds.middleware.cache.cachemanage.redis;

import com.mcgwinds.middleware.cache.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;

/**
 * Created by mcg on 2017/12/16.
 */
@Component
public class JedisTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTemplate.class);

    @Resource
    private JedisPool jedisPool;

    public <T> T execute(JedisTemplate.JedisAction<T> jedisAction) throws CacheException {
        Jedis jedis = null;
        T result;
        try {
            jedis = this.jedisPool.getResource();
            result = jedisAction.action(jedis);
        } catch (JedisException e) {
            throw new CacheException(e);
        } finally {
            this.closeResource(jedis);
        }

        return result;
    }

    public void closeResource(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }

    }

    public String get(String key) throws CacheException {
        return (String)this.execute((jedis) -> {
            return jedis.get(key);
        });
    }

    public void delete(String key) throws  CacheException {
        this.execute((jedis) -> {
            return jedis.del(key);
        });
    }


    public interface JedisAction<T> {
        T action(Jedis var1);
    }
}
