package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends Command {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {}
}
