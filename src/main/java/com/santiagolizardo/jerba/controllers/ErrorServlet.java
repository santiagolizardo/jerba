package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ErrorServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int errorCode = HttpServletResponse.SC_NOT_FOUND;
		String errorCodeString = req.getParameter("code");
		if (null != errorCodeString) {
			try {
				errorCode = Integer.parseInt(errorCodeString);
			} catch (NumberFormatException e) {
				// Ignore the exception.
			}
		}

		sendErrorResponse(resp, errorCode);
	}
}
