package com.ngdb;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.shop.ShopItem;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import java.util.List;

import static org.apache.commons.lang.StringUtils.repeat;

public class StarsUtil {

    public static final int STAR_SIZE = 15;
    public static final int MAX_CONDITION = 9;

    public static String getStars(Article article, Session session, ArticleFactory articleFactory) {
        if(article.isGame()) {
            Game result = (Game) session.load(Game.class, article.getId());
            if (result.getHasReviews()) {
                String mark = result.getAverageMark();
                return StarsUtil.toStarsHtml(mark, 15);
            } else {
                String ngh = result.getNgh();
                List<Game> games = articleFactory.findAllGamesByNgh(ngh);
                for (Game game : games) {
                    if (game.getHasReviews()) {
                        String mark = game.getAverageMark();
                        return StarsUtil.toStarsHtml(mark, 15);
                    }
                }
            }
        }
        return StarsUtil.toStarsHtml("0", STAR_SIZE);
    }

    public static String toStarsHtml(String mark, int size) {
        if (mark.length() == 1) {
            mark = "0" + mark;
        }

        int numStars = Integer.valueOf("" + mark.charAt(0));
        boolean halfStar = "5".equals("" + mark.charAt(1));
        String stars = "";
        int numGreyStars = 5 - numStars;
        for (int i = 0; i < numStars; i++) {
            stars += "<img width=\""+size+"px\" src=\"/img/stars/star.png\">";
        }
        if (halfStar) {
            stars += "<img width=\""+size+"px\" src=\"/img/stars/half_star.png\">";
            numGreyStars--;
        }
        for (int i = 0; i < numGreyStars; i++) {
            stars += "<img width=\""+size+"px\" src=\"/img/stars/grey_star.png\">";
        }
        return stars;
    }

    public static String toStarsHtml(String mark) {
        return toStarsHtml(mark, 20);
    }

    public static String getStars(ShopItem shopItem) {
        int id = shopItem.getState().getId().intValue();
        String conditionTitle = shopItem.getState().getTitle();
        int condition = MAX_CONDITION - id + 1;
        String stars = "";

        stars+= repeat(star(conditionTitle), condition);
        stars+= repeat(greystar(conditionTitle), MAX_CONDITION - condition);

        return stars;
    }

    private static String star(String conditionTitle) {
        return "<img title=\"" + conditionTitle + "\" width=\"" + STAR_SIZE + "px\" src=\"/img/stars/star.png\">";
    }

    private static String greystar(String conditionTitle) {
        return "<img title=\""+ conditionTitle +"\" width=\""+STAR_SIZE+"px\" src=\"/img/stars/grey_star.png\">";
    }
}
