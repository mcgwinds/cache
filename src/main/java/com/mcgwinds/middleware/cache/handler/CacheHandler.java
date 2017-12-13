package com.mcgwinds.middleware.cache.handler;

import com.mcgwinds.middleware.cache.annotation.KeyCache;
import com.mcgwinds.middleware.cache.cachemanage.CacheManager;
import com.mcgwinds.middleware.cache.datasource.DataSourceManager;
import com.mcgwinds.middleware.cache.script.AbstractScriptParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mcg on 2017/12/12.
 */
public class CacheHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheHandler.class);

    private AbstractScriptParser scriptParser;

    private DataSourceManager dataSourceManager;

    private CacheManager cacheManager;

    public Object proceed(KeyCache cache,ProceedingJoinPoint pjp) throws Throwable {

        Object[] arguments=pjp.getArgs();
        // 如果不进行缓存，则直接返回数据
        if(!scriptParser.isCacheable(cache, arguments)) {
            return getData(pjp);
        }


        return null;

    }

    private Object getData(ProceedingJoinPoint pjp) {
        return null;
    }

}
