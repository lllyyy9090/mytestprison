package com.alibaba.jtester.bo;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.jtester.dao.IUserDao;
import com.alibaba.jtester.vo.Users;

public class UserBoImpl implements IUserBo {
	@Autowired
	private IUserDao userDao;

	public String addUser(Users user) {
		this.userDao.addUser(user);
		return "success";
	}

	public Users getUser(Users user) {
		return this.userDao.getUser(user);
	}

	public void removeUser() {
		this.userDao.removeUser();
	}

	public void updateUser() {
		this.userDao.updateUser();
	}
}
