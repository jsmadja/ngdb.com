package com.ngdb.web.model;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

public class CustomCurrenciesList extends ModelList {

    private SelectModelImpl selectModel;

    public CustomCurrenciesList(Collection<String> currencies) {
        List<OptionModel> options = CollectionFactory.newList();
        for (String currency : currencies) {
            options.add(new OptionModelImpl(currency, currency));
        }
        selectModel = new SelectModelImpl(null, options);
    }

    @Override
    protected SelectModel getSelectModel() {
        return selectModel;
    }

}
