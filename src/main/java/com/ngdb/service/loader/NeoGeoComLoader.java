package com.ngdb.service.loader;

import static com.ngdb.service.loader.Loaders.extract;
import static com.ngdb.service.loader.Loaders.extractAsLong;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.io.ByteStreams;
import com.ngdb.entities.ExternalGame;

public class NeoGeoComLoader {

	private String NGH_PATTERN = ">([0-9 E\\?]+)<";
	private String PUBLISHER_PATTERN = ">([A-Za-z/ ]+)<";
	private String TITLE_PATTERN = ">([ /\\+&'a-zA-Z0-9² :,-?!ōôûūç/\\(\\)\\\"]+)<";
	private String MEGA_COUNT_PATTERN = ">(\\d+)<";
	private String DATE_PATTERN = ">([0-9.]+)<";

	public List<ExternalGame> load(InputStream stream) throws IOException {
		List<ExternalGame> games = new ArrayList<ExternalGame>();

		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			if (split.split("</td>").length >= 9 && !split.contains("NGH")) {
				ExternalGame game = loadGameInfo(split);
				if (!game.getTitle().isEmpty()) {
					game.setFromNgc(true);
					games.add(game);
				}
			}
		}
		return games;
	}

	public ExternalGame loadGameInfo(String html) {
		String[] splits = html.split("</td>");
		String nghColumn = clean(splits[1]);
		String titleColumn = clean(splits[3]);
		String publisherColumn = clean(splits[2]);
		String megaCountColumn = clean(splits[4]);
		String aesColumn = clean(splits[6]);
		String cdColumn = clean(splits[8]);

		ExternalGame game = new ExternalGame();
		insertNgh(nghColumn, game);
		insertPublisher(publisherColumn, game);
		insertTitle(titleColumn, game);
		insertMegaCount(megaCountColumn, game);
		insertAES(aesColumn, game);
		insertCD(cdColumn, game);
		return game;
	}

	private void insertAES(String aes, ExternalGame game) {
		String aesDate = extract(aes, DATE_PATTERN);
		game.setAesDate(cleanDate(aesDate));
	}

	private String cleanDate(String date) {
		date = date.replaceAll("\\.", "/");
		for (int i = 0; i <= 9; i++) {
			date = date.replaceAll("\\/9" + i, "/199" + i);
		}
		date = date.replaceAll("\\/00", "/2000");
		return date;
	}

	private void insertCD(String cd, ExternalGame game) {
		String cdDate = extract(cd, DATE_PATTERN);
		game.setCdDate(cleanDate(cdDate));
	}

	private void insertMegaCount(String megaCountColumn, ExternalGame game) {
		game.setMegaCount(extractAsLong(megaCountColumn, MEGA_COUNT_PATTERN));
	}

	private void insertTitle(String titleColumn, ExternalGame game) {
		String title = extract(titleColumn, TITLE_PATTERN);
		title = clean(title);
		title = StringUtils.remove(title, "</font>");
		title = StringUtils.remove(title, "<font size=\"2\">");
		title = title.replaceAll("<img .*\">", "").trim();
		game.setTitle(title.toUpperCase());
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

	private void insertPublisher(String publisherColumn, ExternalGame game) {
		game.setPublisher(extract(publisherColumn, PUBLISHER_PATTERN));
	}

	private void insertNgh(String nghColumn, ExternalGame game) {
		game.setNgh(extract(nghColumn, NGH_PATTERN));
	}

	public boolean accept(String s) {
		return s.contains("html");
	}

}
