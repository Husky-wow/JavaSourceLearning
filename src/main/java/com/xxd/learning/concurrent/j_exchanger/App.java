package com.xxd.learning.concurrent.j_exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Exchange 让两个线程之间可以交换数据，exchange方法是阻塞方法，一个线程调用了之后，会阻塞等待另一个线程调用；
 */
class T implements Runnable {
	private Exchanger<String> exchanger;

	public T(Exchanger<String> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		try {
			String str = "倚天剑";
			System.out.println(Thread.currentThread().getName()+"准备把"+str+"换出去");
			TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 1);
			System.out.println(Thread.currentThread().getName()+"交出了"+str+"!!");
			// 交出str并阻塞等待另一个线程也调用exchanger.exchange方法
			String str2 = exchanger.exchange(str);
			System.out.println(Thread.currentThread().getName()+"得到了"+str2+"!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class T2 implements Runnable {
	private Exchanger<String> exchanger;

	public T2(Exchanger<String> exchanger) {
		this.exchanger = exchanger;
	}
	@Override
	public void run() {
		try {
			String str = "屠龙刀";
			System.out.println(Thread.currentThread().getName()+"准备把"+str+"换出去");
			TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 1);
			System.out.println(Thread.currentThread().getName()+"交出了"+str+"!!");
			String str2 = exchanger.exchange(str);
			System.out.println(Thread.currentThread().getName()+"得到了"+str2+"!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class App {
	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<>();

		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new T(exchanger));
		es.execute(new T2(exchanger));

		es.shutdown();
	}
}
