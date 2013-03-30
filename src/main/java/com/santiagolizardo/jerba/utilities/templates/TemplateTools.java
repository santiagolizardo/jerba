package com.santiagolizardo.jerba.utilities.templates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateTools {

	public String firstParagraph(String string) {
		Pattern pattern = Pattern.compile("<p>(.*?)</p>",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			return matcher.group(1);
		} else
			return "";
	}
}
