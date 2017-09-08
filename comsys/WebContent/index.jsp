<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ include file="checkLogin.jsp" %><%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><%@ page import="com.news.lu.db.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻</title>
<script src="tool.js"></script>
<script src="js/jquery.js"></script>
<script src="ckeditor/ckeditor.js?v=0.1"></script>
<link rel="stylesheet" href="css/main.css" >
</head>
<body>
<div id="title">
	后台管理
	<a class="drop" href="#" onclick="drop()">退出</a>
</div>
<div id="center">
	<div id="left_bar">
		<%
			int user_id = (int)session.getAttribute("user_id");
			List<String> tbs = JDBC.getTables(user_id);
			for(String tb_name:tbs){
				out.println("<a href=\"#"+tb_name+"\" onclick='mainLoad(\""+tb_name+"\",0)'>"+JDBC.translate(tb_name)+"</a><br>");
			}
		%>
		<a href="#" onclick="addTable();">添加表</a>
	</div>
	<div id="main"> 
	</div>
</div>
</body>
</html>