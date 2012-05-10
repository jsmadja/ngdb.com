package com.ngdb.service.loader;

import java.io.InputStream;

import org.junit.Test;

public class NeoGeoMuseumLoaderTest {

	private NeoGeoMuseumLoader loader = new NeoGeoMuseumLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoMuseumLoader.class.getClassLoader().getResourceAsStream("sources/neogeomuseum.html");
		loader.load(stream);
	}

}
