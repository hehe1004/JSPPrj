package com.newlectrue.web.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter("/*")
public class CharaterEncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain cahin)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("before filter");
		request.setCharacterEncoding("UTF-8");
		cahin.doFilter(request, response);
		System.out.println("after filter");
		
	}

}
