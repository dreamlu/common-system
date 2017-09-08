<%
	if(session.getAttribute("user_id")==null){
		response.sendRedirect("login.jsp");
		return;//之后的语句就不执行了
	}
%>