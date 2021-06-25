package com.newlectrue.web.controller;

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
import com.newlectrue.web.service.NoticeService;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		NoticeService service = new NoticeService();

		List<Notice> list = service.getNoticeList();
	
		
		//redirect
		//그냥 바로 다른페이지로 가기 
		
		request.setAttribute("list", list);
		
		//forward
		//작업내용 이어받아서
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
		
		
	}
}