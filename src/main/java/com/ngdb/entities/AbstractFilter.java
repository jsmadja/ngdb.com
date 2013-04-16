package com.ngdb.entities;

import com.google.common.base.Joiner;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.services.Request;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilter {

    protected boolean filteredByGames;
    protected boolean filteredByAccessories;
    protected boolean filteredByHardwares;

    protected Publisher filteredPublisher;
    protected Origin filteredOrigin;
    protected Platform filteredPlatform;
    protected User filteredUser;
    protected Article filteredArticle;

    public void clear() {
        this.filteredOrigin = null;
        this.filteredPlatform = null;
        this.filteredUser = null;
        this.filteredArticle = null;
        this.filteredPublisher = null;
        this.filteredByGames = true;
        this.filteredByHardwares = false;
        this.filteredByAccessories = false;
    }

    public void initializeWithUrlParameters(Request request, ReferenceService referenceService, Population population) {
        String originTitle = request.getParameter("origin");
        String platformName = request.getParameter("platform");
        String type = request.getParameter("type");
        String publisherName = request.getParameter("publisher");
        String userName = request.getParameter("user");

        if(originTitle != null || platformName != null || type != null || publisherName != null) {
            clear();
        }

        if(originTitle != null) {
            Origin origin = referenceService.findOriginByTitle(originTitle);
            if(origin != null) {
                filterByOrigin(origin);
            }
        }
        if(platformName != null) {
            Platform platform = referenceService.findPlatformByName(platformName);
            if(platform != null) {
                filterByPlatform(platform);
            }
        }
        if(publisherName != null) {
            Publisher publisher = referenceService.findPublisherByName(publisherName);
            if(publisher != null) {
                filterByPublisher(publisher);
            }
        }
        if(userName != null) {
            User user = population.findByLogin(userName);
            if(user != null) {
                filterByUser(user);
            }
        }
        if(type != null) {
            if("hardwares".equals(type)) {
                filterByHardwares();
            } else if ("accessories".equals(type)) {
                filterByAccessories();
            } else {
                filterByGames();
            }
        }
    }

    public String getQueryLabel() {
        String queryLabel = "all ";
        if (filteredByGames) {
            queryLabel += orange("games");
        } else if(filteredByHardwares){
            queryLabel += orange("hardwares");
        } else {
            queryLabel += orange("accessories");
        }
        if (filteredOrigin != null) {
            queryLabel += " from " + orange(filteredOrigin.getTitle());
        }
        if (filteredPlatform != null) {
            queryLabel += " on " + orange(filteredPlatform.getName());
        }
        if (filteredUser != null) {
            queryLabel += " with user " + orange(filteredUser.getLogin());
        }
        if (filteredArticle != null) {
            queryLabel += " of article " + orange(filteredArticle.getTitle());
        }
        return queryLabel;
    }

    protected String orange(String name) {
        return "<span class=\"orange\">" + name + "</span>";
    }

    public void filterByUser(User user) {
        this.filteredUser = user;
    }

    public void filterByOrigin(Origin origin) {
        this.filteredOrigin = origin;
    }

    public void filterByPlatform(Platform platform) {
        this.filteredPlatform = platform;
    }

    public void filterByArticle(Article article) {
        this.filteredArticle = article;
        if(article.isHardware()) {
            filterByHardwares();
        } else if (article.isAccessory()) {
            filterByAccessories();
        } else {
            filterByGames();
        }
    }

    public void filterByPublisher(Publisher publisher) {
        this.filteredPublisher = publisher;
    }

    public void filterByGames() {
        this.filteredByGames = true;

        this.filteredByHardwares = false;
        this.filteredByAccessories = false;
    }

    public void filterByHardwares() {
        this.filteredByHardwares = true;

        this.filteredByGames = false;
        this.filteredByAccessories = false;
    }

    public void filterByAccessories() {
        this.filteredByAccessories = true;

        this.filteredByGames = false;
        this.filteredByHardwares = false;
    }

    public boolean isFilteredBy(Platform platform) {
        if (filteredPlatform == null) {
            return false;
        }
        return platform.getId().equals(filteredPlatform.getId());
    }

    public boolean isFilteredBy(Origin origin) {
        if (filteredOrigin == null) {
            return false;
        }
        return origin.equals(filteredOrigin);
    }

    public boolean isFilteredBy(Publisher publisher) {
        if (filteredPublisher == null) {
            return false;
        }
        return publisher.equals(filteredPublisher);
    }

    public User getFilteredUser() {
        return filteredUser;
    }

    public boolean isFilteredByGames() {
        return filteredByGames;
    }

    public boolean isFilteredByHardwares() {
        return filteredByHardwares;
    }

    public boolean isFilteredByAccessories() {
        return filteredByAccessories;
    }

    public String getFilterUrl() {
        List<String> parameters = new ArrayList<String>();
        if(filteredOrigin != null) {
            parameters.add("origin="+filteredOrigin.getTitle());
        }
        if(filteredPlatform != null) {
            parameters.add("platform="+filteredPlatform.getShortName());
        }
        if(isFilteredByGames()) {
            parameters.add("type=games");
        }
        if(isFilteredByHardwares()) {
            parameters.add("type=hardwares");
        }
        if(isFilteredByAccessories()) {
            parameters.add("type=accessories");
        }
        if(filteredPublisher != null) {
            parameters.add("publisher="+filteredPublisher);
        }
        if(filteredUser != null) {
            parameters.add("user="+filteredUser.getLogin());
        }
        return "?"+ Joiner.on("&").join(parameters);
    }

    public abstract long getNumHardwares();
    public abstract long getNumGames();
    public abstract long getNumAccessories();

}
