package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.santiagolizardo.jerba.controllers.admin.AddArticleServlet;
import com.santiagolizardo.jerba.controllers.admin.AddResourceServlet;
import com.santiagolizardo.jerba.controllers.admin.AddTemplateServlet;
import com.santiagolizardo.jerba.controllers.admin.AddConfigServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteArticleServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteResourceServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteTemplateServlet;
import com.santiagolizardo.jerba.controllers.admin.EditConfigServlet;
import com.santiagolizardo.jerba.controllers.admin.UpdateArticleServlet;
import com.santiagolizardo.jerba.controllers.admin.UpdateTemplateServlet;

public class FrontControllerFilter implements Filter {

	private static final Logger logger = Logger.getLogger(FrontControllerFilter.class.getName());

	private Map<Pattern, Class<? extends BaseServlet>> routePatterns;

	private Map<String, String> redirections;

	private boolean appspotDomainAllowed = true;

	public void init(FilterConfig filterConfig) throws ServletException {
		initRedirections(filterConfig);
		initRoutes();
	}

	private void initRedirections(FilterConfig filterConfig) {
		redirections = new LinkedHashMap<>();

		InputStream is = filterConfig.getServletContext().getResourceAsStream("/WEB-INF/jerba-config.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			try {
				appspotDomainAllowed = Boolean.parseBoolean(
						doc.getDocumentElement().getAttributes().getNamedItem("appspot-domain-allowed").getNodeValue());
			} catch (Exception e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			NodeList redirectionNodes = doc.getElementsByTagName("redirection");
			for (int i = 0; i < redirectionNodes.getLength(); i++) {
				Node node = redirectionNodes.item(i);
				String pattern = node.getAttributes().getNamedItem("pattern").getNodeValue();
				String url = node.getAttributes().getNamedItem("url").getNodeValue();
				redirections.put(pattern, url);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void initRoutes() {
		Map<String, Class<? extends BaseServlet>> routes = new LinkedHashMap<>();

		// Public pages
		routes.put("/contact", ContactServlet.class);
		routes.put("/search", SearchServlet.class);
		routes.put("/search/index-update", SearchIndexUpdateServlet.class);
		routes.put("/sitemap", SitemapHtmlServlet.class);
		routes.put("/template", TemplateServlet.class);
		routes.put("/archive", ArchiveServlet.class);
		routes.put("/upload", AddResourceServlet.class);
		routes.put("/contact", ContactServlet.class);
		routes.put("/articles.xml", XmlFeedServlet.class);
		routes.put("/robots.txt", RobotsTxtServlet.class);
		routes.put("/sitemap.xml", SitemapXmlServlet.class);
		routes.put("/home", IndexServlet.class);
		routes.put("/article/.*", ArticleServlet.class);
		routes.put("/page/.*", ArticleServlet.class);
		routes.put("/r/.*", ResourceServlet.class);

		// Admin panel
		routes.put("/AddTemplate", AddTemplateServlet.class);
		routes.put("/UpdateTemplate", UpdateTemplateServlet.class);
		routes.put("/DeleteTemplate", DeleteTemplateServlet.class);
		routes.put("/DeleteResource", DeleteResourceServlet.class);
		routes.put("/AddArticle", AddArticleServlet.class);
		routes.put("/UpdateArticle", UpdateArticleServlet.class);
		routes.put("/DeleteArticle", DeleteArticleServlet.class);
		routes.put("/AddConfig", AddConfigServlet.class);
		routes.put("/EditConfig", EditConfigServlet.class);
		routes.put("/Article", ArticleServlet.class);
		routes.put("/", IndexServlet.class);

		routePatterns = new LinkedHashMap<>();
		// Compile regexes at init time (only once) and not for every request.
		for (String route : routes.keySet()) {
			Pattern regexp = Pattern.compile(route);
			routePatterns.put(regexp, routes.get(route));
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (appspotDomainAllowed == false && req.getServerName().contains(".appspot.com")) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (true == processRedirections(req, resp)) {
			return;
		}

		for (Pattern routePattern : routePatterns.keySet()) {
			if (routePattern.matcher(req.getRequestURI()).matches()) {
				Class<? extends BaseServlet> clazz = routePatterns.get(routePattern);
				processRequest(clazz, req, resp);
				return;
			}
		}

		if (req.getRequestURI().startsWith("/admin") || req.getRequestURI().startsWith("/_ah") || req.getRequestURI().startsWith("/.well-known")) {
			chain.doFilter(request, response);
		} else {
			req.getRequestDispatcher("/error-page?code=" + HttpServletResponse.SC_NOT_FOUND).forward(req, resp);
		}
	}

	private void processRequest(Class<? extends BaseServlet> clazz, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BaseServlet httpServlet = clazz.getDeclaredConstructor().newInstance();
			httpServlet.init();
			if ("GET".equals(req.getMethod())) {
				httpServlet.doGet(req, resp);
			} else {
				httpServlet.doPost(req, resp);
			}
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	private boolean processRedirections(HttpServletRequest req, HttpServletResponse resp) {
		String requestUri = req.getRequestURI();

		for (String url : redirections.keySet()) {
			Pattern pattern = Pattern.compile(url);
			if (pattern.matcher(requestUri).matches()) {
				resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				resp.setHeader("Location", redirections.get(url));
				return true;
			}
		}

		return false;
	}

	public void destroy() {
	}
}
