package com.ngdb.service.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.ngdb.domain.Game;

public class NeoGeoComLoader {

	private String NGH_PATTERN = ">([0-9 E\\?]+)<";
	private String PUBLISHER_PATTERN = ">([A-Za-z/ ]+)<";
	private String TITLE_PATTERN = ">([ /\\+&'a-zA-Z0-9² :,-?!ōôûūç/\\(\\)\\\"]+)<";
	private String MEGA_COUNT_PATTERN = ">(\\d+)<";

	public Game loadGameInfo(String html) {
		String[] splits = html.split("</td>");
		String nghColumn = clean(splits[1]);
		String titleColumn = clean(splits[3]);
		String publisherColumn = clean(splits[2]);
		String megaCountColumn = clean(splits[4]);
		String aesDateColumn = clean(splits[4]);

		Game game = new Game();
		insertNgh(nghColumn, game);
		insertPublisher(publisherColumn, game);
		insertTitle(titleColumn, game);
		insertMegaCount(megaCountColumn, game);
		return game;
	}

	private void insertMegaCount(String megaCountColumn, Game game) {
		game.setMegaCount(extractAsLong(megaCountColumn, MEGA_COUNT_PATTERN));
	}

	private void insertTitle(String titleColumn, Game game) {
		String title = extract(titleColumn, TITLE_PATTERN);
		title = clean(title);
		title = StringUtils.remove(title, "</font>");
		title = StringUtils.remove(title, "<font size=\"2\">");
		title = title.replaceAll("<img .*\">", "");
		game.setTitle(title);
	}

	private String clean(String title) {
		title = StringEscapeUtils.unescapeHtml(title);
		title = title.replaceAll(" <", "<");
		title = title.replaceAll("> ", ">");
		title = title.replaceAll("  ", "");
		title = StringUtils.remove(title, "			");
		title = StringUtils.remove(title, "           ");
		title = StringUtils.remove(title, "*");
		title = title.replaceAll("N/A", " ");
		title = title.replaceAll("<a .*\">", "");
		title = title.replaceAll("<i>", "").replaceAll("</i>", "").replaceAll("</a>", "");
		title = title.replaceAll(" ", " ");
		title = title.replaceAll("\n", " ");
		return title.trim();
	}

	private void insertPublisher(String publisherColumn, Game game) {
		game.setPublisher(extract(publisherColumn, PUBLISHER_PATTERN));
	}

	private void insertNgh(String nghColumn, Game game) {
		game.setNgh(extract(nghColumn, NGH_PATTERN));
	}

	private Long extractAsLong(String html, String pattern) {
		Matcher matcher = Pattern.compile(pattern).matcher(html);
		if (matcher.find()) {
			return Long.valueOf(matcher.group(1));
		}
		return 0L;
	}

	private String extract(String html, String pattern) {
		try {
			Matcher matcher = Pattern.compile(pattern).matcher(html);
			matcher.find();
			return matcher.group(1).trim();
		} catch (IllegalStateException e) {
			return "";
		}
	}

	public boolean accept(String s) {
		return s.contains("html");
	}

}
