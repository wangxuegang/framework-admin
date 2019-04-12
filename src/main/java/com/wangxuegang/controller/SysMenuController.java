package com.wangxuegang.controller;



import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wangxuegang.model.SysMenu;
import com.wangxuegang.service.menu.SysMenuService;
import com.wangxuegang.utils.APIResponse;
import com.wangxuegang.utils.ShiroUtils;



@Api("菜单")
@Controller
@RequestMapping(value = "/sys")
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
    @ApiOperation("导航菜单")
    @GetMapping("/nav")
    @ResponseBody
    public APIResponse menuList(){
    	List<SysMenu> menuList = sysMenuService.getUserMenuList(ShiroUtils.getUserId());
		return APIResponse.success(menuList) ;
    }
}
