package com.ngdb;

import java.util.Comparator;

import com.ngdb.entities.article.Article;

public class Comparators {

	public static Comparator<Article> byTitlePlatformOrigin = new Comparator<Article>() {
		@Override
		public int compare(Article a1, Article a2) {
			if (a1.getTitle().compareToIgnoreCase(a2.getTitle()) != 0) {
				return a1.getTitle().compareToIgnoreCase(a2.getTitle());
			}
			if (a1.getPlatform().compareTo(a2.getPlatform()) != 0) {
				return a1.getPlatform().compareTo(a2.getPlatform());
			}
			if (a1.getOrigin().compareTo(a2.getOrigin()) != 0) {
				return a1.getOrigin().compareTo(a2.getOrigin());
			}
			return 0;
		}
	};

}
