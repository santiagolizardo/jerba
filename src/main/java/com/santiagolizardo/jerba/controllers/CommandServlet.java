package com.santiagolizardo.jerba.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class CommandServlet extends HttpServlet {

	protected Command command;

	@Override
	public void init() throws ServletException {
		super.init();

		command = new Command();
		command.init();
	}
}

