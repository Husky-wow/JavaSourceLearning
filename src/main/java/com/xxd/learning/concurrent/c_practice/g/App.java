package com.xxd.jdksource.concurrent.c_practice.g;

/**
 * @author gao
 * @time 2020/05/27 22:14:43
 */

// System.out.println()方法内使用了synchronized的，那么下面的程序运行结果是什么呢？

class Task implements Runnable {

    private Integer i = 5;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " i = " + i--);
    }
}

public class App {
    public static void main(String[] args) {
        Task task = new Task();
        Thread t = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);
        Thread t5 = new Thread(task);

        t.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

/*
多运行几次，会发现线程不安全的情况，也就是数据不一致的情况，某次运行结果如下：

Thread-1 i = 5
Thread-2 i = 4
Thread-0 i = 5
Thread-3 i = 3
Thread-4 i = 2

本例子想要说明的是，虽然println()方法在内部是同步的，但i--的操作却是在进入println()之前
发生的，所以会出现线程不安全的现象。
 */