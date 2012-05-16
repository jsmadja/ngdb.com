package com.ngdb.domain;

public class Picture extends AbstractEntity {

	public static final Picture EMPTY = new Picture("http://cdn1.iconfinder.com/data/icons/realistiK-new/128x128/mimetypes/unknown.png", 128, 128);

	private String url;
	private int width;
	private int height;

	public Picture(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
