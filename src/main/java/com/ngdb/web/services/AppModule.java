package com.ngdb.web.services;

import java.util.ResourceBundle;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;

public class AppModule {

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		String version = ResourceBundle.getBundle("ngdb").getString("version");
		configuration.override(SymbolConstants.APPLICATION_VERSION, version);
	}

	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration) {
		BasicRealm realm = new BasicRealm();
		configuration.add(realm);
	}

	public static void contributeHibernateEntityPackageManager(Configuration<String> configuration) {
		configuration.add("com.ngdb.entities");
	}
}
