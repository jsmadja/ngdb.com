package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.io.ByteStreams;
import com.ngdb.domain.Game;

public class NeoGeoCDWorldLoaderTest {

	private static NeoGeoCDWorldLoader loader = new NeoGeoCDWorldLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoCDWorldLoaderTest.class.getClassLoader().getResourceAsStream("sources/neogeocdworld.html");
		List<Game> games = new ArrayList<Game>();

		byte[] bytes = ByteStreams.toByteArray(stream);
		String html = new String(bytes);
		String[] splits = html.split("<tr>");
		for (String split : splits) {
			try {
				if (split.split("</td>").length >= 6) {
					Game game = loader.loadGameInfo(split);
					games.add(game);
					System.err.println(game + "\n");
				}
			} catch (Exception e) {
				// System.err.println(split);
			}
		}
		System.err.println(games.size());
	}

}
