package com.ngdb.web.pages;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.ReferenceService;

public class Init {

	@Inject
	Session session;

	@Inject
	ReferenceService referenceService;

	@CommitAfter
	void onActivate() throws Exception {
		InputStreamReader stream = new InputStreamReader(new FileInputStream("/Users/juliensmadja/Developpement/workspaces/ngdb.com/src/main/resources/sources/mvs.csv"), "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(stream);
		int i = 0;
		while (bufferedReader.ready()) {
			String gameLine = bufferedReader.readLine();
			gameLine = gameLine.replaceAll("\"", "");
			gameLine = gameLine.replaceAll(", The", " The");
			System.err.println(gameLine);
			if (i > 0) {
				String[] splits = gameLine.split(",");
				String ngh = splits[0];
				String title = splits[1].replaceAll(" The", ", The");
				String platform = splits[2];
				String origin = splits[3];
				String publisher = splits[5];
				String megaCount = splits[6];
				String releaseDate = splits[7];

				Game game = new Game();
				game.setNgh(ngh);
				game.setTitle(title);
				game.setPlatform(referenceService.findPlatformByName(platform));
				game.setOrigin(referenceService.findOriginByTitle(origin));
				game.setPublisher(referenceService.findPublisherByName(publisher));
				game.setMegaCount(Long.valueOf(megaCount));
				game.setReleaseDate(new SimpleDateFormat("DD/MM/yy").parse(releaseDate));
				session.persist(game);
			}
			i++;
		}
	}
}
