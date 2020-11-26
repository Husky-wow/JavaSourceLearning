package com.xxd.jdksource.concurrent.n_aqs.b;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gao
 * @time 2020/05/23 08:45:44
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        // 看源码即可
        lock.lock();

        // 看源码即可
        lock.lockInterruptibly();

        // 看源码即可
        lock.unlock();
    }
}
