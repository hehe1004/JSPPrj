package com.newlectrue.web.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlectrue.web.entity.Notice;
import com.newlectrue.web.service.NoticeService;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("notice detail1");
		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id);
		request.setAttribute("n", notice);
		
		System.out.println("notice detail2");
		//forward
		//작업내용 이어받아서
		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
	}
}


