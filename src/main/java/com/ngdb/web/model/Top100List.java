package com.ngdb.web.model;

import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.Collection;
import java.util.List;

public class Top100List extends ModelList {

    private SelectModelImpl selectModel;

    public Top100List(Collection<String> tops) {
        List<OptionModel> options = CollectionFactory.newList();
        for (String top : tops) {
            options.add(new OptionModelImpl(top, top));
        }
        selectModel = new SelectModelImpl(null, options);
    }

    @Override
    protected SelectModel getSelectModel() {
        return selectModel;
    }

}
