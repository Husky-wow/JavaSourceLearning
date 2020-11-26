package com.xxd.jdksource.concurrent.m_synchronized.d;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gao
 * @time 2020/05/13 23:41:42
 */

/*
    该例子演示了，获取对象的锁之后，对象内存布局中的markword的变化。
    也就是说，对象锁的信息是存放在对象头的markword中的。
 */
public class App {
    public static void main(String[] args) {
        Object obj = new Object();

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("==============================================================\n");

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }
}

// 关于 对象锁 和 对象头中markword 的对应关系，将在e包详细讲解