package com.mcgwinds.middleware.cache.core.redis;


import com.mcgwinds.middleware.cache.core.cache.AbstractCache;
import com.mcgwinds.middleware.cache.core.cache.CacheValueHolder;
import com.mcgwinds.middleware.cache.core.cachecodec.CacheCodec;
import com.mcgwinds.middleware.cache.core.cacheconfig.CacheContextConfig;
import com.mcgwinds.middleware.cache.core.cacheresult.CacheResult;
import com.mcgwinds.middleware.cache.core.cacheresult.CacheResultCode;
import com.mcgwinds.middleware.cache.core.cacheresult.CacheResults;
import com.mcgwinds.middleware.cache.core.exception.CacheConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.concurrent.TimeUnit;

/**
 * Created by mcg on 2018/1/30.
 */
public class RedisCache<K,V>  extends AbstractCache<K,V> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private RedisCacheContextConfig config;

    private CacheCodec cacheCodec;

    private Pool<Jedis> pool;

    public RedisCache(RedisCacheContextConfig config) {

        this.config = config;
        this.pool = config.getJedisPool();
        this.cacheCodec=config.getCacheCodec();

        if (pool == null) {
            throw new CacheConfigException("no pool");
        }
    }

    @Override
    public CacheResult<V> doGet(K key) {
        try (Jedis jedis = pool.getResource()) {
            byte[] newKey = buildKey(key);
            byte[] bytes = jedis.get(newKey);
            if (bytes != null) {
                CacheValueHolder<V> holder = (CacheValueHolder<V>) cacheCodec.decode(bytes);
                if (System.currentTimeMillis() >= holder.getExpireTime()) {
                    return CacheResults.fail(CacheResultCode.EXPIRED.code,CacheResultCode.EXPIRED.message);
                }
                 return CacheResults.success(holder.getValue());
            } else {
                return CacheResults.success(null);
            }
        } catch (Exception ex) {
            logger.error("GET", key, ex);
            return CacheResults.fail(CacheResultCode.FAIL.code,CacheResultCode.FAIL.message);
        }

    }

    public byte[] buildKey(K key) {

        byte[] newKey = null;
            if (key instanceof byte[]) {
                newKey = (byte[])key;

            } else if(key instanceof String){
                newKey = ((String) key).getBytes();
            }

            return  newKey;

    }

    @Override
    public CacheResult doPut(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        try (Jedis jedis = pool.getResource()) {
            CacheValueHolder<V> holder = new CacheValueHolder(value, timeUnit.toMillis(expireAfterWrite));
            byte[] newKey = buildKey(key);
            String rt = jedis.psetex(newKey, timeUnit.toMillis(expireAfterWrite), cacheCodec.encode(holder));
            if ("OK".equals(rt)) {
                return CacheResults.success(null);
            } else {
                 return  CacheResults.fail(CacheResultCode.FAIL.code,CacheResultCode.FAIL.message);
            }
        } catch (Exception ex) {
            logger.error("PUT", key, ex);
            return  CacheResults.fail(CacheResultCode.FAIL.code,CacheResultCode.FAIL.message);
        }
    }

    @Override
    public CacheResult do_Put_If_Absent(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {

        try (Jedis jedis = pool.getResource()) {
            CacheValueHolder<V> holder = new CacheValueHolder(value, timeUnit.toMillis(expireAfterWrite));
            byte[] newKey = buildKey(key);
            String rt = jedis.set(newKey, cacheCodec.encode(holder), "NX".getBytes(), "PX".getBytes(), timeUnit.toMillis(expireAfterWrite));
            if ("OK".equals(rt)) {
                return CacheResults.success(null);
            } else {
                return  CacheResults.fail(CacheResultCode.FAIL.code,CacheResultCode.FAIL.message);
            }
        } catch (Exception ex) {
            logger.error("PUT_IF_ABSENT", key, ex);
            return  CacheResults.fail(CacheResultCode.FAIL.code,CacheResultCode.FAIL.message);
        }
    }

    @Override
    public CacheContextConfig config() {
        return config;
    }
}
