package com.santiagolizardo.jerba.model;

import java.util.Date;

public class SearchResult {

	private long id;
	private String title;
	private String description;
	private String content;
	private String keywords;
	private Date publicationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Override
	public String toString() {
		return "SearchResult [title=" + title + ", description=" + description
				+ ", content=" + content + ", keywords=" + keywords
				+ ", publicationDate=" + publicationDate + "]";
	}
}
