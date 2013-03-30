package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Resource;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class AddResourceServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

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
		}

		Resource resource = new Resource();
		resource.setTitle(title);
		resource.setBlobKey(blobKey);
		resource.setContentType(info.getContentType());
		resource.setFileName(info.getFilename());
		resource.setSize(info.getSize());
		PMF.save(resource);

		resp.sendRedirect("/admin/resource/");
	}
}
