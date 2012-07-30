package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.FormSupport;

public class ExceptionReport implements ExceptionReporter {

    @Property
    private Throwable exception;

    @Inject
    private Environment environment;

    void setupRender() {
        if (environment.peek(FormSupport.class) != null) {
            environment.pop(FormSupport.class);
        }
    }

    public void reportException(Throwable exception) {
        this.exception = exception;
    }
}
