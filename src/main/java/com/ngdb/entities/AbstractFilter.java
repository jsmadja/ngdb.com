package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.user.User;

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
            queryLabel += " owned by " + orange(filteredUser.getLogin());
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

    public abstract long getNumHardwares();
    public abstract long getNumGames();
    public abstract long getNumAccessories();

}
