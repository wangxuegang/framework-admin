package com.wangxuegang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wangxuegang.constant.SysConst;
import com.wangxuegang.model.SysUser;
import com.wangxuegang.utils.ShiroUtils;

/**
 * 拦截器
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//获取请求url
		String path = request.getServletPath();
		if(path.matches(SysConst.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			SysUser sysUser = ShiroUtils.getUserEntity();
			if(sysUser!=null){
				return true;
			}else{
				response.sendRedirect(request.getContextPath() + "/sys/login");
				return false;		
			}
		}
		
	}
}
