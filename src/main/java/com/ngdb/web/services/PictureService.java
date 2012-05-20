package com.ngdb.web.services;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.ngdb.entities.Article;
import com.ngdb.entities.Picture;

public class PictureService {

	private static final String ROOT = "/ngdb/images/articles/";

	public Picture store(String url, Article article) throws NotCreatedException {
		try {
			String parentFolder = ROOT + article.getId() + "/";
			String picture = name() + "." + extension(url);
			File to = new File(new File(parentFolder), picture);
			createParentDirs(to);
			byte[] from = toByteArray(new URL(url).openStream());
			write(from, to);
			return new Picture(parentFolder + picture);
		} catch (MalformedURLException e) {
			throw new NotCreatedException(e);
		} catch (IOException e) {
			throw new NotCreatedException(e);
		}
	}

	private String name() {
		return UUID.randomUUID().toString();
	}

	private String extension(String url) {
		String extension = url.substring(url.lastIndexOf('.') + 1);
		return extension;
	}

}
