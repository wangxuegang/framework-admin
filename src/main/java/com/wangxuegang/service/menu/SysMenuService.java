package com.wangxuegang.service.menu;

import java.util.List;

import com.wangxuegang.model.SysMenu;

public interface SysMenuService {

	List<SysMenu> getUserMenuList(Integer userId);

}
