package com.ngdb.service.loader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.io.Closeables;
import com.ngdb.domain.ExternalGame;

public class Merger {

	private static List<ExternalGame> finalList = new ArrayList<ExternalGame>();

	public static void main(String[] args) throws IOException {
		NeoGeoMuseumLoader neoGeoMuseumLoader = new NeoGeoMuseumLoader();
		ClassLoader classLoader = Merger.class.getClassLoader();
		List<ExternalGame> ngmGames = neoGeoMuseumLoader.load(classLoader.getResourceAsStream("sources/neogeomuseum.html"));

		NeoGeoCDWorldLoader neoGeoCDWorldLoader = new NeoGeoCDWorldLoader();
		List<ExternalGame> ngcdwGames = neoGeoCDWorldLoader.load(classLoader.getResourceAsStream("sources/neogeocdworld.html"));

		NeoGeoComLoader neoGeoComLoader = new NeoGeoComLoader();
		List<ExternalGame> ngcGames = neoGeoComLoader.load(classLoader.getResourceAsStream("sources/neogeocom.html"));

		System.err.println("---");
		System.err.println("ngm:" + ngmGames.size());
		System.err.println("ngcdw:" + ngcdwGames.size());
		System.err.println("ngc:" + ngcGames.size());
		mergeLists(ngcdwGames, ngcGames);

		System.err.println("---");
		System.err.println("ngm:" + ngmGames.size());
		System.err.println("ngcdw:" + ngcdwGames.size());
		System.err.println("ngc:" + ngcGames.size());
		mergeLists(ngmGames, ngcdwGames);

		System.err.println("---");
		System.err.println("ngm:" + ngmGames.size());
		System.err.println("ngcdw:" + ngcdwGames.size());
		System.err.println("ngc:" + ngcGames.size());
		mergeLists(ngcGames, ngmGames);

		System.err.println("---");
		System.err.println("final:" + finalList.size());

		System.err.println("---");
		saveLists(ngmGames, ngcdwGames, ngcGames);
	}

	private static void saveLists(List<ExternalGame> ngmGames, List<ExternalGame> ngcdwGames, List<ExternalGame> ngcGames) throws FileNotFoundException, IOException {
		Collections.sort(ngcdwGames);
		writeTo(ngcdwGames, "/tmp/ngcdwGames.txt");

		Collections.sort(ngcGames);
		writeTo(ngcGames, "/tmp/ngcGames.txt");

		Collections.sort(ngmGames);
		writeTo(ngmGames, "/tmp/ngmGames.txt");

		Collections.sort(finalList);
		writeTo(finalList, "/tmp/merged-games.txt");

		finalList.addAll(ngcdwGames);
		finalList.addAll(ngcGames);
		finalList.addAll(ngmGames);
		Collections.sort(finalList);
		writeTo(finalList, "/tmp/games.txt");
	}

	private static void writeTo(List<ExternalGame> games, String file) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(file);
		for (ExternalGame game : games) {
			fos.write((game.toString() + "\n\n").getBytes());
		}
		Closeables.closeQuietly(fos);
	}

	private static void mergeLists(List<ExternalGame> list1, List<ExternalGame> list2) {
		for (ExternalGame game : list1.toArray(new ExternalGame[] {})) {
			String ngh = game.getNgh();
			String title = game.getTitle();
			boolean isMerged = false;
			int index = list1.indexOf(game);
			ExternalGame mergedGame = list1.get(index);
			if (!ngh.isEmpty()) {
				try {
					ExternalGame matchingGame = findGameByNghIn(list2, ngh);
					mergedGame = merge(game, matchingGame);
					isMerged = true;
				} catch (Exception e) {
				}
			}
			if (!isMerged && !title.isEmpty()) {
				try {
					ExternalGame matchingGame = findGameByTitleIn(list2, title);
					mergedGame = merge(game, matchingGame);
					isMerged = true;
				} catch (Exception ex) {
				}
			}
			if (isMerged) {
				list1.remove(index);
				finalList.add(mergedGame);
			}
		}
	}

	private static ExternalGame findGameByTitleIn(List<ExternalGame> games, String title) throws Exception {
		title = cleanTitle(title);
		for (ExternalGame game : games) {
			String gameTitle = cleanTitle(game.getTitle());
			// System.err.println(gameTitle + ".equals(" + title + ")");
			if (gameTitle.equals(title)) {
				games.remove(game);
				return game;
			}
		}
		throw new Exception("No matching game.");
	}

	private static String cleanTitle(String title) {
		title = StringUtils.remove(title, "   ");
		title = StringUtils.remove(title, "THE ");
		title = title.replaceAll("-", " ");
		title = title.replaceAll("KING OF MONSTERS", "KING OF MONSTER");
		title = title.replaceAll("KING OF FIGHTERS", "KING OF FIGHTER");
		title = StringUtils.remove(title, ", THE");
		return title.trim();
	}

	private static ExternalGame merge(ExternalGame game1, ExternalGame game2) {
		ExternalGame game = new ExternalGame();
		game.setNgh(value(game1, game2, "ngh"));
		game.setTitle(value(game1, game2, "title"));
		game.setPublisher(value(game1, game2, "publisher"));
		game.setNgcdJap(value(game1, game2, "ngcdJap"));
		game.setAesDate(value(game1, game2, "aesDate"));
		game.setCdDate(value(game1, game2, "cdDate"));
		game.setMvsDate(value(game1, game2, "mvsDate"));
		game.setMegaCount(Long.valueOf(value(game1, game2, "megaCount")));
		game.setJapaneseTitle(value(game1, game2, "japaneseTitle"));
		game.setGenre(value(game1, game2, "genre"));
		game.setFromNgc(Boolean.valueOf(value(game1, game2, "fromNgc")));
		game.setFromNgm(Boolean.valueOf(value(game1, game2, "fromNgm")));
		game.setFromNgcd(Boolean.valueOf(value(game1, game2, "fromNgcd")));
		return game;
	}

	private static String value(Object game1, Object game2, String property) {
		Object value1 = ReflectionTestUtils.getField(game1, property);
		Object value2 = ReflectionTestUtils.getField(game2, property);
		String strValue1 = value1 == null ? "" : value1.toString();
		String strValue2 = value2 == null ? "" : value2.toString();

		strValue1 = cleanTitle(strValue1);
		strValue2 = cleanTitle(strValue2);

		String mergeValue = "";
		if (strValue1.equals("0")) {
			strValue1 = strValue2;
		}
		if (strValue2.equals("0")) {
			strValue2 = strValue1;
		}
		if ("true".equals(strValue1) || "true".equals(strValue2)) {
			mergeValue = "true";
		} else if (strValue1.isEmpty()) {
			mergeValue = strValue2;
		} else if (strValue2.isEmpty()) {
			mergeValue = strValue1;
		} else if (strValue1.equals(strValue2)) {
			mergeValue = strValue1;
		} else {
			System.err.println("[WARN " + property + " differs : <" + strValue1 + "> != <" + strValue2 + "> ]");
			mergeValue = strValue1;
		}
		return mergeValue;
	}

	private static ExternalGame findGameByNghIn(List<ExternalGame> ngcGames, String ngh) throws Exception {
		for (ExternalGame game : ngcGames) {
			if (game.getNgh().equals(ngh)) {
				ngcGames.remove(game);
				return game;
			}
		}
		throw new Exception("No matching game.");
	}

}
