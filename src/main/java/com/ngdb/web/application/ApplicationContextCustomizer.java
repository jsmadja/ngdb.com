package com.ngdb.web.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.google.common.io.Closeables;

public class ApplicationContextCustomizer implements org.apache.tapestry5.spring.ApplicationContextCustomizer {

	private static Properties FINAL_PROPERTIES;
	private static final String INTERNAL_PROPERTY_FILE = "/ngdb.properties";
	private static final String CONFIG_PROPERTY_NAME = "ngdn.config.file";

	public static Properties getFinalPropertiesCopy() {
		return new Properties(FINAL_PROPERTIES);
	}

	public ApplicationContextCustomizer() {
		Properties internal = new Properties();
		Properties external = new Properties(internal);
		processInternalConf(internal);
		processExternalConf(external);
		dumpProperties(external);
		FINAL_PROPERTIES = external;
	}

	private static void processInternalConf(Properties properties) {
		InputStream internalConf = null;
		try {
			internalConf = ApplicationContextCustomizer.class.getResourceAsStream(INTERNAL_PROPERTY_FILE);
			if (internalConf == null) {
				throw new Error("internal configuration file not found : " + INTERNAL_PROPERTY_FILE);
			}
			properties.load(internalConf);
		} catch (Exception e) {
			throw new Error("Internal configuration file cannot be loaded for " + INTERNAL_PROPERTY_FILE, e);
		} finally {
			Closeables.closeQuietly(internalConf);
		}
	}

	private static void processExternalConf(Properties properties) {
		String property = System.getProperty(CONFIG_PROPERTY_NAME);
		if (property == null) {
			System.out.println("[WARNING] Property for configuration file not found : -D" + CONFIG_PROPERTY_NAME);
			return;
		}
		File file = new File(property);
		if (!file.exists()) {
			System.out.println("[WARNING] Configuration file does not exists : -D" + CONFIG_PROPERTY_NAME + "=" + property);
			return;
		}
		System.out.println("External configuration file found for application : " + property);

		FileInputStream externalConf = null;
		try {
			externalConf = new FileInputStream(file);
			properties.load(externalConf);
		} catch (IOException e) {
			throw new Error("External configuration file cannot be loaded from " + CONFIG_PROPERTY_NAME, e);
		} finally {
			Closeables.closeQuietly(externalConf);
		}
	}

	private static void dumpProperties(Properties properties) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n#############################################################");
		builder.append("\n");
		builder.append("## Final configuration ");
		builder.append(" application\n");
		builder.append("#############################################################");
		builder.append("\n");
		Set<String> sortedPropertyNames = new TreeSet<String>(properties.stringPropertyNames());
		for (String name : sortedPropertyNames) {
			if (name.endsWith(".secret")) {
				builder.append("## " + name + " : **********");
			} else {
				builder.append("## " + name + " : " + properties.getProperty(name));
			}
			builder.append("\n");
		}
		builder.append("#############################################################");
		builder.append("\n");
		System.out.println(builder.toString());
	}

	@Override
	public void customizeApplicationContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext) {
		applicationContext.setConfigLocation("/WEB-INF/applicationContext.xml");
	}
}
