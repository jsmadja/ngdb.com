package com.ngdb.web.services;

import java.util.ResourceBundle;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.jpa.JpaSymbols;
import org.apache.tapestry5.spring.ApplicationContextCustomizer;

public class AppModule {

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		String version = ResourceBundle.getBundle("ngdb").getString("version");
		configuration.override(SymbolConstants.APPLICATION_VERSION, version);
		configuration.override(JpaSymbols.PERSISTENCE_DESCRIPTOR, "persistence.xml");
	}

	public static void contributeApplicationContextCustomizer(OrderedConfiguration<ApplicationContextCustomizer> config) {
		config.add("MyApplicationContextCustomizer", new com.ngdb.web.application.ApplicationContextCustomizer());
	}

	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration) {
		BasicRealm realm = new BasicRealm();
		configuration.add(realm);
	}

}
