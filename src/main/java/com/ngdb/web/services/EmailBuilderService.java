package com.ngdb.web.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class EmailBuilderService {

	@Inject
	private VelocityEngine velocityEngine;

	String build(String velocityTemplate, Map<String, String> values) {
		try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, buildTemplateName(velocityTemplate), values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	String subject(String velocityTemplate) {
		try {
			String pathSubjects = buildSubjectFile();
			String key = buildKeySubject(velocityTemplate);
			Properties properties = new Properties();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathSubjects);
			properties.load(is);
			return properties.getProperty(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String buildSubjectFile() {
		return String.format("com/ngdb/emailtemplate/subjects.properties");
	}

	private String buildKeySubject(String template) {
		return String.format("mail.subject.%s", template);
	}

	private String buildTemplateName(String template) {
		return String.format("com/ngdb/emailtemplate/%s.vm", template);
	}

}
