package com.alibaba.test;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.alibaba.jtester.bo.IUserBo;
/**
 * JTester应用示例系统1:<br/>
 * 完成一个由数据库操作CRUD的过程<br/>
 * 主要功能：<br/>
 * <li>用测试配置的数据库替换spring的数据源</li>
 * <li>转入spring的配置，从而获取到注入的实例</li>
 * <li>完成数据库的操作，并带有回滚功能</li>
 */
@SpringApplicationContext({"applicationContext.xml"})
@AutoBeanInject
public class TestCRUD extends JTester{

	@SpringBeanByName
	private IUserBo userBo;
	@Test
	public void testAddUser(){
		
		want.string(userBo.addUser()).isEqualTo("success");
	}
}
