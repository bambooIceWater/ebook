package com.sunshine.ebook.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sunshine.ebook.common.response.ErrorResponse;
import com.sunshine.ebook.entity.Userinfo;
import com.sunshine.ebook.request.UserRequest;
import com.sunshine.ebook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "判断手机号或邮箱是否注册")
	@RequestMapping(value = "/checkPhoneOrEmailIsRegist", method = RequestMethod.GET)
	public ResponseEntity checkEmailIsRegist(@ApiParam(value = "注册类型，0=手机号注册，1=邮箱注册", required = true) @RequestParam("type") int type,
			@ApiParam(value = "手机号或邮箱", required = true) @RequestParam("target") String target) {
		Userinfo condition = new Userinfo();
		if (0 == type) {
			condition.setPhonenum(target);
		} else {
			condition.setEmail(target);
		}
		Userinfo userinfo = userService.getUserinfoByCondition(condition);
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
	
	@ApiOperation(value = "用户注册")
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public ResponseEntity userRegister(
			@ApiParam(value = "Json请求体", required = true) @RequestBody UserRequest userRequest) {
		boolean checkCodeIsValid = userService.checkCodeIsValid(userRequest);
		if (!checkCodeIsValid) {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "验证码错误");
			return ResponseEntity.badRequest().body(response);
		}
		Userinfo userinfo = userService.checkCodeIsOverdue(userRequest);
		if (null == userinfo) {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "验证码不在有效期");
			return ResponseEntity.badRequest().body(response);
		}
		Userinfo user = new Userinfo(userRequest);
		user.setUserid(userinfo.getUserid());
		boolean flag = userService.updateUserinfo(user);
		if (flag) {
			return ResponseEntity.ok().build();
		} else {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "用户注册失败");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@ApiOperation(value = "找回密码")
	@RequestMapping(value = "/findPassword", method = RequestMethod.GET)
	public ResponseEntity findPassword(
			@ApiParam(value = "Json请求体", required = true) @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok().build();
	}

}
