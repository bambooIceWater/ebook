package com.sunshine.ebook.service.impl;

import com.sunshine.ebook.entity.Userinfo;
import com.sunshine.ebook.util.SendCheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sunshine.ebook.mapper.UserMapper;
import com.sunshine.ebook.request.UserRequest;
import com.sunshine.ebook.service.UserService;

import java.util.Date;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Value("${ebook.checkcode.timeout}")
	private int timeout;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Userinfo getUserinfoByCondition(Userinfo userinfo) {
		try {
			return userMapper.getUserinfoByCondition(userinfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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
	public boolean sendCheckCode(int sendType, int type, String target) {
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
			Userinfo queryCondition = new Userinfo();
			if (0 == type) {
				queryCondition.setPhonenum(target);
			} else if (1 == type) {
				queryCondition.setEmail(target);
			}
			if (sendType == 1) {
				// 密码找回
				queryCondition.setUserflag(0);
			}
			existUser = this.getUserinfoByCondition(queryCondition);
			boolean saveFlag = false;
			if (null == existUser) {
				//不存在注册记录，则插入数据
				saveFlag = this.saveUserinfo(userinfo);
			} else {
				//已经存在注册记录，则更新数据
				userinfo.setCreatetime(null);
				userinfo.setUpdatetime(new Date());
				userinfo.setUserid(existUser.getUserid());
				if (sendType == 0) {
					userinfo.setUserflag(-1);
				}
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

	@Override
	public boolean checkCodeIsValid(UserRequest userRequest) {
		boolean flag = false;
		String checkCode = userRequest.getCheckcode();
		Userinfo user = new Userinfo();
		user.setCheckcode(checkCode);
		if (userRequest.getType() == 0) {
			// 手机号
			user.setPhonenum(userRequest.getTarget());
		} else {
			// 邮箱
			user.setEmail(userRequest.getTarget());
		}
		Userinfo userinfo = userMapper.getUserinfoByCondition(user);
		if (null != userinfo) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Userinfo checkCodeIsOverdue(UserRequest userRequest) {
		Userinfo userinfo = new Userinfo(userRequest);
		userinfo.setTimeout(timeout);
		Userinfo user = userMapper.checkCodeIsValid(userinfo);
		return user;
	}

}
