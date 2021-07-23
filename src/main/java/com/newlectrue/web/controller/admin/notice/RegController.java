package com.newlectrue.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlectrue.web.entity.Notice;
import com.newlectrue.web.service.NoticeService;

@MultipartConfig(
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50,
		maxRequestSize = 1024*1024*50*5
		)

@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp")
		.forward(request, response);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		String title = request.getParameter("title");
		String content= request.getParameter("content");
		String isOpen= request.getParameter("open");

		
		
		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder();
		
		
		
//		Part filepart =  request.getPart("file");
		
		
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;
			if(p.getSize() ==0 )continue;
			
		
//			Part filepart =  request.getPart("file");
			Part filepart =  p;
			String fileName = filepart.getSubmittedFileName();
			
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filepart.getInputStream();
			
			String realPath = request.getServletContext().getRealPath("/member/upload");
			System.out.println(realPath);
			
			File path = new File(realPath);
			if(!path.exists())
				path.mkdirs();
			
			
			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);
		
			
			byte [] buf = new byte[1024];
			int size = 0;
			while((size = fis.read(buf)) !=-1)
				fos.write(buf,0,size);
			
			fos.close();
			fis.close();
			
		
		}
		
		builder.delete(builder.length()-1, builder.length());
		
		boolean pub = false;
		if(isOpen !=null)
			pub = true;
		
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());
		
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		System.out.println();
		PrintWriter out = response.getWriter();
		out.printf("title : %s<br >", title);
		out.printf("content : %s<br >", content);
		out.printf("isOpen : %s<br >", isOpen);
		
		response.sendRedirect("list");
	}

}
