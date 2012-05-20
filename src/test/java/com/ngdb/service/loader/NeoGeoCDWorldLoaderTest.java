package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.ngdb.entities.ExternalGame;

public class NeoGeoCDWorldLoaderTest {

	private static NeoGeoCDWorldLoader loader = new NeoGeoCDWorldLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoCDWorldLoaderTest.class.getClassLoader().getResourceAsStream("sources/neogeocdworld.html");
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			System.err.println(externalGame);
		}
		System.err.println(games.size());
	}

}
