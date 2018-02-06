package com.mcgwinds.middleware.cache.aop;

import com.mcgwinds.middleware.cache.core.cache.CacheContext;
import com.mcgwinds.middleware.cache.core.cacheconfig.CacheConfigManager;
import com.mcgwinds.middleware.cache.util.ClassUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by mcg on 2018/1/23.
 */

@Component
@Aspect
public class CacheAspect {

    @Resource
    private CacheHandler cacheHandler;

    @Resource
    private CacheContext cacheContext;

    @Resource
    private CacheConfigManager cacheConfigManager;


    @Around("@annotation(com.youzan.cachemanager.client.annotation.Cacheable)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {

        Method method = ClassUtil.getMethod(pjp);
        Object[] args= ClassUtil.getArgs(pjp);

        String cacheConfigKey = ClassUtil.getKey(method, pjp.getClass());

        if (null== cacheConfigManager.get(cacheConfigKey)) {
            synchronized (this) {
                if(cacheConfigManager.get(cacheConfigKey)==null) {
                    CacheInvokeConfig cacheInvokeConfig= cacheConfigManager.parseCacheInvokeConfig(method);
                    cacheConfigManager.put(cacheConfigKey, cacheInvokeConfig);
                }
            }
        }

        CacheInvokeContext context = cacheContext.createCacheInvokeContext();
        context.setInvoker(pjp::proceed);
        context.setMethod(method);
        context.setArgs(args);
        context.setCacheInvokeConfig(cacheConfigManager.get(cacheConfigKey));
        return cacheHandler.invoke(context);

    }

}
