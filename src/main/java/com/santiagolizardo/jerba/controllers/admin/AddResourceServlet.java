package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Resource;

public class AddResourceServlet extends BaseServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String title = req.getParameter("title");

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		BlobKey blobKey = blobs.get("myFile").get(0);
		BlobInfo info = new BlobInfoFactory().loadBlobInfo(blobKey);

		if (blobKey == null) {
			resp.sendRedirect("/admin/resource/add.jsp?error");
			return;
		}

		Resource resource = new Resource();
		resource.setTitle(title);
		resource.setBlobKey(blobKey);
		resource.setContentType(info.getContentType());
		resource.setFileName(info.getFilename());
		resource.setSize(info.getSize());

		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(resource);
		pm.close();

		resp.sendRedirect("/admin/resource/");
	}
}
