package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.utilities.GoogleRecaptcha;
import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

@SuppressWarnings("unchecked")
public class ContactServlet extends BaseServlet {

	private static final Logger LOGGER = Logger.getLogger(ContactServlet.class
			.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fromName = req.getParameter("fromName");
		String fromEmail = req.getParameter("fromEmail");
		String msgBody = req.getParameter("message");

		String recaptchaResponse = req.getParameter("g-recaptcha-response");

		if (null == fromName || null == fromEmail || null == msgBody
				|| null == recaptchaResponse) {
			LOGGER.warning("Invalid/missing params");
			sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String remoteAddr = req.getRemoteAddr();

		ConfigManager configManager = ConfigManager.getInstance();

		GoogleRecaptcha googleRecaptcha = new GoogleRecaptcha(configManager.getValue(ConfigManager.RECAPTCHA_PUBLIC_KEY),
				configManager.getValue(ConfigManager.RECAPTCHA_PRIVATE_KEY));

		if (!googleRecaptcha.isValid(recaptchaResponse, remoteAddr)) {
			resp.sendRedirect("/contact?error=invalid-token");
			return;
		} else {
			InternetAddress address = new InternetAddress(
					configManager.getValue(ConfigManager.ADMINISTRATOR_EMAIL),
					configManager.getValue(ConfigManager.ADMINISTRATOR_NAME));

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(address);
				msg.addRecipient(Message.RecipientType.TO, address);
				msg.setSubject("[Jerba] Message received");
				String header = String.format("From: %s (%s)%n%n", fromName,
						fromEmail);
				msg.setText(header + msgBody);
				Transport.send(msg);
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
				sendErrorResponse(resp);
				return;
			}
		}

		resp.sendRedirect("/contact");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String cacheKey = "page-contact";
		String output = null;
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			ConfigManager configManager = ConfigManager.getInstance();

			String siteKey = configManager
									.getValue(ConfigManager.RECAPTCHA_PUBLIC_KEY);

			VelocityContext context = prepareContext(req);
			context.put(
					"pageTitle",
					"Contact form and information"
							+ configManager
									.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX));
			context.put("siteKey", siteKey);
			output = generateTemplate("contact.vm", context);
			cache.put(cacheKey, output);
		}
		writeResponse(output, resp);
	}
}
