package com.sunshine.ebook.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.sunshine.ebook.mapper.UserMapper;
import com.sunshine.ebook.request.UserRequest;
import com.sunshine.ebook.util.MD5Util;

public class Userinfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//无参构造
	public Userinfo() {}
	
	public Userinfo(UserRequest userRequest) {
		this.username = userRequest.getUsername();
		this.password = MD5Util.encoderByMd5(userRequest.getPassword());
		int type = userRequest.getType();
		if (0 == type) {
			//手机号
			this.phonenum = userRequest.getTarget();
		} else {
			//邮箱
			this.email = userRequest.getTarget();
		}
		this.checkcode = userRequest.getCheckcode();
	}
	
	private Integer userid;
	private String username;
	private String password;
	private String phonenum;
	private String email;
	private Integer userflag;
	private Integer usertype;
	private String checkcode;
	private Date createtime;
	private Date updatetime;
	private Date lastlogtime;
	private Integer timeout;

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

	public Integer getUserflag() {
		return userflag;
	}

	public void setUserflag(Integer userflag) {
		this.userflag = userflag;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
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

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
