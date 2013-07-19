package com.santiagolizardo.jerba.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ArticleTestCase {

	@Test
	public void testDefaults() {
		Article article = new Article();
		assertTrue(article.isVisible());
		assertEquals(0,article.getPosition());
	}

	@Test
	public void testGetTitle() {
		String title = "Something great is going to happen!";
		Article article = new Article();
		article.setTitle(title);
		assertEquals(title, article.getTitle());
	}
}
