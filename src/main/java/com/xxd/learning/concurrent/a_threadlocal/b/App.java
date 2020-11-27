package com.xxd.learning.concurrent.a_threadlocal.b;

import java.io.IOException;

/**
 * @author gao
 * @time 2020/05/25 21:47:35
 */

/*
    强引用
 */
public class App {
    public static void main(String[] args) throws IOException {
        Foo foo = new Foo();
        foo = null;
        System.gc();
        System.out.println("Enter...");
        System.in.read();
    }
}
