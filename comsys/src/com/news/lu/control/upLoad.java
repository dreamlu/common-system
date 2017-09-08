package com.news.lu.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * 上传图片
 */
@WebServlet("/upLoad")
@MultipartConfig //(maxFileSize=52428800,maxRequestSize=52428800,fileSizeThreshold=0)
public class upLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public upLoad() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Part part = request.getPart("upload");
		String name = part.getHeader("content-disposition");
		String root = request.getServletContext().getRealPath("/upload");
		if(name.lastIndexOf(".")!=-1)
		{
			String str = name.substring(name.lastIndexOf("."),name.length()-1);
			//windows系统路径,后面用filename.lastIndexOf("\\")
			//String filename = root+"\\"+UUID.randomUUID().toString()+str;
			//linux系统路径
			String filename = root+"/"+UUID.randomUUID().toString()+str;
			part.write(filename);
			String address = "upload"+filename.substring(filename.lastIndexOf("/"));
			//session.setAttribute("address", address);
			//String imageAddress = address.replace("\\", "/");
			//System.out.println(imageAddress);
	        String callback = request.getParameter("CKEditorFuncNum");
	        PrintWriter out = response.getWriter();
	        out.println("<script type=\"text/javascript\">");
	        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + address + "',''" + ")");
	        out.println("</script>");
	        out.flush();
	        out.close();
		}
		else
		{
			//address = (String)session.getAttribute("address");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
