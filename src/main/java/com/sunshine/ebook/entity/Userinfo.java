package com.sunshine.ebook.entity;

import java.io.Serializable;
import java.util.Date;

public class Userinfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer userid;
	private String username;
	private String password;
	private String phonenum;
	private String email;
	private String userflag;
	private String usertype;
	private String checkcode;
	private Date createtime;
	private Date updatetime;
	private Date lastlogtime;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserflag() {
		return userflag;
	}

	public void setUserflag(String userflag) {
		this.userflag = userflag;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getLastlogtime() {
		return lastlogtime;
	}

	public void setLastlogtime(Date lastlogtime) {
		this.lastlogtime = lastlogtime;
	}
}
