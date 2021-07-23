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
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlectrue.web.entity.Notice;
import com.newlectrue.web.entity.NoticeView;

public class NoticeService {
	
	public int removeNoticeAll(int[] ids){
		
		return 0;
		
	}
	public int pubNoticeAll(int[] oids, int[] cids){
		
		List<String> oidsList = new ArrayList<>();
		for(int i =0; i<oids.length; i++)
			oidsList.add(String.valueOf(oids[i]));
		
		
		List<String> cidsList = new ArrayList<>();
		for(int i =0; i<cids.length; i++)
			cidsList.add(String.valueOf(cids[i]));
			
		

		
		
		return pubNoticeAll(oidsList, cidsList);
		
		
	}
	
	public int pubNoticeAll(List<String> oids, List<String> cids){
		
		
		String oidsCSV = String.join(",", oids);
		String cidsCSV = String.join(",", cids);
		
		
		return pubNoticeAll(oidsCSV, cidsCSV);
		
		
	}
	//20,30,40,45,
	public int pubNoticeAll(String oidsCSV, String cidsCSV){
		
		int result = 0;
		String sqlOpen = String.format("UPDATE NOTICE SET PUB=1 WHERE ID IN (%s)", oidsCSV);
		String sqlClose = String.format("UPDATE NOTICE SET PUB=0 WHERE ID IN (%s)",cidsCSV);
			
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
//			PreparedStatement st = con.prepareStatement(sqlOpen);
			Statement stOpen = con.createStatement();
			int resultOpen = stOpen.executeUpdate(sqlOpen);
			
			
			Statement stClose = con.createStatement();
			int resultClose = stClose.executeUpdate(sqlClose);
			
			
			
			
			
			
			stOpen.close();
			stClose.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return result;
	}


	public int insertNotice(Notice notice){
		int result = 0;
		
		String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES) VALUES(?,?,?,?,?)";

		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
//		System.out.println(notice.getTitle());
//		System.out.println(notice.getContent());
//		System.out.println(notice.getWriterId());
//		System.out.println(notice.getPub());
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());
			
			
			
//			Statement st = con.createStatement();
			result = st.executeUpdate();
			
			
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		return result;
	}
	
	public int deleteNotice(int id){
		return 0;
	}
	
	public int updateNotice(Notice notice){
		return 0;
		
	}
	List<Notice> getNoticeNewestList(){
		
		return null;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public List<NoticeView> getNoticeList() {

		return getNoticeList("title", "", 1);

	}

	public List<NoticeView> getNoticeList(int page) {

		return getNoticeList("title", "", page);

	}

	public List<NoticeView> getNoticeList(String field/*TITLE,WRITER_ID*/, String query/*A*/, int page) {
		
		List<NoticeView> list = new ArrayList<>();

			String sql = "select * from("
					+ "select ROWNUM NUM, N.* "
					+ "from (select * from NOTICE_VIEW where "+field+" LIKE ? order by REGDATE DESC) N"
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
//						String content = rs.getString("CONTENT");
						int cmtCount = rs.getInt("CMT_COUNT");
						boolean pub = rs.getBoolean("pub");
						
						NoticeView notice = new NoticeView(id, title, regdate, writerId, hit, files, pub, 
//								content,
								cmtCount);

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
	
	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		// TODO Auto-generated method stub
		List<NoticeView> list = new ArrayList<>();

		String sql = "select * from("
				+ "select ROWNUM NUM, N.* "
				+ "from (select * from NOTICE_VIEW where "+field+" LIKE ? order by REGDATE DESC) N"
				+ ")"
				+ "where pub=1 AND num between ? and ?";
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
				
//				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery();
				
				
				while (rs.next()) {
					int id = rs.getInt("ID");
					String title = rs.getString("TITLE");
					Date regdate = rs.getDate("REGDATE");
					String writerId = rs.getString("WRITER_ID");
					String hit = rs.getString("HIT");
					String files = rs.getString("FILES");
//					String content = rs.getString("CONTENT");
					int cmtCount = rs.getInt("CMT_COUNT");
					boolean pub = rs.getBoolean("pub");
					
					NoticeView notice = new NoticeView(id, title, regdate, writerId, hit, files, pub, 
//							content,
							cmtCount);

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
						boolean pub = rs.getBoolean("pub");
						int nid = rs.getInt("ID");
						String title = rs.getString("TITLE");
						Date regdate = rs.getDate("REGDATE");
						String writerId = rs.getString("WRITER_ID");
						String hit = rs.getString("HIT");
						String files = rs.getString("FILES");
						String content = rs.getString("CONTENT");

						notice = new Notice(nid, title, regdate, writerId, hit, files, content,pub);

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
					boolean pub = rs.getBoolean("pub");

						notice = new Notice(nid, title, regdate, writerId, hit, files, content,pub);

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

				boolean pub = rs.getBoolean("pub");
				

					notice = new Notice(nid, title, regdate, writerId, hit, files, content,pub);

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
	public int deleteNoticeAll(int[] ids) {
		int result = 0;
		
		String params="";
		
		for(int i=0; i<ids.length; i++) {
			params += ids[i];
			
			if(i <= ids.length-1)
			
				params += ",";
			
		}
		
		String sql = "DELETE NOTICE WHERE ID IN("+params+")";

		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";

	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "1234");
			Statement st = con.createStatement();
			
			
			
//			Statement st = con.createStatement();
			result = st.executeUpdate(sql);
			
			
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		
		return result;
	}


}
