package com.ngdb.entities.shop;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.user.User;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PotentialBuyers implements Serializable {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PotentialBuyers", inverseJoinColumns = { @JoinColumn(name = "user_id") }, joinColumns = { @JoinColumn(name = "shop_item_id") })
	private Set<User> potentialBuyers;

	void add(User potentialBuyer) {
		potentialBuyers.add(potentialBuyer);
	}

    public void remove(User potentialBuyer) {
        potentialBuyers.remove(potentialBuyer);
    }

	boolean contains(User user) {
		return potentialBuyers.contains(user);
	}

	public Set<User> all() {
		return Collections.unmodifiableSet(potentialBuyers);
	}

}
