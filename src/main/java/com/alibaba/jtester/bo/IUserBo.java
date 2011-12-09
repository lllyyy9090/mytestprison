package com.alibaba.jtester.bo;

import com.alibaba.jtester.vo.Users;

public interface IUserBo {
	public String addUser(Users user);

	public Users getUser(Users user);

	public void removeUser();

	public void updateUser();
}
