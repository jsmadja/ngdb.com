package com.ngdb.web.pages;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;

public class Init {

	@Inject
	Session session;

	@Inject
	ReferenceService referenceService;

	String ROOT = "/Users/juliensmadja/Developpement/workspaces/ngdb.com/src/main/resources/sources/";

	@CommitAfter
	void onActivate() throws Exception {
		Origin Japan = referenceService.findOriginByTitle("Japan");
		// insertInDb(ROOT + "mvs.csv", Japan, referenceService.findPlatformByName("MVS"));
		insertInDb(ROOT + "aes.csv", Japan, referenceService.findPlatformByName("AES"));
		// insertInDb(ROOT + "cd_jp.csv", Japan, referenceService.findPlatformByName("CD"));
		// insertInDb(ROOT + "cd_us.csv", referenceService.findOriginByTitle("US"), referenceService.findPlatformByName("CD"));
	}

	private void insertInDb(String fichier, Origin origin, Platform platform) throws UnsupportedEncodingException, FileNotFoundException, IOException, ParseException {
		InputStreamReader stream = new InputStreamReader(new FileInputStream(fichier), "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(stream);
		int i = 0;
		while (bufferedReader.ready()) {
			String gameLine = bufferedReader.readLine();
			gameLine = gameLine.replaceAll("\"", "");
			gameLine = gameLine.replaceAll(", The", " The");
			System.err.println(gameLine);
			if (i > 0) {
				insertRow(origin, platform, gameLine);
			}
			i++;
		}
	}

	private void insertRow(Origin origin, Platform platform, String gameLine) throws ParseException {
		String[] splits = gameLine.split(",");
		String ngh = splits[0];
		String title = splits[1].replaceAll(" The", ", The");
		String publisher = splits[5];
		String megaCount = splits[6];
		String releaseDate = splits[7];

		Game game = new Game();
		game.setNgh(ngh);
		game.setTitle(title);
		game.setPlatform(platform);
		game.setOrigin(origin);
		game.setPublisher(referenceService.findPublisherByName(publisher));
		game.setMegaCount(Long.valueOf(megaCount));
		try {
			game.setReleaseDate(new SimpleDateFormat("DD/MM/yy").parse(releaseDate));
		} catch (Exception e) {

		}
		session.persist(game);
	}
}
