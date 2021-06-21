package com.newlectrue.web;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc3")
public class calc3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();

		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");

		String exp = "";

		if (cookies != null)
			for (Cookie c : cookies)
				if (c.getName().equals("exp")) {
					System.out.println("if2");
					exp = c.getValue();
					break;
				}

		if (operator != null && operator.equals("=")) {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			System.out.println("if3");
			try {
				exp = String.valueOf(engine.eval(exp));
				System.out.println(exp);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (operator != null && operator.equals("C")) {
					exp = "";
			System.out.println("else if");
		} else {

			exp += (value == null) ? "" : value;
			exp += (operator == null) ? "" : operator;
			exp += (dot == null) ? "" : dot;

			System.out.println("else");
		}

		Cookie expCookie = new Cookie("exp", exp);

		if (operator != null && operator.equals("C"))
			expCookie.setMaxAge(0);
		System.out.println("c2");
		
		expCookie.setPath("/");
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");

	}
}
