package com.ngdb.service.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.ngdb.domain.Game;

public class NeoGeoMuseumLoader {

	private static final String DATE = "\\d{4}/\\d{2}/\\d{2}";

	private static final String TITLE_PATTERN = "<td class=\"title\">(.*)</td>";
	private static final String PUBLISHER_PATTERN = "<td class=\"maker\">(.*)</td>";
	private static final String GENRE_PATTERN = "<td class=\"genre\">(.*)</td>";
	private static final String AES_DATE_PATTERN = ".*NEOGEO ROM-cart:(" + DATE + ").*";
	private static final String MVS_DATE_PATTERN = ".*MVS Cartridge:(" + DATE + ").*";
	private static final String CD_DATE_PATTERN = ".*NEOGEO CD:(" + DATE + ").*";

	public Game loadGameInfo(String html) {
		html = clean(html);

		String title = extract(html, TITLE_PATTERN);
		String publisher = extract(html, PUBLISHER_PATTERN);
		String genre = extract(html, GENRE_PATTERN);
		String aesDate = extract(html, AES_DATE_PATTERN);
		String mvsDate = extract(html, MVS_DATE_PATTERN);
		String cdDate = extract(html, CD_DATE_PATTERN);

		Game game = new Game();
		game.setTitle(title);
		game.setPublisher(publisher);
		game.setGenre(genre);
		game.setAesDate(aesDate);
		game.setMvsDate(mvsDate);
		game.setCdDate(cdDate);
		return game;
	}

	private String clean(String title) {
		title = StringEscapeUtils.unescapeHtml(title);
		title = StringUtils.remove(title, "<span class=\"noLink\">");
		title = StringUtils.remove(title, "</span>");
		return title.trim();
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
