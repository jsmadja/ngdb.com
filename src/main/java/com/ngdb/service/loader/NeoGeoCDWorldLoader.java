package com.ngdb.service.loader;

import static com.ngdb.service.loader.Loaders.extract;
import static com.ngdb.service.loader.Loaders.extractAsLong;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.io.ByteStreams;

public class NeoGeoCDWorldLoader {

	private String NGH_PATTERN = ">([0-9 ]+)<";
	private String NGCD_PATTERN = ">([ ACDEGIN-]+-[0-9]+[E]*)<";
	private String PUBLISHER_PATTERN = ">([ A-Za-z/ ]+)<";
	private String TITLE_PATTERN = ">(['a-zA-Z0-9 :,-?!ōôûūç/]+)<";
	private String MEGA_COUNT_PATTERN = ">(\\d+)<";

	public List<ExternalGame> load(InputStream stream) throws IOException {
		List<ExternalGame> games = new ArrayList<ExternalGame>();

		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			try {
				if (split.split("</td>").length >= 6) {
					ExternalGame game = loadGameInfo(split);
					game.setFromNgcd(true);
					games.add(game);
					// System.err.println(game + "\n");
				}
			} catch (Exception e) {
			}
		}
		return games;
	}

	private ExternalGame loadGameInfo(String html) {
		String[] splits = html.split("</td>");
		String nghColumn = splits[0].trim();
		String ngcdColumn = splits[1].trim();
		String publisherColumn = splits[3].trim();
		String titleColumn = splits[4].replaceAll("</font>", "").trim();
		String megaCountColumn = splits[5].trim();

		ExternalGame game = new ExternalGame();
		insertNgh(nghColumn, game);
		insertNgcd(ngcdColumn, game);
		insertPublisher(publisherColumn, game);
		insertTitle(titleColumn, game);
		insertMegaCount(megaCountColumn, game);
		return game;
	}

	private void insertMegaCount(String megaCountColumn, ExternalGame game) {
		game.setMegaCount(extractAsLong(megaCountColumn, MEGA_COUNT_PATTERN));
	}

	private void insertTitle(String titleColumn, ExternalGame game) {
		String title = extract(titleColumn, TITLE_PATTERN).replaceAll("$</a>", "");
		title = StringUtils.remove(title, "</font>");
		title = StringUtils.remove(title, "</a>");
		title = StringUtils.remove(title, "</span>");
		game.setTitle(title.toUpperCase().trim());
	}

	private void insertPublisher(String publisherColumn, ExternalGame game) {
		game.setPublisher(extract(publisherColumn, PUBLISHER_PATTERN).trim());
	}

	private void insertNgh(String nghColumn, ExternalGame game) {
		game.setNgh(extract(nghColumn, NGH_PATTERN));
	}

	private void insertNgcd(String ngcdColumn, ExternalGame game) {
		game.setNgcdJap(extract(ngcdColumn, NGCD_PATTERN).trim());
	}

	public boolean accept(String s) {
		return s.contains("html");
	}

}
