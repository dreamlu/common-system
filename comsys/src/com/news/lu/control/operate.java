package com.news.lu.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.lu.tool.Tools;


@WebServlet("/operate")
public class operate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public operate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/hml");
		
		String operate = "";
		int id = 0;
		String tbname = "";
		String content = "";
		try{
			id = Integer.parseInt(request.getParameter("id"));
		}catch(Exception e){}
		tbname = request.getParameter("tbname");
		operate = request.getParameter("operate");
		
		
		PrintWriter out = response.getWriter();
		
		if(operate.equals("alter")){
			content = Tools.alter(id,tbname);
			out.write(content);
		}
		else if(operate.equals("delete")){
			boolean b = Tools.delete(id,tbname);
			out.write(content);
		}
		else if(operate.equals("add")){
			content = Tools.addData(tbname);
			out.write(content);
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
