package com.alibaba.jtester.vo;

import java.util.Date;

/**
 * author: leo.liul
 * Date: 2011-10-08
 * Time: 13:27:41
 * Description:
 */
public class Users{
    private long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String userId;
    private String inviteUserId;
    private String taoAccount;
    private String alipayAccount;
    private String name;
    private String pwd;
    private byte sex;
    private Date birth;
    private String email;
    private String mobile;
    private String province;
    private String city;
    private float money;
    private long regIp;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	public String getTaoAccount() {
		return taoAccount;
	}
	public void setTaoAccount(String taoAccount) {
		this.taoAccount = taoAccount;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public byte getSex() {
		return sex;
	}
	public void setSex(byte sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public long getRegIp() {
		return regIp;
	}
	public void setRegIp(long regIp) {
		this.regIp = regIp;
	}
 
}