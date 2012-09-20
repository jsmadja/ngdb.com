package com.ngdb.web.services;

import com.ngdb.entities.Population;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.SecuritySymbols;

public class SecurityModule {

	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration, @Inject Population population) {
		BasicRealm realm = new BasicRealm(population);
		configuration.add(realm);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SecuritySymbols.LOGIN_URL, "/");
		configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/");
	}

}
