package com.wangxuegang.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Api("导航管理")
@Controller
@RequestMapping(value = "/sys")
public class SysHomeController {
	
    @ApiOperation("跳转首页")
    @GetMapping("/home")
    public String home(){
    	return "home.html";
    }
    
    @ApiOperation("跳转用户管理页面")
    @GetMapping("/userManage")
    public String userManage(){
    	return "user.html";
    }
}
