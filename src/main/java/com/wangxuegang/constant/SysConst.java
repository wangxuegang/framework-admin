package com.wangxuegang.constant;

import org.springframework.stereotype.Component;


@Component
public class SysConst {
	
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	
	/** 不对匹配该值的访问路径拦截（正则） */
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(js)|(css)|(images)|(error)|(sessionOut)|(swagger)|(v2)|(webjars)|(configuration)).*";	

    /** 菜单类型 */
	public enum MenuType {
		
		/** 目录 */
		CATALOG(0),
		/** 菜单 */
		MENU(1),
		/** 按钮 */
		BUTTON(2);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
