package com.ngdb.entities.user;

import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromCollectionObjectToArticle;
import static com.ngdb.Predicates.*;
import static javax.persistence.FetchType.LAZY;

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

    public int getNumArticles() {
        return collection.size();
    }

}
