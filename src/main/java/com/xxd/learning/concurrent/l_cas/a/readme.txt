1. AtomicInteger的incrementAndGet方法，最终会调用Unsafe类的compareAndSwapInt方法

2. Unsafe类的compareAndSwapInt方法，是虚拟机级别的方法，hotspot虚拟机的源码可以在以下地址看到：
http://hg.openjdk.java.net/jdk8u/jdk8u60/hotspot/file/37240c1019fd
或者
https://gitee.com/jbjdk/jdk8u_hotspot/tree/master

3. unsafe的源码，可以在以下地址看到：
http://hg.openjdk.java.net/jdk8u/jdk8u60/hotspot/file/37240c1019fd/src/share/vm/prims/unsafe.cpp

4. 在unsafe.cpp其中，有一段这样的代码，这段代码是一个方法，恰恰就是java中Unsafe类的compareAndSwapInt方法所对应的C++方法
UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x))
  UnsafeWrapper("Unsafe_CompareAndSwapInt");
  oop p = JNIHandles::resolve(obj);
  jint* addr = (jint *) index_oop_from_field_offset_long(p, offset);
  return (jint)(Atomic::cmpxchg(x, addr, e)) == e;     // <-- 看这里 <-- 看这里 <-- 看这里
UNSAFE_END
我们可以看出，该方法又调用了Atomic::cmpxchg方法

5. 继续追踪到Atomic类，看看cmpxchg方法：
inline jint     Atomic::cmpxchg    (jint     exchange_value, volatile jint*     dest, jint     compare_value) {
  int mp = os::is_MP();
  __asm__ volatile (LOCK_IF_MP(%4) "cmpxchgl %1,(%3)"
                    : "=a" (exchange_value)
                    : "r" (exchange_value), "a" (compare_value), "r" (dest), "r" (mp)
                    : "cc", "memory");
  return exchange_value;
}
其中，LOCK_IF_MP用于判断是否是多核cpu，如果是，则加锁，否则不加锁。
而现在计算机基本上都是多核的，所以最终cas底层对应的汇编指令是：
lock cmpxchg

lock后面的指令是串行执行的，也就是说，“比较和交换”这一步是原子性的。
也即是当一个cpu在进行cmpxchg操作的时候，其他cpu不允许对cmpxchg所操作的值进行修改！