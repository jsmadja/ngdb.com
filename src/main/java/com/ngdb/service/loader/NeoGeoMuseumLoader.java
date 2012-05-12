package com.ngdb.service.loader;

import static com.ngdb.service.loader.Loaders.extract;
import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;
import static org.apache.commons.lang.StringUtils.remove;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteStreams;
import com.ngdb.domain.ExternalGame;

public class NeoGeoMuseumLoader {

	private static final String DATE = "\\d{4}/\\d{2}/\\d{2}";

	private static final String TITLE_PATTERN = "<td class=\"title\">(.*)</td>";
	private static final String PUBLISHER_PATTERN = "<td class=\"maker\">(.*)</td>";
	private static final String GENRE_PATTERN = "<td class=\"genre\">(.*)</td>";
	private static final String AES_DATE_PATTERN = ".*NEOGEO ROM-cart:(" + DATE + ").*";
	private static final String MVS_DATE_PATTERN = ".*MVS Cartridge:(" + DATE + ").*";
	private static final String CD_DATE_PATTERN = ".*NEOGEO CD:(" + DATE + ").*";

	public ExternalGame loadGameInfo(String html) {
		html = clean(html);
		ExternalGame game = new ExternalGame();
		game.setTitle(extract(html, TITLE_PATTERN).toUpperCase());
		game.setPublisher(extract(html, PUBLISHER_PATTERN));
		game.setGenre(extract(html, GENRE_PATTERN));
		game.setAesDate(extract(html, AES_DATE_PATTERN));
		game.setMvsDate(extract(html, MVS_DATE_PATTERN));
		game.setCdDate(extract(html, CD_DATE_PATTERN));
		game.setFromNgm(true);
		return game;
	}

	public List<ExternalGame> load(InputStream stream) throws IOException {
		List<ExternalGame> games = new ArrayList<ExternalGame>();
		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			ExternalGame game = loadGameInfo(split);
			if (!game.getTitle().isEmpty()) {
				games.add(game);
				// System.err.println(game + "\n");
			}
		}
		return games;
	}

	private String clean(String title) {
		title = unescapeHtml(title);
		title = remove(title, "<span class=\"noLink\">");
		title = remove(title, "</span>");
		return title.trim();
	}

}
