package com.xxd.learning.concurrent.k_volatile.d;

import java.util.concurrent.CountDownLatch;

/**
 * @author gao
 * @time 2020/05/24 20:45:52
 */

/*
    缓存行
    目前主流的CPU Cache的Cache Line大小都是64Bytes。 注意是字节，而不是位。
 */
public class App {

    private static class Foo {
        public volatile long x = 0L;
    }

    // 一个Foo对象的成员变量占8个字节，两个Foo对象的成员变量就一共占16个字节
    // 且Foo[]数组中的元素是连续存放的，且缓存行的大小位64字节，是大于16字节
    // 的，所以Foo[]数组中的2个元素，就会在同一个缓存行中。也就是说，例子中的
    // 两个线程，是以缓存行为最小共享单位的，如图，任何一个线程修改了自己缓存中
    // 缓存行的数据时，都会“通知“其他也使用该缓存行的线程。

    // 所以，这2个线程明明没有真正意义上的共享对象，却不停地通过刷新主存来获取最新的缓存行的值。这样效率很低。
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
