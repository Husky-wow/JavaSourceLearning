package com.xxd.jdksource.concurrent.p_hash.b;

import java.util.HashMap;

/**
 * @author gao
 * @time 2020/05/23 09:32:45
 */

/*
    1. jdk1.7 HashMap源码分析：
    2. jdk1.7 HashMap多线程下的扩容问题
 */
public class App {
    public static void main(String[] args) {
        HashMap map = new HashMap(11);

        map.put("foo", "aabbcc");




    }
}
