1. 在命令行键入以下命令
java -XX:+PrintCommandLineFlags -version

打印结果是：
-XX:InitialHeapSize=266479872 -XX:MaxHeapSize=4263677952 -XX:+PrintCommandLineFlags
-XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)

2. 注意以上的"-XX:+UseCompressedClassPointers"和“-XX:+UseCompressedOops”
作为64位的jvm，那么其中一个指针的长度也应该是64位（也就是8个字节）。
但是由于jvm开启了“UseCompressedClassPointers”这个参数，就会把8个字节的class pointer指针压成4个字节的指针！
同样由于jvm开启了“UseCompressedOops””这个参数，就会把8个字节的ordinary object pointer指针也压缩成4个字节的指针！

3. 运行App应用程序，打印结果如下：
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

object header一共占12个字节，其中markword占8个字节（64位，如果是32位操作系统则markwork也为32位），class pointer占4个字节。
object header之后应该是对象的成员变量的存储空间，可是object没有成员变量，所以也就没有这部分的空间。
最后的4个字节是padding，用于填补对象所占字节到8的整数倍的。

4. 思考，如果没有开启"-XX:+UseCompressedClassPointers"和“-XX:+UseCompressedOops”， Object对象是几个字节？





