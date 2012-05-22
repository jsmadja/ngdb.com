package com.ngdb.service.loader;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.ngdb.entities.ExternalGame;

public class NeoGeoComLoaderTest {

	private NeoGeoComLoader loader = new NeoGeoComLoader();

	@Test
	public void main() throws Exception {
		InputStream stream = NeoGeoComLoader.class.getClassLoader().getResourceAsStream("sources/neogeocom.html");
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			System.err.println(externalGame);
		}
		System.err.println(games.size());
	}

	@Test
	public void t() {
		String extract = Loaders.extract("<td align=\"center\" width=\"49\" height=\"15\"><font size=\"2\">13.22.96</font>", ">([0-9.]+)<");
		assertEquals("13.22.96", extract);
	}

	@Test
	public void t2() {
		String extract = Loaders.extract("<td align=\"center\" width=\"64\" height=\"15\" bgcolor=\"#00FF00\"> <font size=\"2\">3.18.04</font>", ">([0-9.]+)<");
		assertEquals("3.18.04", extract);
	}

}
