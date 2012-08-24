package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.Population;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Contact {

    @Property
    @Validate("required")
    private String title;

    @Property
    @Validate("required")
    private String comment;

    @Property
    @Validate("required")
    private String from;

    @Persist
    @Property
    private String message;

    @Persist
    @Property
    private Long id;

    @Inject
    private MailService mailService;

    @Inject
    private Population population;

    @Inject
    private CurrentUser currentUser;

    private static final Logger LOG = LoggerFactory.getLogger(Contact.class);

    public void onActivate() {

    }

    public boolean onActivate(String title) {
        this.title = title;
        return true;
    }

    public boolean onActivate(String title, Long id) {
        this.title = title;
        this.id = id;
        return true;
    }

    public Object onSuccess() {
        String body = "[CONTACT] " + comment + " - ";
        if (id != null) {
            body += " article #" + id + ", ";
        }
        if (currentUser.isAnonymous()) {
            body += " from " + from;
        } else {
            body += " from " + currentUser.getUsername() + " (" + currentUser.getUser().getEmail() + ")";
        }
        LOG.info("Sending feedback : \n" + body);
        mailService.sendMail("anzymus@neogeodb.com", body, title);
        message = "Your comment has been successfully sent";
        return this;
    }

    public boolean isAnonymous() {
        return currentUser.isAnonymous();
    }

}
