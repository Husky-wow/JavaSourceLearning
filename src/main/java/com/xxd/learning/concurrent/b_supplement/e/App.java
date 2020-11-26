package com.xxd.jdksource.concurrent.b_supplement.e;

/**
 * @author gao
 * @time 2020/05/28 00:18:19
 */

/*
先interrupt线程，后让线程sleep，也会抛出异常。
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().interrupt();
        System.out.println("AAAA");
        System.out.println("BBBB");
        System.out.println("CCCC");
        Thread.sleep(10);
        System.out.println("DDDD");
    }
}
