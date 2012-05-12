package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.ngdb.domain.ExternalGame;

public class NeoGeoComLoaderTest {

	private NeoGeoComLoader loader = new NeoGeoComLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoComLoader.class.getClassLoader().getResourceAsStream("sources/neogeocom.html");
		List<ExternalGame> games = loader.load(stream);
		System.err.println(games.size());
	}

}
