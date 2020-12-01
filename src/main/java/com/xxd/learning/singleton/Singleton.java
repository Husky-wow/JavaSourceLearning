package com.xxd.learning.singleton;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName Singleton.java
 * @Description 写一个双检索懒汉式单例
 */
public class Singleton {

    /**
     * volatile 防止指令重排，此处要注意和synchronize的有序性区别
     */
    private static volatile Singleton instance;

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
