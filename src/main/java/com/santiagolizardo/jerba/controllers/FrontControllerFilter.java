package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.Enumeration;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
import com.santiagolizardo.jerba.controllers.admin.ConfigServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteArticleServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteResourceServlet;
import com.santiagolizardo.jerba.controllers.admin.DeleteTemplateServlet;
import com.santiagolizardo.jerba.controllers.admin.UpdateArticleServlet;
import com.santiagolizardo.jerba.controllers.admin.UpdateTemplateServlet;

public class FrontControllerFilter implements Filter {

	private Map<String, Class<? extends BaseServlet>> routes;
	private Map<String, String> redirections;

	public void init(FilterConfig filterConfig) throws ServletException {
		initRedirections( filterConfig );
		initRoutes();
	}

	private void initRedirections( FilterConfig filterConfig ) {
		redirections = new LinkedHashMap<String, String>();

		InputStream is = filterConfig.getServletContext().getResourceAsStream(
				"/WEB-INF/jerba-config.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			NodeList redirectionNodes = doc.getElementsByTagName("redirection");
			for (int i = 0; i < redirectionNodes.getLength(); i++) {
				Node node = redirectionNodes.item(i);
				String pattern = node.getAttributes().getNamedItem("pattern").getNodeValue();
				String url = node.getAttributes().getNamedItem("url").getNodeValue();
				redirections.put( pattern, url );
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initRoutes() {
		routes = new LinkedHashMap<String, Class<? extends BaseServlet>>();

		// Public pages
		routes.put("/migrate", MigrationServlet.class);
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
		routes.put("/UpdateTemplate", UpdateTemplateServlet.class);
		routes.put("/AddTemplate", AddTemplateServlet.class);
		routes.put("/DeleteResource", DeleteResourceServlet.class);
		routes.put("/DeleteTemplate", DeleteTemplateServlet.class);
		routes.put("/DeleteArticle", DeleteArticleServlet.class);
		routes.put("/UpdateArticle", UpdateArticleServlet.class);
		routes.put("/AddArticle", AddArticleServlet.class);
		routes.put("/Config", ConfigServlet.class);
		routes.put("/Article", ArticleServlet.class);
		routes.put("/", IndexServlet.class);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		         HttpServletRequest req = (HttpServletRequest) request;
		         HttpServletResponse resp = (HttpServletResponse) response;
			
		if (true == processRedirections(req, resp)) {
			return;
		}

		for (String route : routes.keySet()) {
			Pattern regexp = Pattern.compile(route);
			if (regexp.matcher(req.getRequestURI()).matches()) {
				Class<? extends BaseServlet> clazz = routes.get(route);
				processRequest(clazz, req, resp);
				return;
			}
		}

		if( req.getRequestURI().startsWith( "/admin" ) ) 
			chain.doFilter( request, response );
		else
			req.getRequestDispatcher("/error-page?code=404").forward(req, resp);
	}

	private void processRequest(Class<? extends BaseServlet> clazz,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BaseServlet httpServlet = (BaseServlet) clazz.newInstance();
			httpServlet.init();
			if ("GET".equals(req.getMethod())) {
				httpServlet.doGet(req, resp);
			} else {
				httpServlet.doPost(req, resp);
			}
		} catch (IllegalAccessException iae) {
			System.err.println(iae.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	private boolean processRedirections(HttpServletRequest req,
			HttpServletResponse resp) {
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

	public void destroy() {}
}
