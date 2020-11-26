package com.xxd.jdksource.concurrent.k_volatile.d;

import java.util.concurrent.CountDownLatch;

/**
 * @author gao
 * @time 2020/05/24 20:45:52
 */

/*
    缓存行
    目前主流的CPU Cache的Cache Line大小都是64Bytes。 注意是字节，而不是位。
 */
public class App2 {

    private static class Padding {
        public volatile long p1,p2,p3,p4,p5,p6,p7;
    }

    // 缓存行对齐
    private static class Foo extends Padding {
        public volatile long x = 0L;
    }

    // 目前一个Foo对象的成员大小刚好是64字节，所以数组中的两个Foo
    // 对象不可能在同一个缓存行中。所以两个线程间不需要通过主存保证
    // 共享缓存行的可见性，因为它们并没有共享的缓存行。这样效率能高
    // 一些。
    public static Foo[] arr = new Foo[2];

    static {
        arr[0] = new Foo();
        arr[1] = new Foo();
    }

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch cd = new CountDownLatch(2);

        Thread t1 = new Thread(()->{
            for (long i = 0; i < 1000_0000L; i++) {
                arr[0].x = i;
            }
            cd.countDown();
        });

        Thread t2 = new Thread(()->{
            for (long i = 0; i < 1000_0000L; i++) {
                arr[1].x = i;
            }
            cd.countDown();
        });

        long start = System.nanoTime();
        t1.start();
        t2.start();
        cd.await();
        long end = System.nanoTime();
        System.out.println((end - start)/1000_000);

    }

}
