package com.alibaba.jtester.bo;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.jtester.dao.IUserDao;
import com.alibaba.jtester.vo.User;

public class UserBoImpl implements IUserBo {
	@Autowired
	private IUserDao userDao;

	public String addUser() {
		this.userDao.addUser();
		return "success";
	}

	public User getUser() {
		return this.userDao.getUser();
	}

	public void removeUser() {
		this.userDao.removeUser();
	}

	public void updateUser() {
		this.userDao.updateUser();
	}
}
