package com.ngdb;

import java.util.Comparator;

import com.ngdb.entities.article.Game;

public class Comparators {

	public static Comparator<Game> byTitlePlatformOrigin = new Comparator<Game>() {
		@Override
		public int compare(Game g1, Game g2) {
			if (g1.getTitle().compareToIgnoreCase(g2.getTitle()) != 0) {
				return g1.getTitle().compareToIgnoreCase(g2.getTitle());
			}
			if (g1.getPlatform().compareTo(g2.getPlatform()) != 0) {
				return g1.getPlatform().compareTo(g2.getPlatform());
			}
			if (g1.getOrigin().compareTo(g2.getOrigin()) != 0) {
				return g1.getOrigin().compareTo(g2.getOrigin());
			}
			return 0;
		}
	};

}
