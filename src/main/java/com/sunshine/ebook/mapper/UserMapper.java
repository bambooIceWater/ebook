package com.sunshine.ebook.mapper;

import com.sunshine.ebook.common.mapper.BaseMapper;
import com.sunshine.ebook.entity.Userinfo;

public interface UserMapper extends BaseMapper<Userinfo> {
	
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
	void saveUserinfo(Userinfo userinfo);

	/**
	 * 更新用户信息
	 * @param userinfo
	 */
	void updateUserinfo(Userinfo userinfo);
	
}
