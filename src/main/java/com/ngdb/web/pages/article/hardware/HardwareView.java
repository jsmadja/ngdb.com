package com.ngdb.web.pages.article.hardware;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;

public class HardwareView {

	@Persist("entity")
	private Hardware hardware;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private String value;

	@Property
	private Note note;

	public void onActivate(Hardware hardware) {
		this.hardware = hardware;
	}

	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	public Hardware getHardware() {
		return hardware;
	}

}
