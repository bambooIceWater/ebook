package com.sunshine.ebook.controller;

import com.sunshine.ebook.common.response.ErrorResponse;
import com.sunshine.ebook.entity.Userinfo;
import com.sunshine.ebook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "根据邮箱判断是否注册")
	@RequestMapping(value = "/checkEmailIsRegist", method = RequestMethod.GET)
	public ResponseEntity checkEmailIsRegist(@ApiParam(value = "邮箱", required = true) @RequestParam("email") String email) {
		Userinfo userinfo = userService.checkEmailIsRegist(email);
		if (userinfo != null) {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "邮箱已被注册");
			return ResponseEntity.badRequest().body(response);
		} else {
			return ResponseEntity.ok().build();
		}
	}

	@ApiOperation(value = "发送验证码")
	@RequestMapping(value = "/sendCheckCode", method = RequestMethod.PUT)
	public ResponseEntity sendCheckCode(
			@ApiParam(value = "注册类型，0=手机号，1=邮箱", required = true) @RequestParam("type") int type,
			@ApiParam(value = "手机号或邮箱", required = true) @RequestParam("target") String target) {
		boolean flag = userService.sendCheckCode(type, target);
		if (flag) {
			return ResponseEntity.ok().build();
		} else {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "验证码发送失败");
			return ResponseEntity.badRequest().body(response);
		}
	}

}
