package com.xxd.learning.concurrent.m_synchronized.a;

/**
 * @author gao
 * @time 2020/05/16 15:27:56
 */

public class Bar {
    public synchronized void f1(Object obj) {
        obj.hashCode();
    }
}

/*
编译Bar后，得到Bar字节码，然后键入：javap -v Bar，得到以下输出内容：

public synchronized void f1(java.lang.Object);
    descriptor: (Ljava/lang/Object;)V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=1, locals=2, args_size=2
         0: aload_1
         1: invokevirtual #2                  // Method java/lang/Object.hashCode:()I
         4: pop
         5: return
      LineNumberTable:
        line 3: 0
        line 4: 5

从反编译的结果来看，方法的同步并没有通过指令monitorenter和monitorexit来完成（理论上其实也可以通过这两条指令来实现），不过相对于普通方法，其常量池中多了ACC_SYNCHRONIZED标示符。
JVM就是根据该标示符来实现方法的同步的：当方法调用时，调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置，如果设置了，执行线程将先获取monitor，获取成功之后才能执行方法体，
方法执行完后再释放monitor。在方法执行期间，其他任何线程都无法再获得同一个monitor对象。 其实本质上没有区别
*/