package com.ngdb.web.components.common.layout;

import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Footer {

    @Inject
    private CurrentUser currentUser;

    public boolean isContributor() {
        return currentUser.isContributor();
    }

}
