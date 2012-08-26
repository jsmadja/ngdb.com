package com.ngdb;

public class StarsUtil {

    public static String toStarsHtml(String mark) {
        if (mark.length() == 1) {
            mark = "0" + mark;
        }

        int numStars = Integer.valueOf("" + mark.charAt(0));
        boolean halfStar = "5".equals("" + mark.charAt(1));
        String stars = "";
        int numGreyStars = 5 - numStars;
        for (int i = 0; i < numStars; i++) {
            stars += "<img width=\"20px\" src=\"/img/stars/star.png\">";
        }
        if (halfStar) {
            stars += "<img width=\"20px\" src=\"/img/stars/half_star.png\">";
            numGreyStars--;
        }
        for (int i = 0; i < numGreyStars; i++) {
            stars += "<img width=\"20px\" src=\"/img/stars/grey_star.png\">";
        }
        return stars;
    }

}
