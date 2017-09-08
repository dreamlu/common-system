<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>忘记密码</title>
</head>
<body>
请通过邮箱中的验证，正确后，将会将重置后的密码通过邮箱发送～
<form method="post" action="sendEmail">
	昵称：<input type="text" name="user_name"><br>
	邮箱：<input type="email" name="user_email">
	<input type="submit" value="重置密码">
</form>
</body>
</html>