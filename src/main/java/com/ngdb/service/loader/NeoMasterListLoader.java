package com.ngdb.service.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ngdb.domain.Game;

public class NeoMasterListLoader {

	private String NGH_PATTERN = ">([0-9 ]+)<";
	private String MEGA_COUNT_PATTERN = ">([0-9]+) MEGA<";
	private String TITLE_PATTERN = ">([a-zA-Z 0-9])<o:p>";

	public Game loadGameInfo(String html) {
		String[] splits = html.split("<td");
		String nghColumn = splits[1];

		String titleColumn = splits[2];

		String megaCountColumn = splits[4];
		System.err.println(titleColumn);

		Game game = new Game();
		try {
			String ngh = extract(nghColumn, NGH_PATTERN);
			game.setNgh(ngh);
		} catch (IllegalStateException e) {
		}
		try {
			game.setTitle(extract(titleColumn, TITLE_PATTERN));
		} catch (IllegalStateException e) {
		}
		try {
			game.setMegaCount(Long.valueOf(extract(megaCountColumn, MEGA_COUNT_PATTERN)));
		} catch (IllegalStateException e) {
		}

		return game;
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

}
