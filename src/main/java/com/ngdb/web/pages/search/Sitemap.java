package com.ngdb.web.pages.search;

import com.ngdb.entities.article.Article;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.TextStreamResponse;
import org.hibernate.Session;

import java.util.List;

public class Sitemap {

    @Inject
    private Response response;

    @Inject
    private Session session;

    @Inject
    @Symbol("host.url")
    private String host;

    Object onActivate() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset\n" +
                "      xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n" +
                "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "      xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n" +
                "            http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">\n");
        List<Article> articles = session.createCriteria(Article.class).list();
        for (Article article : articles) {
            String url = host+article.getViewPage()+"/"+article.getId()+"/"+article.getSuffix();
            url = url.replaceAll("gameView", "view");
            url = url.replaceAll("hardwareView", "view");
            url = url.replaceAll("accessoryView", "view");
            String s = "<url>\n" +
                    "  <loc>"+url+"</loc>\n" +
                    "  <changefreq>daily</changefreq>\n" +
                    "</url>";
            sb.append(s);
        }
        sb.append("</urlset>");

        return ok(sb.toString());
    }

    private TextStreamResponse ok(String content) {
        TextStreamResponse stream = new TextStreamResponse("application/xml", content);
        response.setStatus(200);
        stream.prepareResponse(response);
        return stream;
    }

}

