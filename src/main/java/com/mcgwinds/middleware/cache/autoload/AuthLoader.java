package com.mcgwinds.middleware.cache.autoload;

import com.mcgwinds.middleware.cache.bean.AutoLoadWrapper;
import com.mcgwinds.middleware.cache.bean.CacheKey;
import com.mcgwinds.middleware.cache.handler.CacheHandler;
import com.mcgwinds.middleware.cache.type.AutoLoadStateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Created by mcg on 2017/12/18.
 */

public class AuthLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthLoader.class);

    private  AutoLoadConfig autoLoadConfig;

    private CacheHandler cacheHandler;

    private final ConcurrentHashMap<CacheKey,AutoLoadWrapper>  autoLoadMap;

    private final LinkedBlockingQueue<AutoLoadWrapper> autoLoadQuene;

    private Thread [] autoLoadThreads;

    private Thread sortThread;

    private volatile AutoLoadStateType autoLoadStateType;

    public AuthLoader(CacheHandler cacheHandler,AutoLoadConfig autoLoadConfig) {
        this.cacheHandler=cacheHandler;
        this.autoLoadConfig=autoLoadConfig;
        if(autoLoadConfig.getAutoLoad()==0) {
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
            for (int i = 0; i < autoLoadConfig.getAutoLoad(); i++) {
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

    class AutoLoadHandler implements Runnable {
        @Override
        public void run() {
            while(autoLoadStateType==AutoLoadStateType.RUNNING) {

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
