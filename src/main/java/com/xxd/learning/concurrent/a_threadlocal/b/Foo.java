package com.xxd.learning.concurrent.a_threadlocal.b;

/**
 * @author gao
 * @time 2020/05/25 21:49:44
 */
public class Foo {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("i have been... " + this);
    }
}
