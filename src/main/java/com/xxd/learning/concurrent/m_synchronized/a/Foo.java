package com.xxd.learning.concurrent.m_synchronized.a;

/**
 * @author gao
 * @time 2020/05/16 15:27:38
 */
public class Foo {
    public void f1(Object obj) {
        synchronized(obj) {
            obj.hashCode();
        }
    }
}

/*
编译Foo后，得到Foo字节码，然后键入：javap -c Foo， 可以看到以下的反编译内容：
Compiled from "Foo.java"
class Foo {
  Foo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public void f1(java.lang.Object);
    Code:
       0: aload_1
       1: dup
       2: astore_2
       3: monitorenter
       4: aload_1
       5: invokevirtual #2                  // Method java/lang/Object.hashCode:()I
       8: pop
       9: aload_2
      10: monitorexit
      11: goto          19
      14: astore_3
      15: aload_2
      16: monitorexit
      17: aload_3
      18: athrow
      19: return
    Exception table:
       from    to  target type
           4    11    14   any
          14    17    14   any
}

应该能够看到当程序声明 synchronized 代码块时，编译成的字节码会包含 monitorenter和 monitorexit 指令，
这两种指令会消耗操作数栈上的一个引用类型的元素(也就是 synchronized 关键字括号里面的引用)，作为所要加锁解锁的锁对象。
如果看的比较仔细的话，上面有一个 monitorenter 指令和两个 monitorexit 指令，这是 Java 虚拟机为了确保获得的锁不管
是在正常执行路径，还是在异常执行路径上都能够解锁。

关于 monitorenter 和 monitorexit ，可以理解为每个锁对象拥有一个锁计数器和一个指向持有该锁的线程指针:
当程序执行 monitorenter 时，如果目标锁对象的计数器为 0 ，说明这个时候它没有被其他线程所占有，此时如果有线程来请求使用， Java 虚拟机就会分配给该线程，并且把计数器的值加 1
目标锁对象计数器不为 0 时，如果锁对象持有的线程是当前线程， Java 虚拟机可以将其计数器加 1 ，如果不是呢?那很抱歉，就只能等待，等待持有线程释放掉
当执行 monitorexit 时， Java 虚拟机就将锁对象的计数器减 1 ，当计数器减到 0 时，说明这个锁就被释放掉了，此时如果有其他线程来请求，就可以请求成功

为什么采用这种方式呢?是为了允许同一个线程重复获取同一把锁。比如，一个 Java 类中拥有好多个 synchronized 方法，那这些方法之间的相互调用，不管是直接的还是间接的，
都会涉及到对同一把锁的重复加锁操作。这样去设计的话，就可以避免这种情况。
 */