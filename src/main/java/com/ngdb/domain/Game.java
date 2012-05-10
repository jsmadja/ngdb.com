package com.ngdb.domain;

import com.google.common.base.Objects;

public class Game implements BaseEntity {

	private Long id;

	private String ngh = "";

	private String ngcdJap = "";

	private String publisher = "";

	private String japaneseTitle = "";

	private String title = "";

	private Long megaCount = 0L;

	private String genre = "";

	private String aesDate = "";

	private String mvsDate = "";

	private String cdDate = "";

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setNgh(String ngh) {
		this.ngh = ngh;
	}

	public String getNgh() {
		return ngh;
	}

	public void setNgcdJap(String ngcdJap) {
		this.ngcdJap = ngcdJap;
	}

	public String getNgcdJap() {
		return ngcdJap;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setJapaneseTitle(String japaneseTitle) {
		this.japaneseTitle = japaneseTitle;
	}

	public String getJapaneseTitle() {
		return japaneseTitle;
	}

	public void setMegaCount(Long megaCount) {
		this.megaCount = megaCount;
	}

	public Long getMegaCount() {
		return megaCount;
	}

	@Override
	public String toString() {
		Objects.ToStringHelper h = Objects.toStringHelper(this);
		if (!ngh.isEmpty()) {
			h = h.add("NGH", ngh);
		}
		h = h.add("Title", title). //
				add("Publisher", publisher);
		if (megaCount != 0) {
			h = h.add("MegaCount", megaCount);
		}
		if (!ngcdJap.isEmpty())
			h = h.add("NGCD", ngcdJap);
		if (!japaneseTitle.isEmpty())
			h = h.add("Japanese Title", japaneseTitle);
		if (!genre.isEmpty())
			h = h.add("Genre", genre);
		if (!aesDate.isEmpty())
			h = h.add("AES", aesDate);
		if (!mvsDate.isEmpty())
			h = h.add("MVS", mvsDate);
		if (!cdDate.isEmpty())
			h = h.add("CD", cdDate);
		return h.toString();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}

	public String getAesDate() {
		return aesDate;
	}

	public void setAesDate(String aesDate) {
		this.aesDate = aesDate;
	}

	public void setMvsDate(String mvsDate) {
		this.mvsDate = mvsDate;
	}

	public String getMvsDate() {
		return mvsDate;
	}

	public void setCdDate(String cdDate) {
		this.cdDate = cdDate;
	}

	public String getCdDate() {
		return cdDate;
	}
}
