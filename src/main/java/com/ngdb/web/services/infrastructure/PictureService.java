package com.ngdb.web.services.infrastructure;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;

public class PictureService {

	private static final String ROOT = "/ngdb/images/articles/";

	private static final Logger LOG = LoggerFactory.getLogger(PictureService.class);

	public Picture store(String url, Article article) {
		try {
			return createPictureFromUrl(url, article);
		} catch (MalformedURLException e) {
			LOG.warn("Cannot create picture with url '" + url + "' for article " + article.getId(), e);
		} catch (IOException e) {
			LOG.warn("Cannot create picture with url '" + url + "' for article " + article.getId(), e);
		}
		return Picture.EMPTY;
	}

	public Picture store(UploadedFile uploadedFile, Article article) {
		try {
			return createPictureFromUploadedFile(uploadedFile, article);
		} catch (NullPointerException e) {
			LOG.warn("Cannot create picture with name '" + uploadedFile.getFileName() + "' for article " + article.getId(), e);
		} catch (IOException e) {
			LOG.warn("Cannot create picture with name '" + uploadedFile.getFileName() + "' for article " + article.getId(), e);
		}
		return Picture.EMPTY;
	}

	private Picture createPictureFromUrl(String url, Article article) throws IOException, MalformedURLException {
		InputStream fromStream = new URL(url).openStream();
		String pictureName = name() + "." + extension(url);
		return createPicture(article, fromStream, pictureName);
	}

	private Picture createPictureFromUploadedFile(UploadedFile uploadedFile, Article article) throws IOException {
		InputStream fromStream = uploadedFile.getStream();
		String pictureName = name() + "." + extension(uploadedFile.getFileName());
		return createPicture(article, fromStream, pictureName);
	}

	private Picture createPicture(Article article, InputStream fromStream, String pictureName) throws IOException {
		String parentFolder = ROOT + article.getId() + "/";
		File to = new File(new File(parentFolder), pictureName);
		createParentDirs(to);
		byte[] from = toByteArray(fromStream);
		write(from, to);
		return new Picture(parentFolder + pictureName);
	}

	private String name() {
		return UUID.randomUUID().toString();
	}

	private String extension(String url) {
		return url.substring(url.lastIndexOf('.') + 1);
	}

}
