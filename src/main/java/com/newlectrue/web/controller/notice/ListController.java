package com.newlectrue.web.controller.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlectrue.web.entity.Notice;
import com.newlectrue.web.entity.NoticeView;
import com.newlectrue.web.service.NoticeService;

@WebServlet("/notice/list")
public class ListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//list?f=title&q=a
		
		
		String field_ = request.getParameter("f");
		String query_ =request.getParameter("q");
		String page_ = request.getParameter("p");
		
		System.out.println(field_);
		System.out.println(query_);
		System.out.println(page_);
		
		String field = "title";
		if(field_ !=null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ !=null && !query_.equals(""))
			query = query_;
		
		int page = 1;
		if(page_ !=null&& !page_.equals(""))
			page =Integer.parseInt(page_);
		
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field,query,page);
	
		int count = service.getNoticeCount(field,query);
		
		//redirect
		//그냥 바로 다른페이지로 가기 
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		//forward
		//작업내용 이어받아서
		request
		.getRequestDispatcher("/WEB-INF/view/notice/list.jsp")
		.forward(request, response);
		
		
	}
}