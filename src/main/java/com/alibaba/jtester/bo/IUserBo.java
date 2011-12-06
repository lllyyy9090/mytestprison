package com.alibaba.jtester.bo;

import com.alibaba.jtester.vo.User;

public interface IUserBo {
	public String addUser();

	public User getUser();

	public void removeUser();

	public void updateUser();
}
