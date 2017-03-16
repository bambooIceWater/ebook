package com.sunshine.ebook.service;

import java.util.List;

import com.sunshine.ebook.entity.Permission;
import com.sunshine.ebook.entity.Role;
import com.sunshine.ebook.entity.Userinfo;
import com.sunshine.ebook.request.UserRequest;

public interface UserService {
	
	/**
	 * 根据条件查询用户信息
	 * @param userinfo
	 * @return
	 */
	Userinfo getUserinfoByCondition(Userinfo userinfo);
	
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
	 * 注册用户
	 * @param userinfo
	 * @return
	 */
	boolean registerUserinfo(Userinfo userinfo);

	/**
	 * 发送验证码
	 * @param type
	 * @return
	 */
	boolean sendCheckCode(int sendType, int type, String target);
	
	/**
	 * 校验验证码是否有效
	 * @param userRequest
	 * @return
	 */
	boolean checkCodeIsValid(UserRequest userRequest);
	
	/**
	 * 校验验证码是否过期
	 * @param userRequest
	 * @return
	 */
	Userinfo checkCodeIsOverdue(UserRequest userRequest);
	
	/**
	 * 根据用户ID获取用户角色信息
	 * @param userid
	 * @return
	 */
	List<Role> getRolesByUserId(Integer userid);
	
	/**
	 * 根据角色ID获取权限信息
	 * @param roleid
	 * @return
	 */
	List<Permission> getPermissionsByRoleId(Integer roleid);

	/**
	 * 保存用户角色信息
	 * @param userid
	 * @param roleid
	 */
	boolean saveUserRole(Integer userid, Integer roleid);

}
