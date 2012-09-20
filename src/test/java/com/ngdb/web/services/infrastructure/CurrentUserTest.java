package com.ngdb.web.services.infrastructure;

import org.apache.tapestry5.services.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tynamo.security.services.SecurityService;

import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentUserTest {

    @InjectMocks
    private CurrentUser currentUser;

    @Mock
    private Request request;

    @Mock
    private SecurityService securityService;

    @Test
    public void should_return_EUR_if_locale_is_fr_fr() {
        Locale locale = new Locale("fr","fr");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("EUR");
    }

    @Test
    public void should_return_GBP_if_locale_is_en_gb() {
        Locale locale = new Locale("en","gb");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("GBP");
    }

    @Test
    public void should_return_USD_if_locale_is_en_us() {
        Locale locale = new Locale("en","us");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("USD");
    }

    @Test
    public void should_return_EUR_if_locale_is_en_fr() {
        Locale locale = new Locale("en","fr");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("EUR");
    }

    @Test
    public void should_return_EUR_if_locale_is_fr() {
        Locale locale = new Locale("fr");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("EUR");
    }

    @Test
    public void should_return_CAD_if_locale_is_canada() {
        Locale locale = Locale.CANADA_FRENCH;
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("CAD");
    }

    @Test
    public void should_return_EUR_if_locale_is_german() {
        Locale locale = Locale.GERMAN;
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("EUR");
    }

    @Test
    public void should_return_EUR_if_locale_is_germany() {
        Locale locale = Locale.GERMANY;
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("EUR");
    }

    @Test
    public void should_return_USD_if_locale_is_invalid() {
        when(securityService.isAuthenticated()).thenReturn(false);

        Locale locale = new Locale("fesjfkeslkfjes");
        when(request.getLocale()).thenReturn(locale);
        assertThat(currentUser.getPreferedCurrency()).isEqualTo("USD");
    }
}
