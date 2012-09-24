package com.ngdb;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import org.hibernate.Session;

import java.util.List;

public class StarsUtil {

    public static final int STAR_SIZE = 15;

    public static String getStars(Article resultX, Session session, ArticleFactory articleFactory) {
        if(resultX.isGame()) {
            Game result = (Game) session.load(Game.class, resultX.getId());
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

}
