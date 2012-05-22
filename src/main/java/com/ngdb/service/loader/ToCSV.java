package com.ngdb.service.loader;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ngdb.entities.ExternalGame;

public class ToCSV {

	static FileOutputStream aes, mvs, cd, japanese;
	static {
		try {
			aes = new FileOutputStream("/tmp/aes.csv");
			mvs = new FileOutputStream("/tmp/mvs.csv");
			cd = new FileOutputStream("/tmp/cd.csv");
			japanese = new FileOutputStream("/tmp/japanese.csv");
			aes.write(("title;publisher;megacount;ngh;releasedate;genre;origin;url;source;platform\n").getBytes());
			mvs.write(("title;publisher;megacount;ngh;releasedate;genre;origin;url;source;platform\n").getBytes());
			cd.write(("title;publisher;megacount;ngh;releasedate;genre;origin;url;source;platform\n").getBytes());
			japanese.write(("title;publisher;megacount;ngh;releasedate;genre;origin;url;source;platform;japanesetitle\n").getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		ngcd();
		museum();
		neogeocom();
		masterlist();
	}

	private static void masterlist() throws IOException {
		InputStream stream = ToCSV.class.getClassLoader().getResourceAsStream("sources/neomasterlist.csv");
		NeoMasterListLoader loader = new NeoMasterListLoader();
		List<ExternalGame> games = loader.findAll(stream);
		for (ExternalGame game : games) {
			japanese.write((game.getTitle() + ";;;" + game.getNgh().toString() + ";;;;;;;" + game.getJapaneseTitle() + ";\n").getBytes());
		}
	}

	private static void ngcd() throws FileNotFoundException, IOException {
		NeoGeoCDWorldLoader loader = new NeoGeoCDWorldLoader();
		InputStream stream = ToCSV.class.getClassLoader().getResourceAsStream("sources/neogeocdworld.html");
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			String line = word(externalGame.getTitle()) + ";" + word(externalGame.getPublisher()) + ";" + externalGame.getMegaCount() + ";" + externalGame.getNgcdJap() + ";;;;;neogeocdworld;cd";
			cd.write((line + "\n").getBytes());
		}
	}

	private static String word(String word) {
		word = word.toLowerCase();
		System.err.print(word + " ->");
		word = word.replaceAll("  ", " ");
		word = StringUtils.capitaliseAllWords(word);
		word = word.replaceAll("Vs.", "VS");
		word = word.replaceAll("IIi", "III");
		word = word.replaceAll("Ii", "II");
		if (word.startsWith("The ")) {
			word = StringUtils.remove(word, "The ");
			word += ", The";
		}
		System.err.println(word);
		return word;
	}

	private static void museum() throws FileNotFoundException, IOException {
		InputStream stream = ToCSV.class.getClassLoader().getResourceAsStream("sources/neogeomuseum.html");
		NeoGeoMuseumLoader loader = new NeoGeoMuseumLoader();
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			String line = word(externalGame.getTitle()) + ";" + word(externalGame.getPublisher()) + ";;;";
			if (isNotBlank(externalGame.getAesDate())) {
				aes.write((line + externalGame.getAesDate() + ";" + word(externalGame.getGenre()) + ";;;museum;aes\n").getBytes());
			}
			if (isNotBlank(externalGame.getMvsDate())) {
				mvs.write((line + externalGame.getMvsDate() + ";" + word(externalGame.getGenre()) + ";;;museum;mvs\n").getBytes());
			}
			if (isNotBlank(externalGame.getCdDate())) {
				cd.write((line + externalGame.getCdDate() + ";" + word(externalGame.getGenre()) + ";;;museum;cd\n").getBytes());
			}
		}
	}

	private static void neogeocom() throws FileNotFoundException, IOException {
		InputStream stream = ToCSV.class.getClassLoader().getResourceAsStream("sources/neogeocom.html");
		NeoGeoComLoader loader = new NeoGeoComLoader();
		List<ExternalGame> games = loader.load(stream);
		for (ExternalGame externalGame : games) {
			String line = word(externalGame.getTitle()) + ";" + word(externalGame.getPublisher()) + ";" + externalGame.getMegaCount() + ";" + externalGame.getNgh() + ";";
			if (isNotBlank(externalGame.getAesDate())) {
				aes.write((line + externalGame.getAesDate() + ";;;;neogeocom;aes\n").getBytes());
			}
			if (isNotBlank(externalGame.getCdDate())) {
				cd.write((line + externalGame.getCdDate() + ";;;;neogeocom;cd\n").getBytes());
			}
		}
	}

}
