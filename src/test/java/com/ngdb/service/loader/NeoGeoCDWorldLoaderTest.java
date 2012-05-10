package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.ngdb.domain.Game;

public class NeoGeoCDWorldLoaderTest {

	private static NeoGeoCDWorldLoader loader = new NeoGeoCDWorldLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoCDWorldLoaderTest.class.getClassLoader().getResourceAsStream("sources/neogeocdworld.html");
		List<Game> games = loader.load(stream);
		System.err.println(games.size());
	}

}
