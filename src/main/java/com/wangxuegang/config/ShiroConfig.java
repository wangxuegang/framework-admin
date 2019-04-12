package com.wangxuegang.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wangxuegang.shiro.UserRealm;


/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {
	
	@Bean
    public UserRealm myUserRealm() {
		UserRealm userRealm = new UserRealm();
        return userRealm;
    }
	
	@Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myUserRealm());
        return securityManager;
    }
	
	@Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        shiroFilterFactoryBean.setLoginUrl("login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        
        Map<String,String> map = new HashMap<String, String>();
        
        //允许请求的静态资源   start
		map.put("/css/**", "anon");
		map.put("/js/**", "anon");
		map.put("/images/**", "anon");
		// 允许请求的静态资源   end
		
		//swagger2 允许请求的资源   start
		map.put("/swagger-ui.html", "anon");
		map.put("/swagger-resources/**", "anon");
		map.put("/v2/**", "anon");
		map.put("/webjars/**", "anon");
		map.put("/configuration/**", "anon");
		//swagger2 允许请求的资源   end
		
		//允许请求的系统资源   start
		map.put("/sys/**", "anon");
		/*map.put("/sys/saveSysUser", "anon");
		map.put("/sys/nav", "anon");
		map.put("/sys/login", "anon");
		map.put("/sys/logout", "anon");*/
		//允许请求的系统资源   end
		
		/** 认证才可以访问 */
		map.put("/**", "authc");
		
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
	
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
	/*@Bean("securityManager")
	public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		securityManager.setSessionManager(sessionManager);

		return securityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login.html");
		shiroFilter.setUnauthorizedUrl("/");

		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/swagger/**", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/swagger-resources/**", "anon");

		filterMap.put("/statics/**", "anon");
		filterMap.put("/login.html", "anon");
		filterMap.put("/sys/login", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/captcha.jpg", "anon");
		filterMap.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}*/
}
