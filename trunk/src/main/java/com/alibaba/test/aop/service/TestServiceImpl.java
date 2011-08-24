package com.alibaba.test.aop.service;

public class TestServiceImpl implements TestService {

	public void process(String value) {
		System.out.println("====业务操作代码=========");
		System.out.println("'TestServiceImpl.process()'\t参数值：" + value);
	}
}
