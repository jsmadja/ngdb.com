package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.ExceptionReporter;

public class ExceptionReport implements ExceptionReporter {

	@Property
	private Throwable exception;

	public void reportException(Throwable exception) {
		this.exception = exception;
	}
}
