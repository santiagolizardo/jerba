package com.santiagolizardo.jerba.utilities;

import junit.framework.TestCase;

public class HtmlUtilsTestCase extends TestCase {

	public void testFirstParagraphExtraction() {
		String html = "<p>First para</p><p>Second para</p>";
		assertEquals("First para", new HtmlUtils().extractFirstParagraph(html));
	}
}
