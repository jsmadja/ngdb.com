package com.ngdb;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;

import java.util.Comparator;

public class Comparators {

	public static Comparator<Article> byTitlePlatformOrigin = new Comparator<Article>() {
		@Override
		public int compare(Article a1, Article a2) {
			if (a1.getTitle().compareToIgnoreCase(a2.getTitle()) != 0) {
				return a1.getTitle().compareToIgnoreCase(a2.getTitle());
			}
			if (a1.getPlatformShortName().compareTo(a2.getPlatformShortName()) != 0) {
				return a1.getPlatformShortName().compareTo(a2.getPlatformShortName());
			}
			if (a1.getOriginTitle().compareTo(a2.getOriginTitle()) != 0) {
				return a1.getOriginTitle().compareTo(a2.getOriginTitle());
			}
			return 0;
		}
	};

    public static Comparator<Game> gamesByTitlePlatformOrigin = new Comparator<Game>() {
        @Override
        public int compare(Game a1, Game a2) {
            if (a1.getTitle().compareToIgnoreCase(a2.getTitle()) != 0) {
                return a1.getTitle().compareToIgnoreCase(a2.getTitle());
            }
            if (a1.getPlatformShortName().compareTo(a2.getPlatformShortName()) != 0) {
                return a1.getPlatformShortName().compareTo(a2.getPlatformShortName());
            }
            if (a1.getOriginTitle().compareTo(a2.getOriginTitle()) != 0) {
                return a1.getOriginTitle().compareTo(a2.getOriginTitle());
            }
            return 0;
        }
    };

    public static Comparator<Game> gamesByNghTitlePlatformOrigin = new Comparator<Game>() {
        @Override
        public int compare(Game a1, Game a2) {
            String ngh1 = a1.getNgh();
            String ngh2 = a2.getNgh();
            if(ngh1 != null && ngh2 != null) {
                if(ngh1.compareToIgnoreCase(ngh2) != 0) {
                    return ngh1.compareToIgnoreCase(ngh2);
                }
            }
            if (a1.getTitle().compareToIgnoreCase(a2.getTitle()) != 0) {
                return a1.getTitle().compareToIgnoreCase(a2.getTitle());
            }
            if (a1.getPlatformShortName().compareTo(a2.getPlatformShortName()) != 0) {
                return a1.getPlatformShortName().compareTo(a2.getPlatformShortName());
            }
            if (a1.getOriginTitle().compareTo(a2.getOriginTitle()) != 0) {
                return a1.getOriginTitle().compareTo(a2.getOriginTitle());
            }
            return 0;
        }
    };
    public static Comparator<Game> compareByNghOrId = new Comparator<Game>() {
        @Override
        public int compare(Game game1, Game game2) {
            String ngh1 = game1.getNgh();
            String ngh2 = game2.getNgh();
            if (ngh1 == null || ngh2 == null || ngh1.equalsIgnoreCase(ngh2)) {
                return game1.getId().compareTo(game2.getId());
            }
            return ngh1.compareToIgnoreCase(ngh2);
        }
    };

}
