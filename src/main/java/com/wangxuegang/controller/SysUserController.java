package com.wangxuegang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wangxuegang.model.SysUser;
import com.wangxuegang.service.user.SysUserService;
import com.wangxuegang.utils.APIResponse;

@Api("用户管理")
@Controller
@RequestMapping(value = "/sys")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    
    @ApiOperation("用户新增")
    @PostMapping("/saveSysUser")
    @ResponseBody
    public APIResponse<String> saveSysUser(@RequestBody SysUser user){
    	sysUserService.save(user);
        return APIResponse.success();
    }
    
}
