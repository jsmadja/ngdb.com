package com.ngdb.domain;

public enum Origin {

	Europe("EU"), America("US"), Japan("JP");

	private String title;

	private Origin(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
