package com.xxd.learning.concurrent.m_synchronized.e;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.util.Objects;

/**
 * @author gao
 * @time 2020/05/13 23:55:18
 */

/*
    该例子演示锁升级的过程
 */
public class App {
    /*
        关闭偏向锁：-XX:-UseBiasedLocking
        关闭偏向锁，jvm在分配对象空间时，对象就是“无锁”状态

        java.lang.Object object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
              0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
              4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
             12     4        (loss due to the next object alignment)
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        对象头中原始的mark word：01 00 00 00 00 00 00 00

        由于我的机器按小端模式存储数据，所以对象的 mark word 应该是这样：
        16进制： 00 00 00 00 00 00 00 01
         2进制： 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000001
                偏向锁位    锁标志位      状态
        最后3位： 0         01          无锁
     */
    @Test
    public void test() throws Exception {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    /*
        关闭偏向锁：-XX:-UseBiasedLocking
        在“无锁”状态下，调用java.lang.Object的hashCode，是可以把“哈希码”存到对象头中的

        hashCode = 1495242910
        com.gao.m_synchronized.e.App$Person object internals:
         OFFSET  SIZE                           TYPE DESCRIPTION                               VALUE
              0     4                                (object header)                           01 9e 98 1f (00000001 10011110 10011000 00011111) (530095617)
              4     4                                (object header)                           59 00 00 00 (01011001 00000000 00000000 00000000) (89)
              8     4                                (object header)                           67 1d 01 f8 (01100111 00011101 00000001 11111000) (-134144665)
             12     4   com.gao.m_synchronized.e.App Person.this$0                             (object)
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

        对象头中原始的mark word：01 9e 98 1f 59 00 00 00

        由于我的机器按小端模式存储数据，所以对象的 mark word 应该是这样：
        16进制： 00 00 00 59 1f 98 9e 01
         2进制： 00000000 00000000 00000000 01011001 00011111 10011000 10011110 00000001
        取中间31位：                         01011001 00011111 10011000 10011110

     */
    @Test
    public void test2() throws Exception {
        class Person {
        }
        Person p = new Person();
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
        int hashCode = p.hashCode();
        System.out.println("hashCode = " + hashCode);
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }


    /*
        关闭偏向锁：-XX:-UseBiasedLocking
        如果重写过java.lang.Object的hashCode方法，则调用了hashCode是不会向
        markword中存入hashCode的（除非调用了super.hashCode()）
     */
    @Test
    public void test3() throws Exception {
        class Person {
            @Override
            public int hashCode() {
                // super.hashCode();
                return 100;
            }
        }
        Person p = new Person();
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
        int hashCode = p.hashCode();
        System.out.println("hashCode = " + hashCode);
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }

    /*
        在偏向锁开启的情况下（默认是开启着的）， jvm分配对象空间的时候，
        对象头中，偏向锁位 + 锁标志位 = 101
        但此时对象头中并没有记录任何线程的ThreadId，也就是不偏向任何线程
        此时的状态可以叫做： “可偏向”

        但是有一点特别要注意：
        虚拟机加载时会延迟偏向锁的出现，所以睡5秒后创建的对象才会有“可偏向”的状态！！！
        虚拟机加载时会延迟偏向锁的出现，所以睡5秒后创建的对象才会有“可偏向”的状态！！！
        虚拟机加载时会延迟偏向锁的出现，所以睡5秒后创建的对象才会有“可偏向”的状态！！！
     */
    @Test
    public void test4() throws Exception {
        Object o = new Object();
        Thread.sleep(5000);
        Object o2 = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println(ClassLayout.parseInstance(o2).toPrintable());
    }

    // 此处可以有一个练习：测试对象中markword处于“可偏向”的状态下，markword是否能存储对象的hashCode的

    // 让处于“可偏向”的对象，真正地偏向某一个线程
    @Test
    public void test5() throws Exception {
        Thread.sleep(5000);
        Object o = new Object();

        // 可偏向
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            // 已偏向
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        // 虽然同步块已经执行完毕，但是对象头中仍然保持着之前偏向的线程的Id
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }


    // 偏向锁 --> 轻量级锁
    @Test
    public void test6() throws Exception {
        class Foo {
        }
        Thread.sleep(5000);
        Foo foo = new Foo();
        // 偏向锁，但是此时foo的对象头中的markword中没有记录任何Thread的id，也就是不偏向任何一个具体的线程
        System.out.println(ClassLayout.parseInstance(foo).toPrintable());

        // 偏向锁，偏向于某一个线程
        Thread th = new Thread(() -> {
            synchronized (foo) {
                System.out.println(ClassLayout.parseInstance(foo).toPrintable());
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        th.start();


        // th2获取th线程获取过的锁，此时th线程已死亡，不会造成偏向锁升级
        // Thread.sleep(3000);

        // th2获取th线程获取过的锁，此时th线程还存活，造成偏向th线程的锁 升级为 轻量级锁  锁状态：0 00
        Thread.sleep(1000);

        Thread th2 = new Thread(() -> {
            synchronized (foo) {
                System.out.println(ClassLayout.parseInstance(foo).toPrintable());
            }
        });
        th2.start();

        // 保证th和th2都执行完
        Thread.sleep(2000);


        System.out.println("============================================");

        /*
            再次查看foo对象的对象头中的markword
            会发现，foo之前的轻量级锁的状态，已经恢复成无锁状态了：0 01
         */
        System.out.println(ClassLayout.parseInstance(foo).toPrintable());

        System.out.println("Enter...");
        System.in.read();

        // 小结： 什么时候变为轻量级锁呢？
        // 一个线程对共享对象加锁访问完毕并释放了锁后，但该线程还没结束，
        // 此时另一个线程也来对共享资源访问加锁，这时共享对象中的锁就变为轻量级锁了；0 00
    }

    /*
     关闭偏向锁：-XX:-UseBiasedLocking
     关闭偏向锁以后，如果线程抢占某个对象的锁，则对象的锁状态，直接进入轻量级锁的状态。

     com.gao.m_synchronized.e.App$2Foo object internals:
     OFFSET  SIZE                           TYPE DESCRIPTION                               VALUE
          0     4                                (object header)                           b0 f2 d2 1f (10110000 11110010 11010010 00011111) (533918384)
          4     4                                (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
          8     4                                (object header)                           c2 11 01 f8 (11000010 00010001 00000001 11111000) (-134147646)
         12     4   com.gao.m_synchronized.e.App App$2Foo.this$0                           (object)
    Instance size: 16 bytes
    Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
     */
    @Test
    public void test7() throws Exception {
        class Foo {
        }
        Foo foo = new Foo();

        Thread th = new Thread(() -> {
            synchronized (foo) {
                System.out.println(ClassLayout.parseInstance(foo).toPrintable());
            }
        });

        th.start();

        System.out.println("Enter...");
        System.in.read();
    }

    /*
        关闭偏向锁：-XX:-UseBiasedLocking
        轻量级锁 --> 重量级锁

        一个线程对共享对象加锁正在访问还未释放锁，此时另一个线程也来对共享资源访问加锁(等待)，
        这时共享对象中的锁就变为重量级锁了；
     */
    @Test
    public void test8() throws Exception {
        class Foo {
        }
        Foo foo = new Foo();

        // 轻量级锁： 0 00
        Thread th = new Thread(() -> {
            synchronized (foo) {
                System.out.println(ClassLayout.parseInstance(foo).toPrintable());
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        th.start();

        /*
         此处休眠时间小于2000ms,则下面会出现重量级锁：010

         这是因为：一个线程对共享对象加锁正在访问还未释放锁，此时另一个线程也来对共享资源访问加锁(等待)，
         这时共享对象中的锁就变为重量级锁了；
         */

        Thread.sleep(1500);

        Thread th2 = new Thread(() -> {
            synchronized (foo) {
                System.out.println(ClassLayout.parseInstance(foo).toPrintable());
            }
        });
        th2.start();

        System.out.println("Enter...");
        System.in.read();
    }

}
