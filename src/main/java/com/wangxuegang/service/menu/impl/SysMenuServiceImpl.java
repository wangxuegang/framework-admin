package com.wangxuegang.service.menu.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxuegang.constant.SysConst;
import com.wangxuegang.dao.SysMenuDao;
import com.wangxuegang.model.SysMenu;
import com.wangxuegang.service.menu.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuDao sysMenuDao;

	@Override
	public List<SysMenu> getUserMenuList(Integer userId) {
		// 系统管理员，拥有最高权限
		if (userId == SysConst.SUPER_ADMIN) {
			List<SysMenu> menuList = sysMenuDao.queryListParentId(SysConst.SUPER_ADMIN);
			return getMenuTreeList(menuList,null);
		}
		return null;
		
	}
	
	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		List<List<SysMenu>> sysMenuList = new ArrayList<List<SysMenu>>();

		for (SysMenu entity : menuList) {
			// 目录
			if (entity.getType() == SysConst.MenuType.CATALOG.getValue()) {
				sysMenuList.add(getMenuTreeList(sysMenuDao.queryListParentId(SysConst.SUPER_ADMIN), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		System.out.println(subMenuList);
		
		return subMenuList;
	}
}
