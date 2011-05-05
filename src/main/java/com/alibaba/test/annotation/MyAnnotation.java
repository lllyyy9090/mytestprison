package com.alibaba.test.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME)  
public @Inherited @interface MyAnnotation {
	int id() default (1);
	String name() default ("Áù¸öµ°");
	String value();
}
