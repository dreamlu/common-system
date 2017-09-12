package com.news.lu.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.lu.tool.Tools;

@WebServlet("/globalSearch")
public class globalSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public globalSearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String search_keyword = request.getParameter("search");
		String tb_name = request.getParameter("tb_name");
		
		PrintWriter out = response.getWriter();
		String content = Tools.search(search_keyword,tb_name);
		out.write(content);
		
		out.write("");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
