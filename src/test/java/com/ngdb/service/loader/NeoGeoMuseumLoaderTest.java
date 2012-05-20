package com.ngdb.service.loader;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.ngdb.entities.ExternalGame;

public class NeoGeoMuseumLoaderTest {

	private NeoGeoMuseumLoader loader = new NeoGeoMuseumLoader();
	private List<ExternalGame> list;

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoMuseumLoader.class.getClassLoader().getResourceAsStream("sources/neogeomuseum.html");
		list = loader.load(stream);
		for (ExternalGame game : list) {
			System.err.println(game);
		}
	}

}
