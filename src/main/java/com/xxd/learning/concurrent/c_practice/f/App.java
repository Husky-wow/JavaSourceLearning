package com.xxd.learning.concurrent.c_practice.f;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * @author gao
 * @time 2020/05/17 23:23:31
 */

/*
一道面试题：实现一个容器，提供两个方法，add，size
写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
当个数到5个时，线程2给出提示并结束线程2。
 */
public class App {

    public static void main(String[] args) {
        CountDownLatch cd = new CountDownLatch(5);
        int[] container = new int[10];
        Runnable adder = new T4Add(cd, container);
        Runnable watcher = new T4Watch(cd);

        Thread thread1 = new Thread(adder);
        Thread thread2 = new Thread(watcher);
        thread1.start();
        thread2.start();
    }

}

/**
 * 给容器中添加元素的线程
 */
class T4Add implements Runnable {

    private CountDownLatch cd;

    private int[] container;

    public T4Add(CountDownLatch cd, int[] container) {
        this.cd = cd;
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            container[i] = i;
            cd.countDown();
        }
        System.out.println(Arrays.toString(container));
    }
}

class T4Watch implements Runnable {

    private CountDownLatch cd;

    public T4Watch(CountDownLatch cd) {
        this.cd = cd;
    }

    @Override
    public void run() {
        try {
            cd.await();
            System.out.println("容器中元素数量到达5个");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
