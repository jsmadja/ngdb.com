package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.io.ByteStreams;
import com.ngdb.domain.Game;

public class NeoGeoMuseumLoaderTest {

	private NeoGeoMuseumLoader loader = new NeoGeoMuseumLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoMuseumLoader.class.getClassLoader().getResourceAsStream("sources/neogeomuseum.html");
		List<Game> games = new ArrayList<Game>();

		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			Game game = loader.loadGameInfo(split);
			if (!game.getTitle().isEmpty()) {
				games.add(game);
				System.err.println(game + "\n");
			}
		}
		System.err.println(games.size());
	}
}
