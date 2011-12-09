package com.alibaba.test;

import mockit.Mocked;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.core.IJTester.Expectations;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.alibaba.jtester.bo.IUserBo;
import com.alibaba.jtester.vo.Users;
/**
 * JTester应用示例系统1:<br/>
 * 完成一个由数据库操作CRUD的过程<br/>
 * 主要功能：<br/>
 * <li>实现Spring的注入</li>
 * <li>实现将对象Mocked</li>
 */
@SpringApplicationContext({"applicationContext.xml"})
@AutoBeanInject
public class TestJdbc extends JTester{

	@SpringBeanByName
	private IUserBo userBo;
	
	//@SpringBeanByName("userBo")
	@Mocked
	private IUserBo userBo2;
	
	/**
	 * 通过Spring注入userBo
	 */
	/**
	 * 通过Spring注入userBo
	 */
	@Test
	public void testJdbc(){
		//验证
		Users user = new Users();
		user.setUserId("1");
		user.setInviteUserId("1");
		user.setAlipayAccount("cenly60@163.com");
		want.string(userBo.addUser(user)).isEqualTo("success");
	}
	
	/**
	 * 通过Mocked
	 */
	@Test
	public void testAddUserByMocked(){
		
		//录制
		new Expectations(){
			{
				Users user = new Users();
				userBo2.addUser(user);//方法名
				result = "mocked";//返回值
			}
		};
		//验证
		Users user = new Users();
		want.string(userBo2.addUser(user)).isEqualTo("mocked");
	}
}
