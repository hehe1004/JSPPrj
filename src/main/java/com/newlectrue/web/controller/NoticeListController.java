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

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "SELECT* FROM NOTICE";

		List<Notice> list = new ArrayList<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");

				Notice notice = new Notice(id, title, regdate, writerId, hit, files, content);

				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		//redirect
		//그냥 바로 다른페이지로 가기 
		
		request.setAttribute("list", list);
		
		//forward
		//작업내용 이어받아서
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
		
		
	}
}