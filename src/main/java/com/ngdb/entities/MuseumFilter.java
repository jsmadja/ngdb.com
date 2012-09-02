package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.Predicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.user.User;

public class MuseumFilter {

    private boolean filteredByGames;

    private Publisher filteredPublisher;

    private Origin filteredOrigin;

    private Platform filteredPlatform;

    private User filteredUser;

    private GameFactory gameFactory;

    private HardwareFactory hardwareFactory;

    private Tag filteredTag;

    private String filteredNgh;

    private Date filteredReleaseDate;

    private Collection<Article> initialArticleList;

    public MuseumFilter(GameFactory gameFactory, HardwareFactory hardwareFactory) {
        this.gameFactory = gameFactory;
        this.hardwareFactory = hardwareFactory;
        clear();
    }

    public void clear() {
        this.filteredOrigin = null;
        this.filteredPlatform = null;
        this.filteredPublisher = null;
        this.filteredNgh = null;
        this.filteredReleaseDate = null;
        this.filteredTag = null;
        this.filteredByGames = true;
        this.filteredUser = null;
    }

    public String getQueryLabel() {
        String queryLabel = "all ";
        if (filteredByGames) {
            queryLabel += orange("games");
        } else {
            queryLabel += orange("hardwares");
        }
        if (filteredOrigin != null) {
            queryLabel += " from " + orange(filteredOrigin.getTitle());
        }
        if (filteredPublisher != null) {
            queryLabel += " published by " + orange(filteredPublisher.getName());
        }
        if (filteredPlatform != null) {
            queryLabel += " on " + orange(filteredPlatform.getName());
        }
        if (filteredNgh != null) {
            queryLabel += " with ngh " + orange(filteredNgh);
        }
        if (filteredReleaseDate != null) {
            queryLabel += " released at " + orange(new SimpleDateFormat("MM/dd/yyyy").format(filteredReleaseDate));
        }
        if (filteredTag != null) {
            queryLabel += " with tag " + orange(filteredTag.getName());
        }
        if (filteredUser != null) {
            queryLabel += " owned by " + orange(filteredUser.getLogin());
        }
        return queryLabel;
    }

    private String orange(String name) {
        return "<span class=\"orange\">" + name + "</span>";
    }

    public void filterByUser(User user) {
        this.filteredUser = user;
    }

    public void filterByOrigin(Origin origin) {
        this.filteredOrigin = origin;
    }

    public void filterByPublisher(Publisher publisher) {
        this.filteredPublisher = publisher;
    }

    public void filterByPlatform(Platform platform) {
        this.filteredPlatform = platform;
    }

    private Collection<Article> applyFilters(List<Predicate<Article>> filters) {
        Collection<Article> filteredArticles = getInitialArticleList();
        buildFilters(filters);
        for (Predicate<Article> filter : filters) {
            filteredArticles = filter(filteredArticles, filter);
        }
        return filteredArticles;
    }

    private Collection<Article> getInitialArticleList() {
        if(initialArticleList == null) {
            initialArticleList = buildInitialArticleList();
        }
        return initialArticleList;
    }

    private Collection<Article> buildInitialArticleList() {
        Collection<Article> articles = new ArrayList<Article>();
        if (filteredByGames) {
            if (filteredUser == null) {
                articles.addAll(gameFactory.findAll());
            } else {
                articles.addAll(gameFactory.findAllOwnedBy(filteredUser));
            }
        } else {
            if (filteredUser == null) {
                articles.addAll(hardwareFactory.findAll());
            } else {
                articles.addAll(hardwareFactory.findAllOwnedBy(filteredUser));
            }
        }
        return articles;
    }

    private void buildFilters(List<Predicate<Article>> filters) {
        if (filteredOrigin != null) {
            filters.add(new Predicates.OriginPredicate(filteredOrigin));
        }
        if (filteredPlatform != null) {
            filters.add(new Predicates.PlatformPredicate(filteredPlatform));
        }
        if (filteredPublisher != null) {
            filters.add(new Predicates.PublisherPredicate(filteredPublisher));
        }
        if (filteredTag != null) {
            filters.add(new Predicates.TagPredicate(filteredTag));
        }
        if (filteredNgh != null) {
            filters.add(new Predicates.NghPredicate(filteredNgh));
        }
        if (filteredReleaseDate != null) {
            filters.add(new Predicates.ReleaseDatePredicate(filteredReleaseDate));
        }
    }

    public List<Article> getArticles() {
        List<Predicate<Article>> filters = Lists.newArrayList();
        Collection<Article> filteredArticles = applyFilters(filters);
        List<Article> arrayList = new ArrayList<Article>(filteredArticles);
        Collections.sort(arrayList);
        return arrayList;
    }

    public void filterByGames() {
        this.initialArticleList = null;
        this.filteredByGames = true;
    }

    public void filterByHardwares() {
        this.initialArticleList = null;
        this.filteredByGames = false;
    }

    public boolean isFilteredBy(Publisher publisher) {
        if (filteredPublisher == null) {
            return false;
        }
        return publisher.getId().equals(filteredPublisher.getId());
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

    public int getNumArticlesInThisOrigin(Origin origin) {
        Collection<Article> articles = getInitialArticleList();
        if (filteredPlatform != null) {
            Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(origin);
        articles = filter(articles, filterByOrigin);
        return articles.size();
    }

    public int getNumArticlesInThisPublisher(Publisher publisher) {
        Collection<Article> articles = getInitialArticleList();
        if (filteredPlatform != null) {
            Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        if (filteredOrigin != null) {
            Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(filteredOrigin);
            articles = filter(articles, filterByOrigin);
        }
        Predicates.PublisherPredicate filterByPublisher = new Predicates.PublisherPredicate(publisher);
        articles = filter(articles, filterByPublisher);
        return articles.size();
    }

    public int getNumArticlesInThisPlatform(Platform platform) {
        Collection<Article> articles = getInitialArticleList();
        articles = filter(articles, new Predicates.PlatformPredicate(platform));
        return articles.size();
    }

    public void filterByTag(Tag tag) {
        this.filteredTag = tag;
    }

    public void filterByNgh(String ngh) {
        this.filteredNgh = ngh;
    }

    public void filterByReleaseDate(Date releaseDate) {
        this.filteredReleaseDate = releaseDate;
    }

    public User getFilteredUser() {
        return filteredUser;
    }

    public boolean isFilteredByGames() {
        return filteredByGames;
    }

    public Tag getFilteredTag() {
        return filteredTag;
    }

    public Date getFilteredReleaseDate() {
        return filteredReleaseDate;
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getHardwaresInCollection().size();
        }
        return hardwareFactory.getNumHardwares();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getGamesInCollection().size();
        }
        return gameFactory.getNumGames();
    }

}
