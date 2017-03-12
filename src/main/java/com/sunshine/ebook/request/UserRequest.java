package com.sunshine.ebook.request;

import io.swagger.annotations.ApiModel;

@ApiModel("用户注册请求体")
public class UserRequest {

	private String username;
	private String password;
	private int registType;
	private String target;
	private String checkcode;

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

	public int getRegistType() {
		return registType;
	}

	public void setRegistType(int registType) {
		this.registType = registType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

}
