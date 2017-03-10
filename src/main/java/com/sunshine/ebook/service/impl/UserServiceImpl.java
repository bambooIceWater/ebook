package com.sunshine.ebook.service.impl;

import com.sunshine.ebook.entity.Userinfo;
import com.sunshine.ebook.util.SendCheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sunshine.ebook.mapper.UserMapper;
import com.sunshine.ebook.service.UserService;

import java.util.Date;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public Userinfo checkEmailIsRegist(String email) {
		return userMapper.checkEmailIsRegist(email);
	}

	@Override
	public Userinfo checkPhoneIsRegist(String phone) {
		return userMapper.checkPhoneIsRegist(phone);
	}

	@Override
	public boolean saveUserinfo(Userinfo userinfo) {
		boolean flag = false;
		try {
			userMapper.saveUserinfo(userinfo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateUserinfo(Userinfo userinfo) {
		boolean flag = false;
		try {
			userMapper.updateUserinfo(userinfo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean sendCheckCode(int type, String target) {
		boolean flag = false;
		try {
			String checkCode = SendCheckCode.getCheckCode(type);
			Userinfo userinfo = new Userinfo();
			if (0 == type) {
				userinfo.setPhonenum(target);
			} else if (1 == type) {
				userinfo.setEmail(target);
			}
			userinfo.setCheckcode(checkCode);
			userinfo.setCreatetime(new Date());
			userinfo.setUpdatetime(new Date());
			Userinfo existUser = null;
			if (0 == type) {
				existUser = this.checkPhoneIsRegist(target);
			} else if (1 == type) {
				existUser = this.checkEmailIsRegist(target);
			}
			boolean saveFlag = false;
			if (null == existUser) {
				//不存在注册记录，则插入数据
				saveFlag = this.saveUserinfo(userinfo);
			} else {
				//已经存在注册记录，则更新数据
				userinfo.setCreatetime(null);
				userinfo.setUserid(existUser.getUserid());
				userinfo.setUserflag("-1");
				saveFlag = this.updateUserinfo(userinfo);
			}
			if (saveFlag) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
