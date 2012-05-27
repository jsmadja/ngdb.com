package com.ngdb.service.loader;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;


public class NeoMasterListLoaderTest {

	@Test
	public void main() throws Exception {
		InputStream stream = NeoMasterListLoaderTest.class.getClassLoader().getResourceAsStream("sources/neomasterlist.csv");
		NeoMasterListLoader loader = new NeoMasterListLoader();
		List<ExternalGame> games = loader.findAll(stream);
		ExternalGame game = games.get(0);
		assertEquals("1", game.getNgh());
		assertEquals("Nam-1975", game.getTitle());
		assertEquals("ナム-1975", game.getJapaneseTitle());
	}
}
