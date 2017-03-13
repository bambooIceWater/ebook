package com.sunshine.ebook.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "判断手机号或邮箱是否存在")
	@RequestMapping(value = "/checkPhoneOrEmailIsRegist", method = RequestMethod.GET)
	public ResponseEntity checkEmailIsRegist(@ApiParam(value = "调用类型，0=注册，1=密码找回", required = true) @RequestParam("invokeType") int invokeType,
			@ApiParam(value = "注册类型，0=手机号注册，1=邮箱注册", required = true) @RequestParam("type") int type,
			@ApiParam(value = "手机号或邮箱", required = true) @RequestParam("target") String target) {
		String key = null;
		Userinfo condition = new Userinfo();
		if (0 == type) {
			key = "手机号";
			condition.setPhonenum(target);
		} else {
			key = "邮箱";
			condition.setEmail(target);
		}
		condition.setUserflag(0);
		Userinfo userinfo = userService.getUserinfoByCondition(condition);
		if (userinfo != null) {
			if (0 == invokeType) {
				ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), key + "已被注册");
				return ResponseEntity.badRequest().body(response);
			} else {
				return ResponseEntity.ok().build();
			}
		} else {
			if (0 == invokeType) {
				return ResponseEntity.ok().build();
			} else {
				ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), key + "不存在");
				return ResponseEntity.badRequest().body(response);
			}
		}
	}

	@ApiOperation(value = "发送验证码")
	@RequestMapping(value = "/sendCheckCode", method = RequestMethod.PUT)
	public ResponseEntity sendCheckCode(
			@ApiParam(value = "发送类型，0=注册，1=密码找回", required = true) @RequestParam("sendType") int sendType,
			@ApiParam(value = "注册类型，0=手机号，1=邮箱", required = true) @RequestParam("type") int type,
			@ApiParam(value = "手机号或邮箱", required = true) @RequestParam("target") String target) {
		boolean flag = userService.sendCheckCode(sendType, type, target);
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
		user.setUserflag(0);
		boolean flag = userService.updateUserinfo(user);
		if (flag) {
			return ResponseEntity.ok().build();
		} else {
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "用户注册失败");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@ApiOperation(value = "修改密码")
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public ResponseEntity modifyPassword(
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
			ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "修改密码失败");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@ApiOperation(value = "用户登录")
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public ResponseEntity userLogin(
			@ApiParam(value = "手机号或邮箱", required = true) @RequestParam("target") String target,
			@ApiParam(value = "密码", required = true) @RequestParam("password") String password) {
		String username = target;
		UsernamePasswordToken token = new UsernamePasswordToken(target, password);
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.hasRole("admin");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		}catch(UnknownAccountException uae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			//redirectAttributes.addFlashAttribute("message", "未知账户");
		}catch(IncorrectCredentialsException ice){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			ice.printStackTrace();
			//redirectAttributes.addFlashAttribute("message", "密码不正确");
		}catch(LockedAccountException lae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			//redirectAttributes.addFlashAttribute("message", "账户已锁定");
		}catch(ExcessiveAttemptsException eae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			//redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		}catch(AuthenticationException ae){
			//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			//redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		return null;
	}

	@ApiOperation(value = "上传文件")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@RequiresRoles("admin")
	public ResponseEntity upload() {
		System.out.println("-------------------------------------");
		return null;
	}


}
