package com.newlectrue.web.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlectrue.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {

		return getNoticeList("title", "", 1);

	}

	public List<Notice> getNoticeList(int page) {

		return getNoticeList("title", "", page);

	}

	public List<Notice> getNoticeList(String field/*TITLE,WRITER_ID*/, String query/*A*/, int page) {
		
		List<Notice> list = new ArrayList<>();

			String sql = "select * from("
					+ "select ROWNUM NUM, N.* "
					+ "from (select * from NOTICE where "+field+" LIKE ? order by REGDATE DESC) N"
					+ ")"
					+ "where num between ? and ?";
			//1,11,21,31, -> an = 1+(page-a)*10
			//10,20,30,40 -> page*10
			

				String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

			
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection(url, "newlec", "1234");
					
					PreparedStatement st = con.prepareStatement(sql);
					st.setString(1, "%"+query+"%");
					st.setInt(2, 1+(page-1)*10);
					st.setInt(3, page*10);
					
//					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery();
					
					
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

		return list;

	}

	public int getNoticeCount() {

		return getNoticeCount("title","");

	}

	public int getNoticeCount(String field, String query) {

		
		int count=0;
		
		List<Notice> list = new ArrayList<>();

		String sql = "select COUNT(ID) COUNT from("
				+ "select ROWNUM NUM, N.* "
				+ "from (select * from NOTICE where "+field+" LIKE ? order by REGDATE DESC) N"
				+ ")";
				
		//1,11,21,31, -> an = 1+(page-a)*10
		//10,20,30,40 -> page*10
		

			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

		
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "newlec", "1234");
				
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, "%"+query+"%");
				
//				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery();
				
				if(rs.next())
					count = rs.getInt("count");
				

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

		return count;

	}

	public Notice getNotice(int id) {
		Notice notice = null;
		
			String sql = "select * from notice where id =?";

				String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

			
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection(url, "newlec", "1234");
					
					PreparedStatement st = con.prepareStatement(sql);
					st.setInt(1, id);
					
//					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery();
					
					
					if (rs.next()) {
						int nid = rs.getInt("ID");
						String title = rs.getString("TITLE");
						Date regdate = rs.getDate("REGDATE");
						String writerId = rs.getString("WRITER_ID");
						String hit = rs.getString("HIT");
						String files = rs.getString("FILES");
						String content = rs.getString("CONTENT");

						notice = new Notice(nid, title, regdate, writerId, hit, files, content);

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
				
				
		return notice;
	}

	public Notice getPreNotice(int id) {
		
		Notice notice = null;
		
			String sql = "select * from notice "
					+ "where id = ("
					+ "select id from notice "
					+ "where regdate > (select regdate from notice where id =?)"
					+ "and rownum = 1"
					+ ")";
			

			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

		
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection(url, "newlec", "1234");
				
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				
//				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery();
				
				
				if (rs.next()) {
					int nid = rs.getInt("ID");
					String title = rs.getString("TITLE");
					Date regdate = rs.getDate("REGDATE");
					String writerId = rs.getString("WRITER_ID");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
					String content = rs.getString("CONTENT");

					notice = new Notice(nid, title, regdate, writerId, hit, files, content);

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
			
			
	return notice;
	
	}

	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		
		String sql = "select id from(select * from notice order by regdate desc)"
				+ "where regdate < (select regdate from notice where id =?)"
				+ "and rownum = 1";
		

		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			
//			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery();
			
			
			if (rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");

				notice = new Notice(nid, title, regdate, writerId, hit, files, content);

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
		
		
return notice;

		

	}

}
