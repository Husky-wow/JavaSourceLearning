package com.xxd.learning.concurrent.a_threadlocal.b;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gao
 * @time 2020/05/25 22:16:25
 */

/*
    虚引用
    1. 虚引用是get不到所引用的对象的。
    2. 在gc执行时，虚引用所引用的对象会被回收。
 */
public class App4 {
    static int x = 0;
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        ReferenceQueue<Foo> queue = new ReferenceQueue<>();
        // 当虚引用所引用的对象被回收时，虚引用对象本身就会被放入queue中。
        PhantomReference<Foo> pr = new PhantomReference<>(new Foo(), queue);

        new Thread(() -> {
            while (true) {
                list.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(100);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                System.out.println(++x + ": " +pr.get());
            }
        }).start();

        // 该线程时刻“监听”着queue，一旦queue中有元素，则立刻获取并输出。
        new Thread(()->{
            while (true) {
                Reference<? extends Foo> poll = queue.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用对象被jvm回收了 ---" + poll);
                }
            }
        }).start();


    }
}

/*

 深入理解JAVA虚拟机一书中有这样一句描述：“为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知”。

 网上有人认为虚引用的作用如下（做为参考）：
 1. 重要对象回收监听 进行日志统计
 2. 系统gc监听 因为虚引用每次内GC都会被回收，那么我们就可以通过虚引用来判断gc的频率，如果频率过大，内存使用可能存在问题，才导致了系统gc频繁调用容

*/
