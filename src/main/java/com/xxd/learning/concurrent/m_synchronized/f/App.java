package com.xxd.jdksource.concurrent.m_synchronized.f;

/**
 * @author gao
 * @time 2020/05/25 21:15:25
 */

/*
 注意，使用字符串常量做为的时候，要考虑到常量池的存在。

 */
public class App {
//    所以，以下的s和s2其实指向的是常量池中的同一个对象。
//    static String s = "abc";
//    static String s2 = "abc";

    static String s = new String("abc");
    static String s2 = new String("abc");
    static void f1() {
        synchronized (s) {
            System.out.println("AAAA");
            System.out.println("BBBB");
            System.out.println("CCCC");
            System.out.println("DDDD");
        }
    }

    static void f2() {
        synchronized (s2) {
            System.out.println("1111");
            System.out.println("2222");
            System.out.println("3333");
            System.out.println("4444");
        }
    }

    public static void main(String[] args) {
        Thread th = new Thread(()->{
            while (true) {
                f1();
            }
        });

        Thread th2 = new Thread(()->{
            while (true) {
                f2();
            }
        });

        th.start();
        th2.start();


    }

}
