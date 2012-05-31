package com.ngdb.web.services;

import static org.apache.tapestry5.SymbolConstants.APPLICATION_VERSION;

import java.util.ResourceBundle;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.AssetSource;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.Market;
import com.ngdb.entities.Museum;
import com.ngdb.entities.Population;
import com.ngdb.entities.Registry;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.services.infrastructure.PictureService;
import com.ngdb.web.services.infrastructure.UserSession;

@SubModule({ SecurityModule.class })
public class AppModule {

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		String version = ResourceBundle.getBundle("ngdb").getString("version");
		configuration.override(APPLICATION_VERSION, version);
	}

	public static void contributeComponentMessagesSource(AssetSource assetSource, OrderedConfiguration<Resource> configuration) {
		configuration.add("GlobalCatalogue", assetSource.resourceForPath("ngdb.properties"), "after:AppCatalog");
	}

	public static void contributeHibernateEntityPackageManager(Configuration<String> configuration) {
		configuration.add("com.ngdb.entities");
	}

	public static void bind(ServiceBinder binder) {
		binder.bind(UserSession.class);
		binder.bind(PictureService.class);
		binder.bind(WishBox.class);
		binder.bind(Museum.class);
		binder.bind(ReferenceService.class);
		binder.bind(Market.class);
		binder.bind(GameFactory.class);
		binder.bind(HardwareFactory.class);
		binder.bind(ArticleFactory.class);
		binder.bind(Population.class);
		binder.bind(Registry.class);
	}
}
