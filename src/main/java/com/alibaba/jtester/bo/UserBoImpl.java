package com.alibaba.jtester.bo;

import com.alibaba.jtester.dao.IUserDao;
import com.alibaba.jtester.vo.User;

public class UserBoImpl implements IUserBo {
	private IUserDao userDao;

	public String addUser() {
		this.getUserDao().addUser();
		return "success";
	}

	public User getUser() {
		return this.getUserDao().getUser();
	}

	public void removeUser() {
		this.getUserDao().removeUser();
	}

	public void updateUser() {
		this.getUserDao().updateUser();
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
}
