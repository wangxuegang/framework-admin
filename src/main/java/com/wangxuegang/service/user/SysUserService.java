package com.wangxuegang.service.user;


import org.springframework.stereotype.Service;

import com.wangxuegang.model.SysUser;

@Service
public interface SysUserService {

    int updateUserInfo(SysUser sysUser);

    SysUser getUserInfoById(Integer uId);

    SysUser login(String username, String password);

}
