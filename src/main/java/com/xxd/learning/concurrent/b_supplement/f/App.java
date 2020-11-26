package com.xxd.jdksource.concurrent.b_supplement.f;

/**
 * @author gao
 * @time 2020/05/29 00:05:18
 */

// yield，让出cpu，重新抢占cpu，测试时，可以将Thread.yield()的注释去掉，然后进行对比。
class T implements Runnable {
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 50000000; i++) {
            // Thread.yield();
            count = count + (i + 1);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

public class App {
    public static void main(String[] args) {
        T t = new T();
        Thread th = new Thread(t);
        th.start();
    }
}
