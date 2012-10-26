package com.ngdb.services;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Cacher {

    private static Cache averageMarksOfArticles;

    static {
        CacheManager create = CacheManager.create();
        averageMarksOfArticles = create.getCache("average.marks.of.games");
    }

    public boolean hasAverageMarkOf(Article article) {
        if(!article.isGame()) {
            return false;
        }
        Game game = (Game) article;
        if(!averageMarksOfArticles.isKeyInCache(key(game))) {
            return false;
        }
        Element element = averageMarksOfArticles.get(key(game));
        return !element.isExpired();
    }

    public void setAverageMarkOf(Article article, Double averageMark) {
        if(article.isGame()) {
            averageMarksOfArticles.put(new Element(key((Game)article), averageMark));
        }
    }

    public Double getAverageMarkOf(Article article) {
        Game game = (Game) article;
        return (Double)averageMarksOfArticles.get(key(game)).getValue();
    }

    public void invalidateAverageMarkOf(Game game) {
        if(hasAverageMarkOf(game)) {
            averageMarksOfArticles.remove(key(game));
        }
    }

    private String key(Game game) {
        return game.getNgh();
    }

}
