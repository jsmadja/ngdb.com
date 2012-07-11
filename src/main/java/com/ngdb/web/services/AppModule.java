package com.ngdb.web.services;

import static org.apache.tapestry5.SymbolConstants.APPLICATION_VERSION;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.internal.services.ResourceSymbolProvider;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.History;
import com.ngdb.entities.Market;
import com.ngdb.entities.Museum;
import com.ngdb.entities.Population;
import com.ngdb.entities.Registry;
import com.ngdb.entities.Suggestionner;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.services.infrastructure.CurrencyService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;

@SubModule({ SecurityModule.class })
public class AppModule {

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		String version = ResourceBundle.getBundle("ngdb").getString("version");
		configuration.override(APPLICATION_VERSION, version + "-" + RandomStringUtils.randomNumeric(5));
	}

	public static void contributeComponentMessagesSource(AssetSource assetSource, OrderedConfiguration<Resource> configuration) {
		configuration.add("GlobalCatalogue", assetSource.resourceForPath("ngdb.properties"), "after:AppCatalog");
	}

	public static void contributeHibernateEntityPackageManager(Configuration<String> configuration) {
		configuration.add("com.ngdb.entities");
	}

	public static void bind(ServiceBinder binder) {
		binder.bind(CurrentUser.class);
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
		binder.bind(EmailBuilderService.class);
		binder.bind(TokenService.class);
		binder.bind(MailService.class);
		binder.bind(History.class);
		binder.bind(CurrencyService.class);
		binder.bind(Suggestionner.class);
	}

	public static VelocityEngine buildVelocityEngine() {
		try {
			VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean();
			Properties velocityProperties = new Properties();
			velocityProperties.setProperty("resource.loader", "class");
			velocityProperties.setProperty("class.resource.loader.path", ".");
			velocityProperties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			factoryBean.setVelocityProperties(velocityProperties);
			return factoryBean.createVelocityEngine();
		} catch (IOException e) {
			throw new TapestryException("Cannot create velocity engine", e);
		} catch (VelocityException e) {
			throw new TapestryException("Cannot create velocity engine", e);
		}
	}

	public static JavaMailSender buildMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setDefaultEncoding("UTF-8");
		sender.setHost("localhost");
		return sender;
	}

	@Contribute(PageRenderLinkTransformer.class)
	@Primary
	public static void provideURLRewriting(OrderedConfiguration<PageRenderLinkTransformer> configuration) {
		configuration.addInstance("Faces", NeoGeoDbLinkTransformer.class);
	}

	@Contribute(SymbolSource.class)
	public static void contributeSymbolSource(OrderedConfiguration<SymbolProvider> configuration) {
		configuration.add("NGDB Properties", new ResourceSymbolProvider(new ClasspathResource("ngdb.properties")));
	}

}
