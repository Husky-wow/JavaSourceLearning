package com.xxd.learning.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName Cal2.java
 * @Description 新的需求是在每个计算方法前后加入中文日志，那其实可以直接改方法；
 * 但是第二天又让加英文日志，第三天有让加德文日志，第四天又让加火星文日志，第五天又让改回中文日志；
 * 每天一个需求变化，难道我们每天都要对所有方法都进行一遍更改吗？
 * 答案是使用JDK动态代理
 * 被代理的类应该实现一个接口
 */
public class Cal2 {
    public static void main(String[] args) {
        // 目标代理对象
        ICal cal = new Cal();
        CalHandler calHandler = new CalHandler(cal);
        // 代理对象
        ICal proxyInstance = (ICal) Proxy.newProxyInstance(Cal2.class.getClassLoader(), cal.getClass().getInterfaces(), calHandler);
        proxyInstance.add(1, 2);
    }
}
