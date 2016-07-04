package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.santiagolizardo.jerba.managers.ResourceManager;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Resource;

public class ResourceServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Pattern pattern = Pattern.compile("^/r/[^_]+_(\\d+)");
		String requestUri = req.getRequestURI();
		Matcher matcher = pattern.matcher(requestUri);

		if (matcher.find()) {
			String idString = matcher.group(1);
			Long id = Long.parseLong(idString);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Resource resource = new ResourceManager(pm).findById(id);
			pm.close();

			res.setContentType(resource.getContentType());
			res.setContentLength(resource.getSize().intValue());

			BlobKey blobKey = resource.getBlobKey();
			blobstoreService.serve(blobKey, res);
		}
	}
}
