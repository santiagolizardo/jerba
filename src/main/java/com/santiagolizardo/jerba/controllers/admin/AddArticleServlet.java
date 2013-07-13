package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.managers.ArchiveStatsManager;
import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.TagManager;
import com.santiagolizardo.jerba.model.ArchiveStats;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Tag;
import com.santiagolizardo.jerba.utilities.PingService;
import com.santiagolizardo.jerba.utilities.RequestParam;
import com.santiagolizardo.jerba.utilities.StringUtils;

public class AddArticleServlet extends BaseServlet {

	private static final Logger LOGGER = Logger
			.getLogger(AddArticleServlet.class.getName());

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestParam input = new RequestParam(req);
		String type = req.getParameter("type");
		String title = req.getParameter("title");
		String keywords = req.getParameter("keywords");
		String description = req.getParameter("description");
		Text content = new Text(req.getParameter("content"));
		int order = input.getInteger("order");
		boolean visible = input.getBoolean("visible", true);
		long parentId = input.getLong("parentId", -1l);
		String pubDateParam = req.getParameter("pubDate");

		String sanitizedTitle = StringUtils.sanitize(title);

		Article article = new Article();
		if (parentId != -1) {
			Article parentArticle = ArticleManager.getInstance()
					.findByPrimaryKey(parentId);
			if (null != parentArticle) {
				article.setParent(parentArticle.getKey());
			}
		}
		article.setType(type.equals("P") ? ArticleType.Permanent
				: ArticleType.Ephemeral);
		article.setTitle(title);
		article.setSanitizedTitle(sanitizedTitle);
		article.setKeywords(keywords);
		article.setDescription(description);
		article.setContent(content);
		article.setPosition(order);
		article.setVisible(visible);

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");

		Date pubDate = new Date();
		if (pubDateParam != null && !pubDateParam.isEmpty()) {
			try {
				pubDate = dateFormat.parse(pubDateParam);
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
			}
		}

		article.setPublicationDate(pubDate);

		int year = pubDate.getYear() + 1900;
		int month = pubDate.getMonth() + 1;

		// Save transaction.

		PersistenceManager pm = PMF.get().getPersistenceManager();

		ArchiveStats archiveStats = new ArchiveStatsManager(pm)
				.findByYearMonth(year, month);
		if (archiveStats == null) {
			archiveStats = new ArchiveStats(year, month);
		}
		archiveStats.setCount(archiveStats.getCount() + 1);
		pm.makePersistent(archiveStats);

		String[] tokens = StringUtils.tokenize(keywords);
		TagManager tagManager = new TagManager(pm);
		tagManager.save(tokens);

		pm.makePersistent(article);
		pm.close();

		PingService.pingArticle(article);

		resp.sendRedirect("/admin/post/");
	}
}
