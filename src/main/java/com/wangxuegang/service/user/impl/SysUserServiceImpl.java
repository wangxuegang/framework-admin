package com.wangxuegang.service.user.impl;

import com.wangxuegang.dao.SysUserDao;
import com.wangxuegang.model.SysUser;
import com.wangxuegang.service.user.SysUserService;
import com.wangxuegang.utils.ShiroUtils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao SysUserDao;
	
	@Override
	@Transactional
	public void save(SysUser sysUser) {
		String salt = RandomStringUtils.randomAlphanumeric(20);
		sysUser.setSalt(salt);
		sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(), sysUser.getSalt()));
		sysUser.setStatus(1);
		SysUserDao.insertSelective(sysUser);
	}

}
