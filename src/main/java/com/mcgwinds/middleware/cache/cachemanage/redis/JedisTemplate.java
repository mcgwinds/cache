package com.mcgwinds.middleware.cache.cachemanage.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by mcg on 2017/12/16.
 */
@Component
public class JedisTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTemplate.class);

    @Resource
    private JedisPool jedisPool;


}
