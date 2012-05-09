package com.ngdb.service.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ngdb.domain.Game;

public class NeoGeoCDWorldLoader {

	private String NGH_PATTERN = ">([0-9 ]+)<";
	private String NGCD_PATTERN = ">([ ACDEGIN-]+-[0-9]+[E]*)<";
	private String PUBLISHER_PATTERN = ">([ A-Za-z/ ]+)<";
	private String TITLE_PATTERN = ">(['a-zA-Z0-9 :,-?!ōôûūç/]+)<";
	private String MEGA_COUNT_PATTERN = ">(\\d+)<";

	public Game loadGameInfo(String html) {
		String[] splits = html.split("</td>");
		String nghColumn = splits[0];
		String ngcdColumn = splits[1];
		String publisherColumn = splits[3];
		String titleColumn = splits[4].replaceAll("</font>", "");
		String megaCountColumn = splits[5];

		Game game = new Game();
		insertNgh(nghColumn, game);
		insertNgcd(ngcdColumn, game);
		insertPublisher(publisherColumn, game);
		insertTitle(titleColumn, game);
		insertMegaCount(megaCountColumn, game);
		return game;
	}

	private void insertMegaCount(String megaCountColumn, Game game) {
		game.setMegaCount(extractAsLong(megaCountColumn, MEGA_COUNT_PATTERN));
	}

	private void insertTitle(String titleColumn, Game game) {
		String japaneseTitle = extract(titleColumn, TITLE_PATTERN).replaceAll("$</a>", "");
		japaneseTitle = StringUtils.remove(japaneseTitle, "</font>");
		japaneseTitle = StringUtils.remove(japaneseTitle, "</a>");
		japaneseTitle = StringUtils.remove(japaneseTitle, "</span>");
		game.setTitle(japaneseTitle.trim());
	}

	private void insertPublisher(String publisherColumn, Game game) {
		game.setPublisher(extract(publisherColumn, PUBLISHER_PATTERN).trim());
	}

	private void insertNgh(String nghColumn, Game game) {
		game.setNgh(extract(nghColumn, NGH_PATTERN));
	}

	private void insertNgcd(String ngcdColumn, Game game) {
		game.setNgcdJap(extract(ngcdColumn, NGCD_PATTERN).trim());
	}

	private Long extractAsLong(String html, String pattern) {
		Matcher matcher = Pattern.compile(pattern).matcher(html);
		if (matcher.find()) {
			return Long.valueOf(matcher.group(1));
		}
		return null;
	}

	private String extract(String html, String pattern) {
		try {
			Matcher matcher = Pattern.compile(pattern).matcher(html);
			matcher.find();
			return matcher.group(1);
		} catch (IllegalStateException e) {
			throw new IllegalStateException("Cannot find pattern in " + html, e);
		}
	}

	public boolean accept(String s) {
		return s.contains("html");
	}

}
