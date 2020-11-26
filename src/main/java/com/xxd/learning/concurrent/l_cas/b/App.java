package com.xxd.jdksource.concurrent.l_cas.b;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author gao
 * @time 2020/05/25 23:39:31
 */
public class App {
    public static void main(String[] args) throws Exception {

        // 运行时异常： java.lang.SecurityException: Unsafe
        // Unsafe unsafe = Unsafe.getUnsafe();
        // System.out.println("unsafe = " + unsafe);


        // run well
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println("unsafe = " + unsafe);
    }
}
