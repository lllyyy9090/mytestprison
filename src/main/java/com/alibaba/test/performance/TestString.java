package com.alibaba.test.performance;

public class TestString {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		StringBuffer sb2 = new StringBuffer(100000);
		for (int i = 0; i < 500000; i++) {
			sb2.append(i);
		}
		
		long t2= System.currentTimeMillis();
		System.out.println((t2-t1)/1000f);
		sb2 = null;
		t2= System.currentTimeMillis();
		StringBuilder sb = new StringBuilder(100000);
		for (int i = 0; i < 500000; i++) {
			sb.append(i);
		}
		System.out.println((System.currentTimeMillis()-t2)/1000f);
	}
}
