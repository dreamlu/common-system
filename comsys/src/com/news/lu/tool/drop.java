package com.news.lu.tool;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.news.lu.db.JDBC;


@WebServlet("/drop")
public class drop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public drop() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String drop = request.getParameter("drop");
		if(drop == null) {
			drop = "";
		}
		if(drop.equals("drop")){
			session.invalidate();
		}
		/*else if(drop.equals("ban")){
			String user_id = (String)session.getAttribute("user_id");
			String sql = "select user_ban_from `user` where id=?";
			List<Map<String, Object>> list = JDBC.executeQuery(sql, user_id);
			int i = (int)list.get(0).get("user_ban");
			if(i != 0) session.invalidate();
		}*/
	}

}
