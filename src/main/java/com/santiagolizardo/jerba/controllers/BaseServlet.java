package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.HtmlUtils;
import com.santiagolizardo.jerba.utilities.UrlFactory;
import com.santiagolizardo.jerba.utilities.WebUtils;
import com.santiagolizardo.jerba.utilities.templates.DatastoreResourceLoader;

public abstract class BaseServlet extends Command {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}
}
