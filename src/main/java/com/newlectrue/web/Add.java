package com.newlectrue.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String x_ = request.getParameter("x");
		String y_ = request.getParameter("y");
		
		int x = 0;
		int y = 0;
	
		
		
		if(!x_.equals("")) x =Integer.parseInt(x_);
		if(!y_.equals("")) y =Integer.parseInt(y_);
		
		int result = x+y;
		
		response.getWriter().printf("result id %d \n", result);

	}

}
