package com.sunshine.ebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sunshine.ebook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "根据邮箱判断是否注册")
	@RequestMapping(value = "/checkEmailIsRegist/{email}", method = RequestMethod.GET)
	public void checkEmailIsRegist(@PathVariable String email) {
		int count = userService.checkEmailIsRegist(email);
		String msg = "";
		if (count > 0) {
			msg = "邮箱已被注册";
		}
		System.out.println(msg);
	}

}
