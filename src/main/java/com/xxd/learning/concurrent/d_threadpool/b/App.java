package com.xxd.jdksource.concurrent.d_threadpool.b;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gao
 * @time 2020/08/08 09:30:04
 */
public class App {
    public static void main(String[] args) {
        // 自己定义一个阻塞队列，传入线程池
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(10);
        ExecutorService es = new ThreadPoolExecutor(3,100,60, TimeUnit.SECONDS, queue);
        for(int i = 1; i <= 10; i++) {
            es.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " begin...");
                try {
                    Thread.sleep(2000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end...");
            });

            // 获取阻塞队列中，阻塞的线程
            System.out.println("阻塞队列大小：" + queue.size());
        }

        es.shutdown();
    }
}
