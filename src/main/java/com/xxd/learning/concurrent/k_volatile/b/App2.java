package com.xxd.learning.concurrent.k_volatile.b;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName App2.java
 * @Description 对 App1 的示例使用 CountDownLatch 修改
 */
public class App2 {

    volatile int count = 0;

    private static final CountDownLatch cd = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        App2 app = new App2();

        List<Thread> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            Thread th = new Thread(app::f1);
            list.add(th);
        }

        for (Thread th : list) {
            th.start();
        }
        cd.await();
        // 如果volatile保证原子性，那么结果将会是10000
        System.out.println(app.count);
    }


    /* synchronized */ void f1() {
        for (int i = 1; i <= 1000; i++) {
            count++;
        }
        cd.countDown();
    }

}
