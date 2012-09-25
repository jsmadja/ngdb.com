package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.shop.ShopItem;
import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;
import static java.util.UUID.randomUUID;

public class PictureService {

	private static final String ARTICLE_ROOT = "/ngdb/images/articles/";
	private static final String SHOP_ITEM_ROOT = "/ngdb/images/shop-items/";

	private static final Logger LOG = LoggerFactory.getLogger(PictureService.class);

	public Picture store(UploadedFile uploadedFile, Article article) {
		try {
			Picture picture = createPictureFromUploadedFile(uploadedFile, article);
			picture.setArticle(article);
			return picture;
		} catch (IOException e) {
			LOG.warn("Cannot create picture with name '" + uploadedFile.getFileName() + "' for article " + article.getId(), e);
		}
		return Picture.EMPTY;
	}

	public Picture store(UploadedFile uploadedFile, ShopItem shopItem) {
		try {
			Picture picture = createPictureFromUploadedFile(uploadedFile, shopItem);
			picture.setShopItem(shopItem);
			return picture;
		} catch (IOException e) {
			LOG.warn("Cannot create picture with name '" + uploadedFile.getFileName() + "' for article " + shopItem.getId(), e);
		}
		return Picture.EMPTY;
	}

	private Picture createPictureFromUploadedFile(UploadedFile uploadedFile, Article article) throws IOException {
		InputStream fromStream = uploadedFile.getStream();
		String pictureName = name() + "." + extension(uploadedFile.getFileName());
		return createPicture(article, fromStream, pictureName);
	}

	private Picture createPictureFromUploadedFile(UploadedFile uploadedFile, ShopItem shopItem) throws IOException {
		InputStream fromStream = uploadedFile.getStream();
		String pictureName = name() + "." + extension(uploadedFile.getFileName());
		return createPicture(shopItem, fromStream, pictureName);
	}

	private Picture createPicture(Article article, InputStream fromStream, String pictureName) throws IOException {
		String parentFolder = ARTICLE_ROOT + article.getId() + "/";
		return createPicture(fromStream, pictureName, parentFolder);
	}

	private Picture createPicture(ShopItem shopItem, InputStream fromStream, String pictureName) throws IOException {
		String parentFolder = SHOP_ITEM_ROOT + shopItem.getId() + "/";
		return createPicture(fromStream, pictureName, parentFolder);
	}

	private Picture createPicture(InputStream fromStream, String pictureName, String parentFolder) throws IOException {
		File to = new File(new File(parentFolder), pictureName);
		createParentDirs(to);
		byte[] from = toByteArray(fromStream);
		write(from, to);
		return new Picture(parentFolder + pictureName);
	}

	private String name() {
		return randomUUID().toString();
	}

	private String extension(String url) {
		return url.substring(url.lastIndexOf('.') + 1);
	}

	public void delete(Picture picture) {
        try {
            deletePicture(picture, "small");
            deletePicture(picture, "medium");
            deletePicture(picture, "high");
            File file = new File(picture.getOriginalUrl());
            if(file.exists()) {
                FileUtils.forceDelete(file);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deletePicture(Picture picture, String size) throws IOException {
        File file = new File(picture.getUrl(size));
        if(file.exists()) {
            FileUtils.forceDelete(file);
        }
        File parent = file.getParentFile();
        if(parent != null && parent.listFiles() != null && parent.listFiles().length == 0) {
            FileUtils.forceDelete(parent);
        }
    }

}
