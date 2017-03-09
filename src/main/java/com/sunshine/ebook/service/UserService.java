package com.sunshine.ebook.service;

public interface UserService {
	
	/**
	 * 验证邮箱是否注册
	 * @param email
	 * @return
	 */
	int checkEmailIsRegist(String email);

}
