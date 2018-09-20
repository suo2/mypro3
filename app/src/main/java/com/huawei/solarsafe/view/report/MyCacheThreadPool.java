package com.huawei.solarsafe.view.report;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by P00708 on 2018/3/19.
 */

public class MyCacheThreadPool {


    private static MyCacheThreadPool instance;
    public  ThreadPoolExecutor threadPoolExecutor;
    public synchronized static MyCacheThreadPool createMyMyCacheThreadPool(){

        if(instance == null){
            instance = new MyCacheThreadPool();
        }
        if(instance.threadPoolExecutor == null){
            instance.handlerThreadPool();
        }
        return instance;
    }

    private void handlerThreadPool(){
        threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
             6L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }
    public void startExecute(Runnable runnable){
       threadPoolExecutor.execute(runnable);
    }

}
