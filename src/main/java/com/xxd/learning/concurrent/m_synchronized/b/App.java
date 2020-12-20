package com.xxd.learning.concurrent.m_synchronized.b;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gao
 * @time 2020/05/13 22:37:13
 */

/*
 演示Java Object Layer的使用，需要导入以下依赖：
    <dependency>
        <groupId>org.openjdk.jol</groupId>
        <artifactId>jol-core</artifactId>
        <version>0.10</version>
    </dependency>
*/
public class App {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
