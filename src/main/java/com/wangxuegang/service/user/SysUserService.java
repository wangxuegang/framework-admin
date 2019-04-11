package com.wangxuegang.service.user;


import org.springframework.stereotype.Service;

import com.wangxuegang.model.SysUser;

@Service
public interface SysUserService {
	
	public void save(SysUser user);
}
