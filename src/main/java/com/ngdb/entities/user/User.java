package com.ngdb.entities.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.PotentialBuys;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;

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
    private WishList wishList;

    @Embedded
    private ArticleCollection collection;

    @Embedded
    private Shop shop;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;

    @Embedded
    private PotentialBuys potentialBuys;

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
    public boolean equals(Object o) {
        if (o instanceof User) {
            String login2 = ((User) o).login;
            return login.equalsIgnoreCase(login2);
        }
        return false;
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

    public boolean canSell(Article article) {
        if (collection == null) {
            return false;
        }
        return collection.contains(article);
    }

    public Collection<Wish> getWishes() {
        return wishList.getWishes();
    }

    public Set<ShopItem> getShopItems() {
        return shop.getShopItems();
    }

    public Collection<Article> getArticlesInCollection() {
        return collection.getArticles();
    }

    public ArticleCollection getCollection() {
        return collection;
    }

    public Shop getShop() {
        return shop;
    }

    public Wish addToWishes(Article article) {
        Wish wish = new Wish(this, article);
        wishList.addInWishList(wish);
        return wish;
    }

    public void removeFromWishes(Article article) {
        wishList.removeFromWishList(article);
    }

    public List<ShopItem> getShopItemsForSale() {
        List<ShopItem> shopItemsToSell = new ArrayList<ShopItem>(shop.getShopItemsToSell());
        Collections.sort(shopItemsToSell, new Comparator<ShopItem>() {
            @Override
            public int compare(ShopItem shopItem1, ShopItem shopItem2) {
                return shopItem1.getArticle().getTitle().compareToIgnoreCase(shopItem2.getArticle().getTitle());
            }
        });
        return shopItemsToSell;
    }

    public boolean canAddInCollection(Article article) {
        if (collection == null) {
            return true;
        }
        return !collection.contains(article);
    }

    public boolean canRemoveFromCollection(Article article) {
        if (collection == null) {
            return false;
        }
        return collection.contains(article);
    }

    public boolean canWish(Article article) {
        if (wishList == null) {
            return true;
        }
        return !wishList.contains(article);
    }

    public boolean canUnwish(Article article) {
        if (wishList == null) {
            return false;
        }
        return wishList.contains(article);
    }

    public CollectionObject addInCollection(Article article) {
        CollectionObject collectionObject = new CollectionObject(this, article);
        collection.addInCollection(collectionObject);
        return collectionObject;
    }

    public void removeFromCollection(Article article) {
        collection.removeFromCollection(article);
    }

    public Collection<Article> getGamesInCollection() {
        return collection.getGames();
    }

    public Collection<Article> getHardwaresInCollection() {
        return collection.getHardwares();
    }

    public int getNumArticlesInCollection() {
        return collection.getNumArticles();
    }

    public int getNumArticlesInWishList() {
        return wishList.getNumWishes();
    }

    public int getNumArticlesInShop() {
        return shop.getNumArticlesToSell();
    }

    public boolean canMarkAsSold(ShopItem shopItem) {
        return shop.contains(shopItem);
    }

    public boolean canRemove(ShopItem shopItem) {
        return canMarkAsSold(shopItem);
    }

    public boolean owns(Article article) {
        return collection.contains(article);
    }

    @Override
    public int compareTo(User user) {
        return login.compareToIgnoreCase(user.getLogin());
    }

    public Set<ShopItem> getPotentialBuys() {
        return potentialBuys.all();
    }

    public long getNumHardwaresForSale() {
        return shop.getNumHardwaresForSale();
    }

    public long getNumGamesForSale() {
        return shop.getNumGamesForSale();
    }

    public int getNumArticleForSale() {
        return shop.getNumArticlesToSell();
    }

    public Collection<ShopItem> getAllGamesForSale() {
        return shop.getAllGamesForSale();
    }

    public Collection<ShopItem> getAllHardwaresForSale() {
        return shop.getAllHardwaresForSale();
    }

    public boolean isContributor() {
        return login.equalsIgnoreCase("anzymus") || login.equalsIgnoreCase("takou");
    }

    public Collection<Wish> getAllWishedGames() {
        return wishList.getAllGames();
    }

    public Collection<Wish> getAllWishedHardwares() {
        return wishList.getAllHardwares();
    }

    public int getNumWishedHardwares() {
        return wishList.getNumWishedHardwares();
    }

    public int getNumWishedGames() {
        return wishList.getNumWishedGames();
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
