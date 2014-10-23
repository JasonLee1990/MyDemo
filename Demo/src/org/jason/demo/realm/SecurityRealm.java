package org.jason.demo.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jason.demo.entity.User;
import org.jason.demo.repository.OperationRepository;

public class SecurityRealm extends AuthorizingRealm{
	
	@Resource
	OperationRepository operationRepository;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = principalCollection.oneByType(User.class);
//		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		operationRepository.equals(user.getId());
//		sai.addStringPermissions(permissions);
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		return null;
	}

}
