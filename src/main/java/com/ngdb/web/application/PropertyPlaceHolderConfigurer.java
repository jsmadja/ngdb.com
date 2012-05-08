package com.ngdb.web.application;

import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;

public class PropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties p = new Properties();
        CollectionUtils.mergePropertiesIntoMap(ApplicationContextCustomizer.getFinalPropertiesCopy(), p);
        processProperties(beanFactory, p);
    }

}
