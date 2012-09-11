package com.ngdb.entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.Predicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.collect.Collections2.filter;

public class MuseumFilter extends AbstractFilter {

    private GameFactory gameFactory;
    private HardwareFactory hardwareFactory;
    private AccessoryFactory accessoryFactory;

    private Tag filteredTag;

    private String filteredNgh;

    private Date filteredReleaseDate;

    private Collection<Article> initialArticleList;

    public MuseumFilter(GameFactory gameFactory, HardwareFactory hardwareFactory, AccessoryFactory accessoryFactory) {
        this.gameFactory = gameFactory;
        this.hardwareFactory = hardwareFactory;
        this.accessoryFactory = accessoryFactory;
        clear();
    }

    public void clear() {
        super.clear();
        this.filteredNgh = null;
        this.filteredReleaseDate = null;
        this.filteredTag = null;
    }

    public String getQueryLabel() {
        String queryLabel = super.getQueryLabel();
        if (filteredPublisher != null) {
            queryLabel += " published by " + orange(filteredPublisher.getName());
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
        return queryLabel;
    }

    @Override
    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getCollection().getNumHardwares();
        }
        return hardwareFactory.getNumHardwares();
    }

    @Override
    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getCollection().getNumGames();
        }
        return gameFactory.getNumGames();
    }

    @Override
    public long getNumAccessories() {
        if (filteredUser != null) {
            return filteredUser.getCollection().getNumAccessories();
        }
        return accessoryFactory.getNumAccessories();
    }

    private Collection<Article> applyFilters(List<Predicate<Article>> filters) {
        Collection<Article> filteredArticles = allArticles();
        buildFilters(filters);
        for (Predicate<Article> filter : filters) {
            filteredArticles = filter(filteredArticles, filter);
        }
        return filteredArticles;
    }

    private Collection<Article> allArticles() {
        Collection<Article> articles = new ArrayList<Article>();
        if (filteredByGames) {
            if (filteredUser == null) {
                articles.addAll(gameFactory.findAllLight());
            } else {
                articles.addAll(gameFactory.findAllOwnedBy(filteredUser));
            }
        } else if(filteredByHardwares){
            if (filteredUser == null) {
                articles.addAll(hardwareFactory.findAll());
            } else {
                articles.addAll(hardwareFactory.findAllOwnedBy(filteredUser));
            }
        } else {
            if (filteredUser == null) {
                articles.addAll(accessoryFactory.findAll());
            } else {
                articles.addAll(accessoryFactory.findAllOwnedBy(filteredUser));
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

    public int getNumArticlesInThisOrigin(Origin origin) {
        Collection<Article> articles = allArticles();
        if (filteredPlatform != null) {
            Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(origin);
        articles = filter(articles, filterByOrigin);
        return articles.size();
    }

    public int getNumArticlesInThisPublisher(Publisher publisher) {
        Collection<Article> articles = allArticles();
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
        Collection<Article> articles = allArticles();
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

    public Tag getFilteredTag() {
        return filteredTag;
    }

    public Date getFilteredReleaseDate() {
        return filteredReleaseDate;
    }

}
