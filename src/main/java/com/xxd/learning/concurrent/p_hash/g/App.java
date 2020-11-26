package com.xxd.jdksource.concurrent.p_hash.g;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author gao
 * @time 2020/05/23 16:20:33
 */

/*
遍历Map的方式
 */
public class App {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a","王菲");
        map.put("b","邓紫棋");
        map.put("c","孙燕姿");

        map.forEach((String key, String value)-> {
                System.out.println(key + " -- " + value);
        });
    }
}
