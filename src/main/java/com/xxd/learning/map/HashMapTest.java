package com.xxd.learning.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName HashMapTest.java
 * @Description HashMap的源码学习
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "test");
        map.put(15, "aaa");
        System.out.println(map);
    }
}
