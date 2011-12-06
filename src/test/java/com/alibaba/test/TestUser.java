package com.alibaba.test;

import mockit.Mocked;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanFrom;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.alibaba.jtester.bo.IUserBo;
import com.alibaba.jtester.dao.IUserDao;
@SpringApplicationContext({"applicationContext.xml"})
@AutoBeanInject
public class TestUser extends JTester{

	@SpringBeanByName
	private IUserBo userBo;
	
	@SpringBeanByName
	private IUserDao userDao;
	@Test
	public void testAddUser(){
		
		want.string(userBo.addUser()).isEqualTo("ddd");
	}
}
