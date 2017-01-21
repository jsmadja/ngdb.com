package com.ngdb.web.services;

import com.ngdb.web.services.infrastructure.CurrencyService;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class EntityServiceInjectionInterceptor extends EmptyInterceptor {

    private CurrencyService currencyService;

    public EntityServiceInjectionInterceptor(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return false;
    }

}
