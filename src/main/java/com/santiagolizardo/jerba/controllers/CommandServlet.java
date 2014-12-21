package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;

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

