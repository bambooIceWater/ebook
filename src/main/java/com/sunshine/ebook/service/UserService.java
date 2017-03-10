package com.sunshine.ebook.service;

import com.sunshine.ebook.entity.Userinfo;

public interface UserService {
	
	/**
	 * 验证邮箱是否注册
	 * @param email
	 * @return
	 */
	Userinfo checkEmailIsRegist(String email);

	/**
	 * 验证手机号是否注册
	 * @param phone
	 * @return
	 */
	Userinfo checkPhoneIsRegist(String phone);

	/**
	 * 保存用户信息
	 * @param userinfo
	 */
	boolean saveUserinfo(Userinfo userinfo);

	/**
	 * 更新用户信息
	 * @param userinfo
	 */
	boolean updateUserinfo(Userinfo userinfo);

	/**
	 * 发送验证码
	 * @param type
	 * @return
	 */
	boolean sendCheckCode(int type, String target);

}
