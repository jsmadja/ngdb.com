package com.ngdb.entities.user;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromCollectionObjectToArticle;
import static com.ngdb.Predicates.isGame;
import static com.ngdb.Predicates.isHardware;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.article.Article;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleCollection implements Serializable {

    @OneToMany(mappedBy = "owner", fetch = LAZY, orphanRemoval = true)
    private Set<CollectionObject> collection;

    public boolean contains(Article article) {
        for (CollectionObject collectionObject : collection) {
            Long searchId = article.getId();
            Long idInCollection = collectionObject.getArticle().getId();
            if (searchId.equals(idInCollection)) {
                return true;
            }
        }
        return false;
    }

    public void addInCollection(CollectionObject collectionObject) {
        collection.add(collectionObject);
    }

    public void removeFromCollection(Article article) {
        if (contains(article)) {
            CollectionObject collectionObjectToRemove = null;
            for (CollectionObject collectionObject : collection) {
                if (collectionObject.getArticle().getId().equals(article.getId())) {
                    collectionObjectToRemove = collectionObject;
                    break;
                }
            }
            collection.remove(collectionObjectToRemove);
        }
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

    public int getNumArticles() {
        return collection.size();
    }

}
