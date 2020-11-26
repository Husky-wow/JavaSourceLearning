package com.xxd.jdksource.concurrent.n_aqs.a;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author gao
 * @time 2020/05/23 08:37:34
 */

// 该例子演示了LockSupport.park()和LockSupport.unpark()的作用
// 线程的interrupt方法也可以使由于park()方法而阻塞的线程醒过来！！
// 线程的interrupt方法也可以使由于park()方法而阻塞的线程醒过来！！
// 线程的interrupt方法也可以使由于park()方法而阻塞的线程醒过来！！
class T implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始任务");
        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + "结束任务");
    }
}
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        T t = new T();
        Thread th = new Thread(t, "郭靖");
        Thread th2 = new Thread(t, "黄蓉");

        th.start();
        th2.start();

        System.out.println("Enter...");
        System.in.read();
        LockSupport.unpark(th);

        System.out.println("Enter...");
        System.in.read();
        // LockSupport.unpark(th2);
        // th2.interrupt()方法也可以唤醒由于park而阻塞的th2线程
        th2.interrupt();
    }
}
