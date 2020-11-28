package com.xxd.learning.map;

import java.util.HashMap;
import java.util.Map;

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
