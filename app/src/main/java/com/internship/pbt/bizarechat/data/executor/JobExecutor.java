package com.internship.pbt.bizarechat.data.executor;

import com.internship.pbt.bizarechat.domain.executor.ThreadExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JobExecutor implements ThreadExecutor {

    private static volatile JobExecutor sInstance;

    private final ThreadPoolExecutor mThreadPoolExecutor;

    private JobExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                40,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new JobThreadFactory());
    }

    public static JobExecutor getInstance() {
        JobExecutor local = sInstance;
        if (local == null) {
            synchronized (JobExecutor.class) {
                local = sInstance;
                if (local == null) {
                    sInstance = local = new JobExecutor();
                }
            }
        }
        return local;
    }

    @Override
    public void execute(Runnable command) {
        mThreadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private int counter = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "BizareChatThread" + counter++);
        }
    }
}