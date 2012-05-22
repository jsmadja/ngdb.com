package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ngdb.entities.ExternalGame;

public class NeoMasterListLoader {

	public ExternalGame loadGameInfo(String html) {
		return new ExternalGame();
	}

	public List<ExternalGame> findAll(InputStream stream) {
		List<ExternalGame> games = new ArrayList<ExternalGame>();

		Scanner sc = new Scanner(stream);
		while (sc.hasNextLine()) {
			String csvLine = sc.nextLine();
			String[] splits = csvLine.split(";");
			String ngh = splits[0];
			String title = splits[1];
			String japaneseTitle = splits[2];
			ExternalGame game = new ExternalGame();
			game.setNgh(ngh);
			game.setTitle(title);
			game.setJapaneseTitle(japaneseTitle);
			games.add(game);
		}

		return games;
	}

}
