package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.ngdb.WishPredicates;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;

public class WishBoxFilter extends AbstractFilter {

    private WishBox wishBox;

    public WishBoxFilter(WishBox wishBox) {
        this.wishBox = wishBox;
        clear();
    }

    private Collection<Wish> applyFilters(List<Predicate<Wish>> filters) {
        Collection<Wish> filteredWishes = allWishes();
        buildFilters(filters);
        for (Predicate<Wish> filter : filters) {
            filteredWishes = filter(filteredWishes, filter);
        }
        return filteredWishes;
    }

    private void buildFilters(List<Predicate<Wish>> filters) {
        if (filteredOrigin != null) {
            filters.add(new WishPredicates.OriginPredicate(filteredOrigin));
        }
        if (filteredPlatform != null) {
            filters.add(new WishPredicates.PlatformPredicate(filteredPlatform));
        }
    }

    public List<Wish> getWishes() {
        List<Predicate<Wish>> filters = Lists.newArrayList();
        Collection<Wish> filteredArticle = applyFilters(filters);
        List<Wish> wishes = new ArrayList<Wish>(filteredArticle);
        Collections.sort(wishes);
        return wishes;
    }

    public int getNumWishesInThisOrigin(Origin origin) {
        Collection<Wish> wishes = new ArrayList<Wish>();
        if (filteredByGames) {
            if (filteredUser == null) {
                wishes.addAll(wishBox.findAllGamesFrom(origin));
            } else {
                wishes.addAll(Collections2.filter(filteredUser.getAllWishedGames(), new WishPredicates.OriginPredicate(origin)));
            }
        } else if(filteredByHardwares) {
            if (filteredUser == null) {
                wishes.addAll(new ArrayList<Wish>(wishBox.findAllHardwaresFrom(origin)));
            } else {
                wishes.addAll(Collections2.filter(filteredUser.getAllWishedHardwares(), new WishPredicates.OriginPredicate(origin)));
            }
        } else {
            if (filteredUser == null) {
                wishes.addAll(new ArrayList<Wish>(wishBox.findAllAccessoriesFrom(origin)));
            } else {
                wishes.addAll(Collections2.filter(filteredUser.getAllWishedAccessories(), new WishPredicates.OriginPredicate(origin)));
            }
        }

        if (filteredPlatform != null) {
            WishPredicates.PlatformPredicate filterByPlatform = new WishPredicates.PlatformPredicate(filteredPlatform);
            wishes = filter(wishes, filterByPlatform);
        }
        return wishes.size();
    }

    public int getNumWishesInThisPlatform(Platform platform) {
        Collection<Wish> wishes = new ArrayList<Wish>();
        if (filteredByGames) {
            if (filteredUser == null) {
                wishes.addAll(wishBox.findAllGamesOn(platform));
            } else {
                wishes.addAll(filter(filteredUser.getAllWishedGames(), new WishPredicates.PlatformPredicate(platform)));
            }
        } else if(filteredByHardwares) {
            if (filteredUser == null) {
                wishes.addAll(wishBox.findAllHardwaresOn(platform));
            } else {
                wishes.addAll(filter(filteredUser.getAllWishedHardwares(), new WishPredicates.PlatformPredicate(platform)));
            }
        } else {
            if (filteredUser == null) {
                wishes.addAll(new ArrayList<Wish>(wishBox.findAllAccessoriesOn(platform)));
            } else {
                wishes.addAll(filter(filteredUser.getAllWishedAccessories(), new WishPredicates.PlatformPredicate(platform)));
            }
        }
        return wishes.size();
    }

    private Collection<Wish> allWishes() {
        Collection<Wish> wishes = new ArrayList<Wish>();
        if (filteredByGames) {
            if (filteredUser == null) {
                wishes.addAll(wishBox.findAllGames());
            } else {
                wishes.addAll(filteredUser.getAllWishedGames());
            }
        } else {
            if (filteredUser == null) {
                wishes.addAll(new ArrayList<Wish>(wishBox.findAllHardwares()));
            } else {
                wishes.addAll(filteredUser.getAllWishedHardwares());
            }
        }
        return wishes;
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedHardwares();
        }
        return wishBox.findAllHardwares().size();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedGames();
        }
        return wishBox.findAllGames().size();
    }

    @Override
    public long getNumAccessories() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedAccessories();
        }
        return wishBox.findAllAccessories().size();
    }

}
