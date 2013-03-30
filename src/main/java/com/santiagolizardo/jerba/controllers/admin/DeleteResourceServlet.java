package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Resource;
import com.santiagolizardo.jerba.utilities.RequestParam;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class DeleteResourceServlet extends BaseServlet {

	private static final Logger LOGGER = Logger
			.getLogger(DeleteResourceServlet.class.getName());

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = new RequestParam(req).getLong("id");
		if (null == id) {
			resp.sendError(500);
			return;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Resource resource = pm.getObjectById(Resource.class, id);
			BlobKey blobKey = resource.getBlobKey();
			pm.deletePersistent(resource);

			BlobstoreService service = BlobstoreServiceFactory
					.getBlobstoreService();
			service.delete(blobKey);
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		} finally {
			pm.close();
		}

		resp.sendRedirect("/admin/resource/");
	}
}
