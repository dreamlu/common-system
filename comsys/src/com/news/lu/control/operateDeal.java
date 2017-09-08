package com.news.lu.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.lu.tool.Tools;

@WebServlet("/operateDeal")
public class operateDeal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public operateDeal() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		int id = 0;
		String tbname = null;
		String[] fd_values = null;
		String operate = "";
		try{
			id = Integer.parseInt(request.getParameter("id"));
		}catch(Exception e){}
		tbname = request.getParameter("tbname");
		fd_values = request.getParameterValues("fd_values");
		operate = request.getParameter("operate");
		
		PrintWriter out = response.getWriter();
		
		if(operate.equals("alter")){
			Tools.alter(id, tbname,fd_values);
			out.write("");
		}if(operate.equals("add")){
			Tools.addData(tbname,fd_values);
			out.write("");
		}
		
		
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
