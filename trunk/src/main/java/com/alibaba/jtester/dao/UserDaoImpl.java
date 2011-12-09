package com.alibaba.jtester.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.jtester.vo.Users;

public class UserDaoImpl  implements IUserDao{
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	public void addUser(Users user) {
		String sql = "insert into users ( user_id, invite_user_id, tao_account, alipay_account, name," +
				" pwd, sex, birth,  email, mobile, " +
				" province, city,  money, reg_ip, gmt_create, gmt_modified) values" +
				"(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?, now(),now())";
		
		Object []objs = new Object[]{user.getUserId(), 
				                     user.getInviteUserId(),
				                     user.getTaoAccount(),
				                     user.getAlipayAccount(),
				                     user.getName(),
				                     user.getPwd(),
				                     user.getSex(),
				                     user.getBirth(),
				                     user.getEmail(),
				                     user.getMobile(),
				                     user.getProvince(),
				                     user.getCity(),
				                     user.getMoney(),
				                     user.getRegIp()
				                     };
		jdbcTemplate.update(sql,objs);
	}

	public Users getUser(Users user) {
		return null;
	}

	public void removeUser() {
		
	}

	public void updateUser() {
		
	}

}
