package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.core.cache.Cache;
import com.mcgwinds.middleware.cache.core.cachekey.CacheKey;
import com.mcgwinds.middleware.cache.core.cacheresult.CacheResult;
import com.mcgwinds.middleware.cache.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by mcg on 2018/1/23.
 */
@Component
public class CacheHandler {

    private static Logger logger = LoggerFactory.getLogger(CacheHandler.class);


    public  Object invoke(CacheInvokeContext context) throws Throwable {
        CacheInvokeConfig annoCacheConfig = context.getCacheInvokeConfig();
        if (annoCacheConfig != null && (annoCacheConfig.isCache())) {
            return invokeWithCache(context);
        } else {
            return invokeOrigin(context);
         }
        }


    private static Object invokeWithCache(CacheInvokeContext context)
            throws Throwable {

        CacheKey cachekey = context.getCacheKeyFunction().apply(context);

        if (cachekey == null) {
            logger.error("no Cachekey with name: " + context.getMethod());
            return invokeOrigin(context);
        }

        Cache cache = context.getCacheFunction().apply(context);
        if (cache == null) {
            logger.error("no cache with name: " + context.getMethod());
            return invokeOrigin(context);
        }

        CacheResult cacheResult = cache.get(cachekey.getKey());

        if(cacheResult.isSuccess()) {
            if(null==cacheResult.getValue()) {
               Object tmp= invokeOrigin(context);
               Object result=tmp;
               if(tmp==null) tmp= Constants.NIL;
               cache.putIfAbsent(cachekey.getKey(),tmp);
                return result;
            }
            else
                return cacheResult.getValue();
        }
        else {
               return  invokeOrigin(context);
        }

    }


    private static Object invokeOrigin(CacheInvokeContext context) throws Throwable {
        return context.getInvoker().invoke();
    }

}

