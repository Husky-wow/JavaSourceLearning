package com.xxd.learning.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName CalHandler.java
 * @Description 必须实现 InvocationHandler 接口来完成代理类要做的功能：
 *  *  1. 调用目标方法；
 *  *  2. 增加功能；
 */
public class CalHandler implements InvocationHandler {
    /**
     * 目标对象
     *  目标对象是活动的，不是固定的，需要传入进来，传入的是谁，就代理谁
     *
     */
    private Object target;

    public CalHandler(Object target) {
        // 给目标对象赋值
        this.target = target;
    }

    /**
     *
     * @param proxy 代理类的实例
     * @param method 调用的方法，即需要执行的方法
     * @param args 方法的参数
     * @return 代理方法的返回值，查看该该方法的原始注释，其中有一句话：
     *   If the value returned by this method is {@code null} and the interface method's return type is primitive, then a {@code NullPointerException} will be thrown by the method invocation on the proxy instance
     *   这就是为什么返回未Null时，会抛出NullPointException异常
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行目标方法
        Object invoke = method.invoke(target, args);
        System.out.println("invoke方法执行");
        return invoke;
    }
}
