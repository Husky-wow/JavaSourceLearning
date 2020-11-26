package com.xxd.jdksource.concurrent.a_threadlocal.b;

import java.lang.ref.SoftReference;

/**
 * @author gao
 * @time 2020/05/25 21:55:50
 */

/*
    软引用

    运行时，设置虚拟机参数：-Xmx20M
 */
public class App2 {
    public static void main(String[] args) {
        SoftReference<byte[]> sr = new SoftReference<>(new byte[1024 * 1024 * 10]);

        System.out.println(sr.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sr.get());

        byte[] bb = new byte[1024 * 1024 * 11];
        // 在即将抛出OOM的时候，jvm就会把软引用所引用的对象收回了。
        // 所以下面获取到的就是null
        System.out.println(sr.get());
    }
}

// 软引用特别适合做缓存。
