package com.wangxuegang.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wangxuegang.constant.SysConst;
import com.wangxuegang.dao.SysMenuDao;
import com.wangxuegang.dao.SysUserDao;
import com.wangxuegang.model.SysMenu;
import com.wangxuegang.model.SysUser;
import com.wangxuegang.utils.ShiroUtils;


/**
 * 认证
 */
@Component
public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysMenuDao sysMenuDao;

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		Integer userId = user.getUserId();

		List<String> permsList;

		// 系统管理员，拥有最高权限
		if (userId == SysConst.SUPER_ADMIN) {
			
			List<SysMenu> menuList = sysMenuDao.selectByExample(null);
			
			permsList = new ArrayList<>(menuList.size());
			for (SysMenu menu : menuList) {
				permsList.add(menu.getPerms());
			}
		} else {
			//permsList = sysUserDao.queryAllPerms(userId);
			permsList = null;
		}

		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for (String perms : permsList) {
			if (StringUtils.isBlank(perms)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 查询用户信息
		SysUser sysUser = new SysUser();
		sysUser.setUsername(token.getUsername());
		
		sysUser = sysUserDao.selectOne(sysUser);

		// 账号不存在
		if (sysUser == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 账号锁定
		if (sysUser.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
