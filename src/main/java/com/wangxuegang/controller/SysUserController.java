package com.wangxuegang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wangxuegang.service.user.SysUserService;
import com.wangxuegang.utils.APIResponse;

@Api("用户管理")
@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    
    @ApiOperation("用户信息")
    @GetMapping("/getSysUser")
    @ResponseBody
    public APIResponse<String> getSysUser(){
        return APIResponse.success(sysUserService.getUserInfoById(1));
    }
    
}
