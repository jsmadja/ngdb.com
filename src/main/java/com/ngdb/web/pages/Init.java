package com.ngdb.web.pages;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ngdb.entities.ExternalGame;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.vo.Box;
import com.ngdb.entities.article.vo.Origin;
import com.ngdb.entities.article.vo.Platform;
import com.ngdb.entities.article.vo.Publisher;
import com.ngdb.service.loader.NeoGeoCDWorldLoader;

public class Init {

	private static NeoGeoCDWorldLoader loader = new NeoGeoCDWorldLoader();

	@Inject
	Session session;

	@CommitAfter
	void onActivate() throws Exception {

		Box box = (Box) session.load(Box.class, 1L);
		Platform platform = (Platform) session.load(Platform.class, 1L);
		Origin origin = (Origin) session.load(Origin.class, 2L);

		InputStream stream = Init.class.getClassLoader().getResourceAsStream("sources/neogeocdworld.html");
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			System.err.println(externalGame);
			Game game = new Game();
			game.setBox(box);
			game.setDetails("automatically imported");
			game.setMegaCount(0L);
			game.setNgh(externalGame.getNgh());
			game.setPlatform(platform);
			game.setOrigin(origin);
			Publisher publisher = (Publisher) session.createCriteria(Publisher.class).add(Restrictions.like("name", externalGame.getPublisher())).uniqueResult();
			game.setPublisher(publisher);
			SimpleDateFormat sdf = new SimpleDateFormat();
			if (StringUtils.isNotEmpty(externalGame.getAesDate())) {
				game.setReleaseDate(sdf.parse(externalGame.getAesDate()));
			} else if (StringUtils.isNotEmpty(externalGame.getMvsDate())) {
				game.setReleaseDate(sdf.parse(externalGame.getMvsDate()));
			} else if (StringUtils.isNotEmpty(externalGame.getCdDate())) {
				game.setReleaseDate(sdf.parse(externalGame.getCdDate()));
			}
			game.setTitle(externalGame.getTitle());
			session.merge(game);
		}
	}
}
