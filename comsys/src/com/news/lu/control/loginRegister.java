package com.news.lu.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.news.lu.tool.Tools;

@WebServlet("/loginRegister")
public class loginRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public loginRegister() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//response.setContentType("text/html");
		String login = request.getParameter("login");
		String name = request.getParameter("user");
		String password = request.getParameter("password");
		if(login == null) login = "";
		
		boolean b = false;
		HttpSession session = request.getSession();
		if(login.equals("login")){
			List<Map<String, Object>> ret = Tools.login(name, password);
			if(ret != null && ret.size() == 1) b = true;
			if(b) {
				int id = (int)ret.get(0).get("id");
				session.setAttribute("user_id", id);
				session.setAttribute("user_name", name);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else if(login.equals("register")){
			List<Map<String, Object>> ret = Tools.register(name, password, null);
			if(ret != null && ret.size() == 1) b = true;
			if(b) {
				int id = (int)ret.get(0).get("id");
				session.setAttribute("user_id", id);
				session.setAttribute("user_name", name);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
