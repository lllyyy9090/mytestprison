package com.alibaba.test;

import org.testng.annotations.Test;
import java.util.Date;

import mockit.Mocked;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;

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
public class TestInjectAndMocked extends JTester{

	@SpringBeanByName
	private IUserBo userBo;
	
	//@SpringBeanByName("userBo")
	@Mocked
	private IUserBo userBo2;
	
	/**
	 * 通过Spring注入userBo
	 */
	@Test
	public void testAddUserByInject(){
		//验证
		Users user = new Users();
		user.setUserId("test_1");
		user.setInviteUserId("test_2");
		user.setName("test");
		user.setTaoAccount("闪亮的张");
		user.setAlipayAccount("alipay_account@126.com");
		user.setPwd("111111");
		user.setSex((byte)1);
		user.setBirth(new Date());
		user.setCity("杭州");
		user.setProvince("浙江");
		user.setEmail("test@126.com");
		user.setMobile("11111111111");
		user.setMoney(99f);
		user.setRegIp(2222222222222l);
		want.string(userBo.addUser(user)).isEqualTo("success");
	}
	
	/**
	 * 通过Mocked
	 */
	@Test
	public void tesstAddUserByMocked(){
		final Users user = new Users();
		//录制
		new Expectations(){
			{
				userBo2.addUser(user);//方法名
				result = "mocked";//返回值
			}
		};
		//验证
		
		want.string(userBo2.addUser(user)).isEqualTo("mocked");
	}
}
