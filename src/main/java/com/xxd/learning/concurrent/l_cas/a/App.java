package com.xxd.jdksource.concurrent.l_cas.a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

// 该例子演示了AtomicInteger的方法天生就具备原子性
// AtomicInteger底层使用了非阻塞同步的CAS机制

// 该例子的主要目的是引出CAS

public class App {
    static CountDownLatch cd = new CountDownLatch(100);
    AtomicInteger count = new AtomicInteger();

    void f1() {
        for (int i = 1; i <= 1000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        List<Thread> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    app.f1();
                    cd.countDown();
                }
            }));
        }
        for (Thread th : list) {
            th.start();
        }

        cd.await();
        System.out.println(app.count.get());
    }
}