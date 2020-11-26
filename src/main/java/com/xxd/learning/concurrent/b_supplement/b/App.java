package com.xxd.jdksource.concurrent.b_supplement.b;

/**
 * @author gao
 * @time 2020/05/26 21:50:24
 */
public class App {

    // 子线程抛出的异常，父线程是无法捕获的
    static void f1() {
        System.out.println("1111");
        try {
            Thread t = new Thread(() -> {
                System.out.println("AAAA");
                System.out.println(8 / 0);
                System.out.println("BBBB");
            });
            t.start();
            t.join();

        } catch (Exception e) {
            System.out.println("caught exception: " + e);
        }
        System.out.println("2222");
    }

    // 所以一般都在子线程中，把异常处理掉。
    // 也可以通过"UncaughtExceptionHandler"来处理子线程抛出的异常
    static void f2() {
        System.out.println("1111");
        try {
            Thread t = new Thread(() -> {
                System.out.println("AAAA");
                System.out.println(8 / 0);
                System.out.println("BBBB");
            });
            // 为子线程设置“未捕获异常的处理器”，当子线程中抛出异常时，就会被该处理器所处理！
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println("caught exception: " + e);
                }
            });
            t.start();
            t.join();

        } catch (Exception e) {
            System.out.println("caught exception: " + e);
        }
        System.out.println("2222");

    }


    public static void main(String[] args) {
        // f1();
        f2();
    }

}
