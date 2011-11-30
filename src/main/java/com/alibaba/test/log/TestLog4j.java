package com.alibaba.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog4j implements Runnable {

	private static Logger logger = LoggerFactory.getLogger("TestLog4j");

	private void init(){
		logger.info("test");
	}
	public void run() {
		int loop = 100000;
		String name = Thread.currentThread().getId() + "\t";
		for (int i = 0; i < loop; i++) {
			logger.error(name + String.valueOf(i));
		}
	}

	public static void main(String[] args) {
		TestLog4j  obj = new TestLog4j();
		obj.init();
		long t = System.currentTimeMillis();
		obj.run(); 
		long t1 = System.currentTimeMillis();
		System.out.println((t1 - t) / 1000f + " s");
	}

}
