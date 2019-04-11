package com.wangxuegang.constant;

import org.springframework.stereotype.Component;


@Component
public class SysConst {
	
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	
    /**
     * session的key
     */
    public static String LOGIN_SESSION_KEY = "login_user";
    
    public static final String USER_IN_COOKIE = "S_L_ID";


    /**
     * aes加密加盐
     */
    public static String AES_SALT = "0123456789abcdef";

}
