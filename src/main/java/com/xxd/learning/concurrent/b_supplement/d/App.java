package com.xxd.learning.concurrent.b_supplement.d;

/**
 * @author gao
 * @time 2020/05/28 00:00:44
 */

/*
    Thread.interrupted()：测试当前正在运行的线程是否已经中断，同时会清除中断标记
    this.isInterrupted()：测试this所代表的线程是否已经中断，且不会清除中断标记
 */
class Task implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            System.out.println("i = " + i);
        }
    }
}
public class App {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Thread th = new Thread(task);
        th.start();
        Thread.sleep(100);
        th.interrupt();
        System.out.println("1.是否停止 = " + Thread.interrupted());
        System.out.println("2.是否停止 = " + Thread.interrupted());
        System.out.println("3.是否停止 = " + th.isInterrupted());
        System.out.println("4.是否停止 = " + th.isInterrupted());
    }
}

/*
    i = 6230
    i = 6231
    1.是否停止 = false
    2.是否停止 = false
    3.是否停止 = true
    4.是否停止 = true
    i = 6232
    i = 6233
 */
