package com.xxd.learning.concurrent.a_threadlocal.b;

import java.lang.ref.WeakReference;

/**
 * @author gao
 * @time 2020/05/25 22:09:42
 */

/*
    弱引用

    只要gc执行了，就一定会回收弱引用所指向的对象。
 */
public class App3 {
    public static void main(String[] args) {
        WeakReference<Foo> wr = new WeakReference<>(new Foo());

        System.out.println(wr.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(wr.get());
    }
}

/*
    ThreadLocal做为当前线程的map中的键值，是一个弱引用。
    为什么key要使用弱引用？若是强引用，即使tl=null，但key的引用依然指向ThreadLocal对象，这就是内存泄露，而使用弱引用则不会。

    就算key使用的是弱引用，也还是有内存泄露存在，当ThreadLocal被回收，key也就成为null，则导致整个value再也无法被访问到，因
    此依然存在内存泄露。

    最好的做法就是,在tl=null之前，先执行tl.remove()
 */
