package com.news.lu.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.lu.tool.Tools;

@WebServlet("/addTable")
public class addTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public addTable() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html");
		
		//表、列名和表、的中文名列
		String tb_name = request.getParameter("tb_name");
		String tb_title = request.getParameter("tb_title");
		String[] fd_names = request.getParameterValues("fd_name");
		String[] fd_types = request.getParameterValues("fd_type");
		String[] fd_titles = request.getParameterValues("fd_title");
		System.out.println(fd_names+"\n"+fd_titles+"\n"+fd_types);

		Tools.addTable(tb_name,tb_title, fd_names, fd_types, fd_titles);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
