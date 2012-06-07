package com.ngdb.entities.shop;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.ngdb.entities.user.User;

@Embeddable
public class PotentialBuyers {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PotentialBuyers", inverseJoinColumns = { @JoinColumn(name = "user_id") }, joinColumns = { @JoinColumn(name = "shop_item_id") })
	private Set<User> potentialBuyers;

	void add(User potentialBuyer) {
		potentialBuyers.add(potentialBuyer);
	}

	boolean contains(User user) {
		return potentialBuyers.contains(user);
	}

}
