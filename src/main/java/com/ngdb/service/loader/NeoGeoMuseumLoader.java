package com.ngdb.service.loader;

import static com.ngdb.service.loader.Loaders.extract;
import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;
import static org.apache.commons.lang.StringUtils.remove;

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
		Game game = new Game();
		game.setTitle(extract(html, TITLE_PATTERN));
		game.setPublisher(extract(html, PUBLISHER_PATTERN));
		game.setGenre(extract(html, GENRE_PATTERN));
		game.setAesDate(extract(html, AES_DATE_PATTERN));
		game.setMvsDate(extract(html, MVS_DATE_PATTERN));
		game.setCdDate(extract(html, CD_DATE_PATTERN));
		return game;
	}

	private String clean(String title) {
		title = unescapeHtml(title);
		title = remove(title, "<span class=\"noLink\">");
		title = remove(title, "</span>");
		return title.trim();
	}

}
