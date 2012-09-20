package com.ngdb.entities.article;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.user.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.ocpsoft.pretty.time.PrettyTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Locale;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleAction extends AbstractEntity implements Comparable<ArticleAction> {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String message;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastUpdateDate() {
        return new PrettyTime(Locale.UK).format(getCreationDate());
    }

    @Override
    public int compareTo(ArticleAction articleAction) {
        return articleAction.getCreationDate().compareTo(getCreationDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleAction that = (ArticleAction) o;

        if (article != null ? !article.equals(that.article) : that.article != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = article != null ? article.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
