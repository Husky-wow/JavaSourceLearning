package com.xxd.learning.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuxiaoding
 * @version 1.0.0
 * @ClassName RemoveTest.java
 * @Description TODO
 */
public class RemoveTest {
    public static void main(String[] args) {
        List<String> list= new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println("list = " + list);
        for (String i:list) {
            if (i=="2") {
                list.remove(i);
            }
            System.out.println(i);
        }
        // System.out.println("list = " + list);
        // java.util.Iterator<String> it = list.iterator();
        // while (it.hasNext()){
        //     if (it.next()=="1"){
        //         list.remove(it.next());
        //     }
        // }
        // System.out.println("list = " + list);
    }
}
