package com.alibaba.test;

import mockit.Mocked;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.alibaba.jtester.bo.IUserBo;
@SpringApplicationContext
@AutoBeanInject
public class TestUser extends JTester{

	@Mocked
	private IUserBo userBo;
	@Test
	public void testAddUser(){
		
		 userBo.addUser();
	}
}
