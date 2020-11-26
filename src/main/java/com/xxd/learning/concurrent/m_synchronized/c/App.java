package com.xxd.jdksource.concurrent.m_synchronized.c;

import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author gao
 * @time 2020/05/13 23:34:47
 */

// 该例子证明了，对象在内存中的布局，不受方法多少的影响。
@Data
class User {
    private int id;
    private String name;

    public void eat() {
        System.out.println(name + "吃饭..");
    }
}

public class App {
    public static void main(String[] args) {
        User user = new User();
        user.setId(10);
        user.setName("foo");
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }
}
