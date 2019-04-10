package com.wangxuegang.service.user.impl;

import com.wangxuegang.dao.SysUserDao;
import com.wangxuegang.model.SysUser;
import com.wangxuegang.service.user.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    
    @Transactional
	@Override
	public int updateUserInfo(SysUser sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SysUser getUserInfoById(Integer uId) {
		return sysUserDao.selectByPrimaryKey(uId);
	}

	@Override
	public SysUser login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
