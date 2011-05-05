package com.alibaba.test.annotation;

@MyAnnotation("dd")
public class TestAnnotation {
	public void test() {
		System.out.println("test()");
	}
	public static void main(String[] args) throws Exception {
		String CLASS_NAME = "com.alibaba.test.annotation.TestAnnotation";
		Class<?> test = Class.forName(CLASS_NAME);
		boolean flag = test.isAnnotationPresent(MyAnnotation.class);
		if (flag) {
			MyAnnotation des = (MyAnnotation) test.getAnnotation(MyAnnotation.class);
			System.out.println("√Ë ˆ:" + des.name());
			System.out.println("-----------------");
		}
	}
}
