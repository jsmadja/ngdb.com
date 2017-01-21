package com.ngdb.entities.user;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractEntity implements Comparable<User> {

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "preferend_currency")
    private String preferedCurrency;

    @Embedded
    private ArticleCollection collection;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;

    @Column(name = "shop_policy")
    private String shopPolicy;

    @Column(name = "about_me")
    private String aboutMe;

    private String country;

    User() {
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return login;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArticleCollection getCollection() {
        return collection;
    }

    public CollectionObject addInCollection(Article article) {
        CollectionObject collectionObject = new CollectionObject(this, article);
        collection.addInCollection(collectionObject);
        return collectionObject;
    }

    public void removeFromCollection(Article article) {
        collection.removeFromCollection(article);
    }

    @Override
    public int compareTo(User user) {
        return login.compareToIgnoreCase(user.getLogin());
    }

    public boolean isContributor() {
        return login.equalsIgnoreCase("anzymus") || login.equalsIgnoreCase("takou");
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getPreferedCurrency() {
        return preferedCurrency;
    }

    public void setPreferedCurrency(String preferedCurrency) {
        this.preferedCurrency = preferedCurrency;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopPolicy() {
        return shopPolicy;
    }

    public void setShopPolicy(String shopPolicy) {
        this.shopPolicy = shopPolicy;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
