package com.xxd.learning.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName ReflectTest.java
 * @Description 反射练习
 */
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.xxd.learning.reflect.Person");
        Object instance = aClass.newInstance();
        Method method = aClass.getMethod("eat");
        method.invoke(instance);
    }
}

class Person {
    public void eat() {
        System.out.println("我吃！！！");
    }

    public void eat(String food) {
        System.out.println("我吃" + food);
    }
}
