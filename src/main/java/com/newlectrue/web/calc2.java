package com.newlectrue.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class calc2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();

		Cookie[] cookies = request.getCookies();

		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");

		int v = 0;
		System.out.println(op);

		if (!v_.equals(""))	v = Integer.parseInt(v_);

		if (op.equals("=")) {

//			int x= (Integer)application.getAttribute("value");
//			int x= (Integer)session.getAttribute("value");
			int x = 0;

			for (Cookie c : cookies) {
				if (c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}

			int y = v;
//			String operato	r = (String)application.getAttribute("op");
//			String operator = (String) session.getAttribute("op");
		
			
			String operator =  "";
			for (Cookie c : cookies) 
				if (c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
				
				
			
			
			int result = 0;

			if (operator.equals("+"))
				result = x + y;
			else
				result = x - y;

			response.getWriter().printf("result id %d \n", result);
		} else {
//		application.setAttribute("value", v);
//		application.setAttribute("op", op);

//		session.setAttribute("value", v);
//		session.setAttribute("op", op);

		Cookie ValueCookie = new Cookie("value", String.valueOf(v));
		Cookie OpCookie = new Cookie("op", op);
		// add 경로 요청했을대만 쿠키 가져갈수있음 경로를 지정할수잇다
		ValueCookie.setPath("/calc2");
		ValueCookie.setMaxAge(24*60*60);
		OpCookie.setPath("/calc2");

		response.addCookie(ValueCookie);
		response.addCookie(OpCookie);
		
		response.sendRedirect("calc2.html");

		}

	}

}
