package com.santiagolizardo.jerba.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.Article;

public class PingService {

	private static final Logger LOGGER = Logger.getLogger(PingService.class
			.getName());

	private static ConfigManager configManager;

	static {
		configManager = ConfigManager.getInstance();
	}

	public static void pingArticle(Article article) {
		String blogTitle = configManager.getValue(ConfigManager.WEBSITE_NAME)
				+ configManager.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX);
		String blogURL = configManager.getValue(ConfigManager.WEBSITE_URL);
		String feedURL = blogURL + "/articles.xml";
		String articleURL = blogURL + UrlFactory.getInstance().createPostUrl(article);

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<methodCall><methodName>weblogUpdates.extendedPing</methodName>");
		sb.append("<params>");
		sb.append(String.format("<param><value>%s</value></param>", blogTitle));
		sb.append(String.format("<param><value>%s</value></param>", blogURL));
		sb.append(String.format("<param><value>%s</value></param>", articleURL));
		sb.append(String.format("<param><value>%s</value></param>", feedURL));
		sb.append("</params>");
		sb.append("</methodCall>");

		try {
			URL url = new URL("http://rpc.pingomatic.com");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(sb.toString());
			writer.flush();
			writer.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = connection.getInputStream();
				String output = StreamUtils.convertStreamToString(is);
				LOGGER.info(output);
			} else {
				LOGGER.warning("Response code: " + connection.getResponseCode());
			}
		} catch (MalformedURLException e) {
			LOGGER.severe(e.getMessage());
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}
