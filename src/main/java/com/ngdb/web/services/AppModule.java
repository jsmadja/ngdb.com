package com.ngdb.web.services;

import com.ngdb.Barcoder;
import com.ngdb.entities.*;
import com.ngdb.entities.Registry;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.services.Cacher;
import com.ngdb.services.HibernateSearchService;
import com.ngdb.services.SNK;
import com.ngdb.web.services.infrastructure.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.hibernate.HibernateConfigurer;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.hibernate.HibernateSymbols;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.internal.services.ResourceSymbolProvider;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.ResourceBundle;

@SubModule({SecurityModule.class})
public class AppModule {

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add(HibernateSymbols.ENTITY_SESSION_STATE_PERSISTENCE_STRATEGY_ENABLED, "true");
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
        String version = ResourceBundle.getBundle("ngdb").getString("version");
        configuration.override(SymbolConstants.SUPPORTED_LOCALES, "en,fr,de");
        configuration.override(SymbolConstants.APPLICATION_VERSION, version);
        configuration.override(HibernateSymbols.EARLY_START_UP, true);
    }

    public static void contributeComponentMessagesSource(AssetSource assetSource, OrderedConfiguration<Resource> configuration) {
        configuration.add("GlobalCatalogue", assetSource.resourceForPath("ngdb.properties"), "after:AppCatalog");
    }

    public static void contributeHibernateEntityPackageManager(Configuration<String> configuration) {
        configuration.add("com.ngdb.entities");
    }

    @Contribute(HibernateSessionSource.class)
    public static void contributeHibernateSessionSource(OrderedConfiguration<HibernateConfigurer> configuration,
                                                        final CurrencyService currencyService) {
        HibernateConfigurer configurer = new HibernateConfigurer() {
            @Override
            public void configure(org.hibernate.cfg.Configuration hbConfiguration) {
                hbConfiguration.setInterceptor(new EntityServiceInjectionInterceptor(currencyService));
            }
        };
        configuration.add("CurrencyService Interceptor", configurer);
    }

    public static void bind(ServiceBinder binder) {
        binder.bind(CurrentUser.class);
        binder.bind(PictureService.class);
        binder.bind(FileService.class);
        binder.bind(WishBox.class);
        binder.bind(Museum.class);
        binder.bind(ReferenceService.class);
        binder.bind(Market.class);
        binder.bind(ArticleFactory.class);
        binder.bind(Population.class);
        binder.bind(Registry.class);
        binder.bind(EmailBuilderService.class);
        binder.bind(TokenService.class);
        binder.bind(MailService.class);
        binder.bind(CurrencyService.class);
        binder.bind(Suggestionner.class);
        binder.bind(ActionLogger.class);
        binder.bind(CheckoutService.class);
        binder.bind(HibernateSearchService.class);
        binder.bind(Reviewer.class);
        binder.bind(Charts.class);
        binder.bind(Cacher.class);
        binder.bind(Barcoder.class);
        binder.bind(YoutubePlaylistManager.class);
        binder.bind(DailymotionPlaylistManager.class);
        binder.bind(SNK.class);
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

    @Contribute(SymbolSource.class)
    public static void contributeSymbolSource(OrderedConfiguration<SymbolProvider> configuration) {
        configuration.add("NGDB Properties", new ResourceSymbolProvider(new ClasspathResource("ngdb.properties")));
    }

    @Match("ContextPathEncoder")
    public static void adviseExceptionHandler(MethodAdviceReceiver receiver) throws SecurityException, NoSuchMethodException {
        MethodAdvice advice = new MethodAdvice() {
            private final String[] toReplace = {"Â", ":", ",", "&", " "," ", "!", "\\(", "\\)", "'", "~"};
            public void advise(Invocation invocation) {
                try {
                    String param = (String) invocation.getParameter(0);
                    if(param != null) {
                        for(String r:toReplace) {
                            param = param.replaceAll(r, "-");
                        }
                        invocation.override(0, param);
                    }
                    invocation.proceed();
                } catch (IllegalArgumentException e) {
                }
            }
        };
        Method method = ContextPathEncoder.class.getMethod("decodePath", String.class);
        receiver.adviseMethod(method, advice);
    }

    public RequestFilter buildNavigationFilter() {
        return new RequestFilter() {
            public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
                Session session = request.getSession(false);
                if (session != null) {
                    String url = request.getPath();
                    session.setAttribute("derniere-page-visitee", url);
                }
                return handler.service(request, response);
            }
        };
    }

}
