package com.xxd.jdksource.concurrent.k_volatile.e;

/**
 * @author gao
 * @time 2020/05/24 23:06:08
 */

/*
    懒汉式，双重检查机制
 */
public class Singleton {
    volatile int state;
    public static void main(String[] args) {
        System.out.println("test");
    }
}
