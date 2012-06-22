package com.ngdb.entities.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.shiro.authz.Permission;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Embedded
	private WishList wishList;

	@Embedded
	private ArticleCollection collection;

	@Embedded
	private Shop shop;

	@Embedded
	private Set<Profile> profiles;

	@OneToMany(mappedBy = "user")
	private Set<Token> tokens;

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

	public Set<Wish> getWishes() {
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

	public Set<Permission> getPermissions() {
		Set<Permission> permissions = new HashSet<Permission>();
		for (Profile profile : profiles) {
			permissions.addAll(profile.getPermissions());
		}
		return permissions;
	}

	public Wish addToWishes(Article article) {
		Wish wish = new Wish(this, article);
		wishList.addInWishList(wish);
		return wish;
	}

	public Collection<ShopItem> getShopItemsForSale() {
		return shop.getShopItemsToSell();
	}

	public boolean canAddInCollection(Article article) {
		if (collection == null) {
			return true;
		}
		return !collection.contains(article);
	}

	public boolean canWish(Article article) {
		if (wishList == null) {
			return true;
		}
		return !wishList.contains(article);
	}

	public CollectionObject addInCollection(Article article) {
		CollectionObject collectionObject = new CollectionObject(this, article);
		collection.addInCollection(collectionObject);
		return collectionObject;
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

}
