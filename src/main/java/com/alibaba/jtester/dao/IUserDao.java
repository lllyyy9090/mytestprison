package com.alibaba.jtester.dao;

import com.alibaba.jtester.vo.Users;

public interface IUserDao {
	public void addUser(Users user);

	public Users getUser(Users user);

	public void removeUser();

	public void updateUser();

}
