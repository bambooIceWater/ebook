package com.sunshine.ebook.mapper;

import com.sunshine.ebook.common.mapper.BaseMapper;
import com.sunshine.ebook.entity.Userinfo;

public interface UserMapper extends BaseMapper<Userinfo> {
	
	/**
	 * 验证邮箱是否注册
	 * @param email
	 * @return
	 */
	int checkEmailIsRegist(String email);
	
}
