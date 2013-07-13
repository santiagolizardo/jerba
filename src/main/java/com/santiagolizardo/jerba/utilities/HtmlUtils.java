package com.santiagolizardo.jerba.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {

	public String extractFirstParagraph(String string) {
		Pattern pattern = Pattern.compile("<p>(.*?)</p>",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			return matcher.group(1);
		} else
			return "";
	}
}
