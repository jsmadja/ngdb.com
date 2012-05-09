package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.io.ByteStreams;
import com.ngdb.domain.Game;

public class NeoGeoComLoaderTest {

	private NeoGeoComLoader loader = new NeoGeoComLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoCDWorldLoaderTest.class.getClassLoader().getResourceAsStream("sources/neogeocom.html");
		List<Game> games = new ArrayList<Game>();

		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			if (split.split("</td>").length >= 6 && !split.contains("NGH")) {
				Game game = loader.loadGameInfo(split);
				if (!game.getTitle().isEmpty()) {
					games.add(game);
					System.err.println(game + "\n");
				}
			}
		}
		System.err.println(games.size());
	}
}
