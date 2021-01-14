package com.xxd.learning.dynamicproxy;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName Cal.java
 * @Description TODO
 */
public class Cal implements ICal {
    /**
     * 这里有一个需求,做一个两数的简单计算器！
     * 简单，搞他
     * 备注：简单就好，这里只是做一个示例，就不考虑多了
     */
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int reduce(int a, int b) {
        return a -b;
    }

    @Override
    public int multi(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }

}


class CalTest {

    public static void main(String[] args) {
        int a = 3;
        int b = 1;

        Cal cal = new Cal();
        cal.add(a, b);
        cal.reduce(a, b);
        cal.reduce(a, b);
        cal.div(a, b);

        int c = 0 % 15;
        System.out.print(c);
    }


}
