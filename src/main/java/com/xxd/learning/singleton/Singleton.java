package com.xxd.learning.singleton;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName Singleton.java
 * @Description 写一个双检索懒汉式单例
 */
public class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
