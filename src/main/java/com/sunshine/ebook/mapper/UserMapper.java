package com.sunshine.ebook.mapper;

import java.util.List;

import com.sunshine.ebook.common.mapper.BaseMapper;
import com.sunshine.ebook.entity.Permission;
import com.sunshine.ebook.entity.Role;
import com.sunshine.ebook.entity.Userinfo;

public interface UserMapper extends BaseMapper<Userinfo> {
	
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
	void saveUserinfo(Userinfo userinfo);

	/**
	 * 更新用户信息
	 * @param userinfo
	 */
	void updateUserinfo(Userinfo userinfo);
	
	/**
	 * 校验验证码是否有效
	 * @param userinfo
	 * @return
	 */
	Userinfo checkCodeIsValid(Userinfo userinfo);
	
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
	void saveUserRole(Integer userid, Integer roleid);
	
}
