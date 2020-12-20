package com.xxd.learning.concurrent.k_volatile.b;

import java.util.ArrayList;
import java.util.List;

// 该例子演示了，volatile并不能保证原子性

public class App {
	volatile int count = 0;

	/* synchronized */ void f1() {
		for (int i = 1; i <= 1000; i++) {
			/*
			这段代码在汇编层面是3句话：
				1. 取出 count；
				2. +1；
				3. 放回去
			所以要是10000，必须保证原子性
			 */
			count++;
		}
	}

	public static void main(String[] args) {
		App app = new App();

		List<Thread> list = new ArrayList<>();
		for(int i = 1; i <= 10; i++) {
			Thread th = new Thread(app::f1);
			list.add(th);
		}

		for (Thread th : list) {
			th.start();
		}
		for (Thread th : list) {
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 如果volatile保证原子性，那么结果将会是10000
		System.out.println(app.count);

	}
}
