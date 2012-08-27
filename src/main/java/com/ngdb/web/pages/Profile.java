package com.ngdb.web.pages;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrencyService;
import com.ngdb.web.services.infrastructure.CurrentUser;

@RequiresAuthentication
public class Profile {

    @Inject
    private CurrentUser currentUser;

    @Persist
    @Property
    private String email;

    @Persist
    @Property
    private String preferedCurrency;

    @Persist
    @Property
    private String country;

    @Inject
    private Session session;

    @Inject
    private CurrencyService currencyService;

    @Persist
    @Property
    private String message;

    @SetupRender
    public void init() {
        User user = currentUser.getUser();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.preferedCurrency = currentUser.getPreferedCurrency();
    }

    @CommitAfter
    public void onSuccess() {
        User user = currentUser.getUserFromDb();
        user.setPreferedCurrency(preferedCurrency);
        user.setEmail(email);
        user.setCountry(country);
        session.persist(user);
        currentUser.refresh();
        message = "Your profile has been updated";
    }

    public Collection<String> getCurrencies() {
        return currencyService.allCurrencies();
    }

    public Collection<String> getCountries() {
        return Arrays.asList(Locale.getISOCountries());
    }

}
