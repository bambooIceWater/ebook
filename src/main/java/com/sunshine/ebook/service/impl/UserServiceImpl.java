package com.sunshine.ebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sunshine.ebook.mapper.UserMapper;
import com.sunshine.ebook.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int checkEmailIsRegist(String email) {
		return userMapper.checkEmailIsRegist(email);
	}

}
