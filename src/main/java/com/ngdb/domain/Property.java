package com.ngdb.domain;

public class Property {

	private String key;
	private String[] values;

	public Property(String key, String... values) {
		this.key = key;
		this.values = values;
	}

	public String getKey() {
		return key;
	}

	public String[] getValues() {
		return values;
	}

}
