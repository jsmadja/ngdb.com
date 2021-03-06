package com.ngdb.web.pages.search;

import com.ngdb.services.HibernateSearchService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.TextStreamResponse;

public class SearchIndex {

    @Inject
    private Response response;

    @Inject
    private HibernateSearchService hibernateSearchService;

    Object onActivate() {
        hibernateSearchService.launchFullIndexation();
        return ok();
    }

    private TextStreamResponse ok() {
        TextStreamResponse stream = new TextStreamResponse("text/plain", "ok");
        response.setStatus(200);
        stream.prepareResponse(response);
        return stream;
    }
}