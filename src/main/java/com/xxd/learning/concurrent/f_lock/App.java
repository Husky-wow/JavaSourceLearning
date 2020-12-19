package com.xxd.learning.concurrent.f_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
	该例子演示了Lock代替synchronized的效果
*/
public class App {
	static class Outputer {
		private Lock lock = new ReentrantLock();
		public  void print(String str) {
			while (true) {
				try {
					lock.lock();
					for (int i = 0; i < str.length(); i++) {
						char c = str.charAt(i);
						System.out.print(c);
					}
				} finally {
					System.out.println();
					lock.unlock();
				}
			}
		}
	}

	static class T implements Runnable {
		private Outputer outputer;
		public T(Outputer outputer) {
			this.outputer = outputer;
		}
		@Override
		public void run() {
			outputer.print("abcd");
		}
	}

	static class T2 implements Runnable {
		private Outputer outputer;
		public T2(Outputer outputer) {
			this.outputer = outputer;
		}
		@Override
		public void run() {
			outputer.print("1234");
		}
	}

	public static void main(String[] args) {
		Outputer outputer = new Outputer();

		new Thread(new T(outputer)).start();
		new Thread(new T2(outputer)).start();
	}
}
