package com.alibaba.jtester.dao;

import com.alibaba.jtester.vo.User;

public interface IUserDao {
	public void addUser();

	public User getUser();

	public void removeUser();

	public void updateUser();

}
