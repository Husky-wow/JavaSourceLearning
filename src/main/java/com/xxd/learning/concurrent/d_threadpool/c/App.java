package com.xxd.jdksource.concurrent.d_threadpool.c;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author gao
 * @time 2020/08/27 10:50:39
 * 设置线程池中线程的各种属性
 */

class MyThreadFactory implements ThreadFactory {
    private static int count = 0;

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r, "Thread(" + count++ + ")");
        return th;
    }
}

public class App {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3, new MyThreadFactory());

        for (int i = 1; i <= 3; i++) {
            es.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "~~~~~");
            });
        }

        es.shutdown();
    }
}
