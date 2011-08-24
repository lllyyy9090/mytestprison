package com.alibaba.test.annotation;

import java.util.Calendar;

@MyAnnotation("dd")
public class TestAnnotation {
	public void test() {
		System.out.println("test()");
	}
	public static void main(String[] args) throws Exception {
//		String CLASS_NAME = "com.alibaba.test.annotation.TestAnnotation";
//		Class<?> test = Class.forName(CLASS_NAME);
//		boolean flag = test.isAnnotationPresent(MyAnnotation.class);
//		if (flag) {
//			MyAnnotation des = (MyAnnotation) test.getAnnotation(MyAnnotation.class);
//			System.out.println("√Ë ˆ:" + des.name());
//			System.out.println("-----------------");
//		}
//		Calendar now = Calendar.getInstance();
//		now.set(Calendar.AM_PM, Calendar.AM);
//		now.set(Calendar.HOUR, 0);
//		now.set(Calendar.MINUTE, 0);
//		now.set(Calendar.SECOND, 0);
//		System.out.println(now.getTime());
		for(int i=1;i<20;i++){
			System.out.println("http://www.pfwx.com/booktopallvisit/0/"+i+".html");
		}
	}
}
