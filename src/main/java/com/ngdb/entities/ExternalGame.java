package com.ngdb.entities;

import java.util.Date;

import com.google.common.base.Objects;

public class ExternalGame implements Comparable<ExternalGame> {

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

	private boolean fromNgc;
	private boolean fromNgcd;
	private boolean fromNgm;

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
		this.publisher = publisher.toUpperCase();
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
		h = h.add("Title", title);
		if (!ngh.isEmpty())
			h = h.add("NGH", ngh);
		if (publisher != null)
			h = h.add("Publisher", publisher);
		if (megaCount != 0)
			h = h.add("MegaCount", megaCount);
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
		if (fromNgc) {
			h = h.addValue("neo-geo.com");
		}
		if (fromNgcd) {
			h = h.addValue("neogeocdworld");
		}
		if (fromNgm) {
			h = h.addValue("snk-museum");
		}
		return h.toString();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setGenre(String genre) {
		this.genre = genre.toUpperCase();
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

	public void setFromNgc(boolean fromNgc) {
		this.fromNgc = fromNgc;
	}

	public void setFromNgcd(boolean fromNgcd) {
		this.fromNgcd = fromNgcd;
	}

	public void setFromNgm(boolean fromNgm) {
		this.fromNgm = fromNgm;
	}

	@Override
	public int compareTo(ExternalGame g) {
		String title1 = title.toLowerCase();
		String title2 = g.title.toLowerCase();
		return title1.compareTo(title2);
	}

	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
