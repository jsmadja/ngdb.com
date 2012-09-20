package com.ngdb;

import com.ngdb.entities.article.Article;

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

}
