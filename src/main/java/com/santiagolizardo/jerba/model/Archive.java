package com.santiagolizardo.jerba.model;

import java.util.List;

public class Archive {

	private int year;
	private int month;
	private List<Article> articles;

	public void setDate(int month, int year) {
		this.month = month;
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
