package com.wangxuegang.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wangxuegang.utils.APIResponse;
import com.wangxuegang.utils.ShiroUtils;


@Api("登录管理")
@Controller
@RequestMapping(value = "/sys")
public class SysLoginController {
	
    @ApiOperation("跳转登录页")
    @GetMapping("/login")
    public String login(){
    	return "login.html";
    }
    
    @ApiOperation("登录")
    @PostMapping("/login")
    @ResponseBody
	public APIResponse login(   HttpServletRequest request,
					            HttpServletResponse response,
					            @ApiParam(name = "username", value = "用户名", required = true)
					            @RequestParam(name = "username", required = true)
					            String username,
					            @ApiParam(name = "password", value = "密码", required = true)
					            @RequestParam(name = "password", required = true)
					            String password){
    	try {
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return new APIResponse(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return new APIResponse("账号或密码不正确");
		} catch (LockedAccountException e) {
			return new APIResponse("账号已被锁定,请联系管理员");
		} catch (AuthenticationException e) {
			return new APIResponse("账户验证失败");
		}
    	
    	System.out.println(ShiroUtils.getUserEntity().toString());
    	
    	return APIResponse.success();
    }
}
