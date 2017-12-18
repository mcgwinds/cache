package com.mcgwinds.middleware.cache.autoload;

import com.mcgwinds.middleware.cache.annotation.Cache;
import com.mcgwinds.middleware.cache.bean.AutoLoadWrapper;
import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.bean.CacheWrapper;
import com.mcgwinds.middleware.cache.handler.CacheHandler;
import com.mcgwinds.middleware.cache.type.AutoLoadStateType;
import com.mcgwinds.middleware.cache.util.ClassUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Created by mcg on 2017/12/18.
 */

public class AutoLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoLoader.class);

    private  AutoLoadConfig autoLoadConfig;

    private CacheHandler cacheHandler;

    private final ConcurrentHashMap<CacheKey,AutoLoadWrapper>  autoLoadMap;

    private final LinkedBlockingQueue<AutoLoadWrapper> autoLoadQuene;

    private Thread [] autoLoadThreads;

    private Thread sortThread;

    private volatile AutoLoadStateType autoLoadStateType;

    public AutoLoader(CacheHandler cacheHandler, AutoLoadConfig autoLoadConfig) {
        this.cacheHandler=cacheHandler;
        this.autoLoadConfig=autoLoadConfig;
        if(autoLoadConfig.getAutoLoadThreads()==0) {
            autoLoadMap=null;
            autoLoadQuene=null;
            autoLoadThreads=null;
            sortThread=null;
        }
        else {
            autoLoadStateType=AutoLoadStateType.RUNNING;
            AutoLoadThreadFactory autoLoadThreadFactory = new AutoLoadThreadFactory();
            ExecutorService executorService = autoLoadConfig.getExecutorService();
            autoLoadMap=new ConcurrentHashMap<CacheKey,AutoLoadWrapper>(autoLoadConfig.getQueneSize());
            autoLoadQuene=new LinkedBlockingQueue<AutoLoadWrapper>(autoLoadConfig.getQueneSize());
            sortThread=autoLoadThreadFactory.newThread(new SortHandler());
            this.sortThread.setDaemon(true);
            this.sortThread.start();
            for (int i = 0; i < autoLoadConfig.getAutoLoadThreads(); i++) {
                autoLoadThreads[i] = autoLoadThreadFactory.getAutoLoadThread(String.valueOf(i));
                if (null == executorService) {
                    autoLoadThreads[i].start();
                }
                else {
                    executorService.submit(autoLoadThreads[i]);
                }

            }
        }

    }

    public void resetAutoLoad(CacheKey cacheKey) {
        AutoLoadWrapper autoLoadWrapper=autoLoadMap.get(cacheKey);
        if(autoLoadWrapper==null) {
            return;
        }



    }

    public AutoLoadWrapper putIfAbsent(CacheKey cacheKey, Cache cache, ProceedingJoinPoint pjp, CacheWrapper<Object> cacheWrapper) {
        if(null == autoLoadMap) {
            return null;
        }
        AutoLoadWrapper autoLoadWrapper=autoLoadMap.get(cacheKey);
        if(null != autoLoadWrapper) {
            return autoLoadWrapper;
        }
        int expire=cacheWrapper.getExpire();
        if(expire >= AUTO_LOAD_MIN_EXPIRE && autoLoadMap.size() <= this.autoLoadConfig.getQueneSize())) {

            autoLoadWrapper=new AutoLoadWrapper(cacheKey,cache,pjp, expire);
            AutoLoadWrapper tmp=autoLoadMap.putIfAbsent(cacheKey, autoLoadWrapper);
            if(null == tmp) {
                return autoLoadWrapper;
            } else {
                return tmp;
            }
        }
        return null;
    }

    class AutoLoadHandler implements Runnable {
        @Override
        public void run() {
            while(autoLoadStateType==AutoLoadStateType.RUNNING) {
                try {
                    AutoLoadWrapper autoLoadWrapper=autoLoadQuene.take();
                    if(null != autoLoadWrapper) {
                        loadCache(autoLoadWrapper);
                        Thread.sleep(autoLoadConfig.getAutoLoadPeriod());
                    }
                } catch(InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }


            }

        }

        private void loadCache(AutoLoadWrapper autoLoadWrapper) {
            if(null == autoLoadWrapper) {
                return;
            }
            long now=System.currentTimeMillis();
            if(autoLoadWrapper.getLastRequestTime() <= 0 || autoLoadWrapper.getLastLoadTime() <= 0) {
                return;
            }
            Cache cache=autoLoadWrapper.getCache();
            long requestTimeout=cache.requestTimeout();
            // 如果超过一定时间没有请求数据，则从队列中删除
            if(requestTimeout > 0 && (now - autoLoadWrapper.getLastRequestTime()) >= requestTimeout * 1000) {
                autoLoadMap.remove(autoLoadWrapper.getCacheKey());
                return;
            }
            // 如果效率比较高的请求，就没必要使用自动加载了。
            if(autoLoadWrapper.getLoadCnt() > 100 && autoLoadWrapper.getAverageUseTime() < 10) {
                autoLoadMap.remove(autoLoadWrapper.getCacheKey());
                return;
            }
            // 对于使用频率很低的数据，也可以考虑不用自动加载
            long difFirstRequestTime=now - autoLoadWrapper.getFirstRequestTime();
            long oneHourSecs=3600000L;
            // 使用率比较低的数据，没有必要使用自动加载。
            if(difFirstRequestTime > oneHourSecs && autoLoadWrapper.getAverageUseTime() < 1000 && (autoLoadWrapper.getRequestTimes() / (difFirstRequestTime / oneHourSecs)) < 60) {
                autoLoadMap.remove(autoLoadWrapper.getCacheKey());
                return;
            }
            if(autoLoadWrapper.isLoading()) {
                return;
            }
            int expire=autoLoadWrapper.getExpire();
            // 如果过期时间太小了，就不允许自动加载，避免加载过于频繁，影响系统稳定性
            if(expire < AUTO_LOAD_MIN_EXPIRE) {
                return;
            }
            // 计算超时时间
            int alarmTime=cache.alarmTime();
            long timeout;
            if(alarmTime > 0 && alarmTime < expire) {
                timeout=expire - alarmTime;
            } else {
                if(expire >= 600) {
                    timeout=expire - 120;
                } else {
                    timeout=expire - 60;
                }
            }
            int rand=RANDOM.get().nextInt(10);
            timeout=(timeout + (rand % 2 == 0 ? rand : -rand)) * 1000;
            if((now - autoLoadWrapper.getLastLoadTime()) < timeout) {
                return;
            }
            CacheWrapper<Object> newCacheWrapper=null;
            ProceedingJoinPoint pj=autoLoadWrapper.getProceedingJoinPoint();
            CacheKey cacheKey=autoLoadWrapper.getCacheKey();
            Object [] arguments=ClassUtil.getArgs(pj);
            if(autoLoadConfig.isBeforeLoadCheckFromCache()) {
                try {
                    Method method= ClassUtil.getMethod(pj);
                    newCacheWrapper=cacheHandler.getDataOfCache(cacheKey, method, arguments);
                } catch(Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
                // 如果已经被别的服务器更新了，则不需要再次更新
                if(null != newCacheWrapper) {
                    autoLoadWrapper.setExpire(newCacheWrapper.getExpire());
                    if(newCacheWrapper.getLastLoadTime() > autoLoadWrapper.getLastLoadTime() && (now - newCacheWrapper.getLastLoadTime()) < timeout) {
                        autoLoadWrapper.setLastLoadTime(newCacheWrapper.getLastLoadTime());
                        return;
                    }
                }
            }
            //从数据源Dao更新数据
            long loadDataUseTime=0l;
            try {
                 long loadDataStartTime=System.currentTimeMillis();
                 Object loadData=pj.proceed(arguments);
                 loadDataUseTime=System.currentTimeMillis() - loadDataStartTime;
                 newCacheWrapper=new CacheWrapper<Object>(loadData, cache.expire());

            }
            catch(Throwable ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            try {
                if(null != newCacheWrapper) {
                    cacheHandler.writeCache(pj, arguments, cache, cacheKey, newCacheWrapper);
                    autoLoadWrapper.setLastLoadTime(newCacheWrapper.getLastLoadTime())
                            .setExpire(newCacheWrapper.getExpire())
                            .addUseTotalTime(loadDataUseTime);
                }
            } catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }


    }

    class SortHandler implements Runnable {

        @Override
        public void run() {
            while(autoLoadStateType==AutoLoadStateType.RUNNING) {

            }

        }
    }


    class AutoLoadThreadFactory implements ThreadFactory {

        private final String autoLoadThreadNamePre="autoLoadThread_";
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }

        public Thread getAutoLoadThread(String name) {
            Thread autoLoadThread= new Thread(new AutoLoadHandler());
            autoLoadThread.setName(autoLoadThreadNamePre+name);
            autoLoadThread.setDaemon(true);
            return autoLoadThread;
        }

    }


}
