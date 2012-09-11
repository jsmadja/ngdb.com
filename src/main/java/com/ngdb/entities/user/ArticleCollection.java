package com.ngdb.entities.user;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromCollectionObjectToArticle;
import static com.ngdb.Predicates.isAccessory;
import static com.ngdb.Predicates.isGame;
import static com.ngdb.Predicates.isHardware;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.ngdb.Predicates;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.article.Article;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleCollection {

    @OneToMany(mappedBy = "owner", fetch = LAZY, orphanRemoval = true)
    private Set<CollectionObject> collection;

    public void addInCollection(CollectionObject collectionObject) {
        collection.add(collectionObject);
    }

    public void removeFromCollection(Article article) {
        CollectionObject collectionObjectToRemove = null;
        for (CollectionObject collectionObject : collection) {
            if (collectionObject.getArticle().getId().equals(article.getId())) {
                collectionObjectToRemove = collectionObject;
                break;
            }
        }
        collection.remove(collectionObjectToRemove);
    }

    public Collection<Article> getArticles() {
        return transform(collection, fromCollectionObjectToArticle);
    }

    public Collection<Article> getGames() {
        return filter(getArticles(), isGame);
    }

    public Collection<Article> getHardwares() {
        return filter(getArticles(), isHardware);
    }

    public Collection<Article> getAccessories() {
        return filter(getArticles(), isAccessory);
    }

    public int getNumArticles() {
        return collection.size();
    }

    public long getNumHardwares() {
        return getHardwares().size();
    }

    public long getNumGames() {
        return getGames().size();
    }

    public long getNumAccessories() {
        return getAccessories().size();
    }
}
