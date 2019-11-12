package com.yimaotong.fruitbase.protocal.netutil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolManager {

    private static ThreadPoolManager instance = new ThreadPoolManager();
    private ThreadPoolProxy poolProxy;
    private ThreadPoolProxy longThreadPoolProxy;

    private ThreadPoolManager() {
    }

    // 懒汉式   饿汉式(线程安全的)

    public static ThreadPoolManager getInstance() {
        return instance;
    }


    /**
     * 1. 读写文件
     * 2. 请求服务器数据
     * 效率最高线程池
     * cpu * 2 + 1
     *
     * @return
     */
    public ThreadPoolProxy createThreadPool() {
        if (poolProxy == null) {
            poolProxy = new ThreadPoolProxy(3, 5, 5000);
        }
        return poolProxy;
    }


    /**
     * 读写文件
     *
     * @return
     */
    public ThreadPoolProxy createLongThreadPool() {
        if (longThreadPoolProxy == null)
            longThreadPoolProxy = new ThreadPoolProxy(5, 10, 5000);
        return longThreadPoolProxy;
    }


    public class ThreadPoolProxy {
        ThreadPoolExecutor threadPool;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        /**
         * 执行线程任务
         *
         * @param runnable
         */
        public void execture(Runnable runnable) {

            if (threadPool == null) {
                //corePoolSize : c初始化的线程数量
                //maximumPoolSize: 额外创建的线程数量
                //keepAliveTime

                threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
            }
            if (!threadPool.isShutdown())
                threadPool.execute(runnable);
        }

        /**
         * 取消线程任务
         *
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (threadPool != null && !threadPool.isShutdown() && !threadPool.isTerminated()) {
                threadPool.remove(runnable);
            }
        }
    }
}
