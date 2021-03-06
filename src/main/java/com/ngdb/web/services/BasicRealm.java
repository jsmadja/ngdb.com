package com.ngdb.web.services;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class BasicRealm extends AuthorizingRealm {

    private static final String NAME = "ngdb-realm";

    private Population population;

    public BasicRealm(Population population) {
        super.setCacheManager(new MemoryConstrainedCacheManager());
        super.setName(NAME);
        super.setAuthenticationTokenClass(UsernamePasswordToken.class);
        this.population = population;
        configureCredentialsMatcher();
    }

    private void configureCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME);
        matcher.setStoredCredentialsHexEncoded(false);
        super.setCredentialsMatcher(matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<Permission> permissions = new HashSet<Permission>();
        User user = getUser(principals.getPrimaryPrincipal().toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addObjectPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        usernamePasswordToken.setRememberMe(true);
        String login = usernamePasswordToken.getUsername();
        User user = getUser(login);
        if (user == null) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(user.getLogin(), user.getPassword(), null, BasicRealm.NAME);
    }

    private User getUser(String login) {
        return population.findByLogin(login);
    }
}