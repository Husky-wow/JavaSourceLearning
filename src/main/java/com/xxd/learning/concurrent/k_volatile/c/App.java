package com.xxd.jdksource.concurrent.k_volatile.c;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gao
 * @time 2020/05/2020/5/7 23:00:32
 */

/*
    该例子演示了volatile保证有序性的特点

    分析以下的例子，a和b的值的组合的可能有3种：
    1. a=1 b=1
    2. a=0 b=1
    3. a=1 b=0

    a和b的组合，绝对不可能出现： a = 0 b= 0 的情况

 */
public class App {
    volatile static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Lock lock = new ReentrantLock();
        Map<String, Integer> resultMap = new HashMap<>();

        for (int i = 0; i < 1000000; i++) {
            x = 0;
            y = 0;
            resultMap.clear();
            Thread one = new Thread(() -> {
                x = 1;
                int a = y;
                resultMap.put("a", a);
            });

            Thread other = new Thread(() -> {
                y = 1;
                int b = x;
                resultMap.put("b", b);
            });
            one.start();
            other.start();
            one.join();
            other.join();
            System.out.println(resultMap);

            // 当 a = b 的时候，会停顿， 我们主要看是否会出现 a = 0 b = 0 的情况
            // 实际的测试结果是：会出现 a = 0 b = 0 的情况的，这恰恰就是指令重排的效果，为a和b变量添加volatile修饰符就不会出现指令重排了。
            if (resultMap.get("a").equals(resultMap.get("b"))) {
                System.out.println("Enter...");
                System.in.read();
            }
        }
    }
}