package com.worthto.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenjie on 15/5/29.
 */
public class ThreadExecutor {
    final static private ExecutorService service = Executors.newCachedThreadPool();
    final static private ExecutorService singleService = Executors.newSingleThreadExecutor();
    final static private ScheduledExecutorService delayService = Executors.newScheduledThreadPool(5);

    public static void exec(Runnable r) {
        if (r != null) {
            service.execute(r);
        }
    }

    public static void execSingle(Runnable r) {
        if (r != null) {
            singleService.execute(r);
        }
    }

    public static void exec(final Runnable r, final long delay) {
        if (r != null) {
            delayService.schedule(r, delay, TimeUnit.MICROSECONDS);
        }
    }
}
