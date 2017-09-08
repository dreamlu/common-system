package com.news.lu.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.lu.tool.Mail;
import com.news.lu.tool.Tools;

@WebServlet("/sendEmail")
public class sendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public sendEmail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String key = request.getParameter("key");
		String user_name = request.getParameter("user_name");
		String user_email = request.getParameter("user_email");
		
		String sercet_key = Tools.md5(user_email);
		
		//根据key来判断时发送重置前的确认邮件还是发送后的重置了密码的邮件
		//密码重置成功,发送重置后的密码到该邮箱
		if(key != null && key.equals(sercet_key)){
			//System.out.println("重置密码成功！");
			//重置密码成功，将数据库中该用户密码随机修改保存，并将新站密码发送给用户
			//随机生成6位数新密码
			String passwd = String.valueOf((int)((Math.random()*9+1)*100000));
			Tools.forgetPassWord(passwd, user_email);
			PrintWriter out = response.getWriter();
			out.println("重置密码成功！新的密码为"+passwd);
		}
		else{
			try {
				new Mail(user_name, user_email, sercet_key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("邮件未发送");;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
