<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.UserService" %>
<%@ page import="object.User" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请选择</title>
<link href="choice.css" rel="stylesheet" type="text/css" />
</head>
<%
	request.setCharacterEncoding("UTF-8");
	//get userId
	ArrayList<Integer> userIds = UserService.getUserIds();
	ArrayList<String> userIdStr = new ArrayList<String>();
	for (int i = 0; i < userIds.size(); i++) {
		userIdStr.add(userIds.get(i) +"");
	}//一一对应？
	//create users
	ArrayList<User> users = new ArrayList<User>();
	for (int i = 0; i < userIdStr.size(); i++) {
		users.add(new User(userIdStr.get(i)));
	}
	session.setAttribute("userId", "0");
%>
<body>
	<div id="wrapper">
	<h2>Enter your own blog OR See others' blog?</h2>
	<div id="list">
		<div id="own">
			<h3 id="nothing">CANNOT enter my own</h3>
		</div>
		<div id="other">
			<% 
				for (int i = 0; i < userIds.size(); i++) {
			%>
			<a href="main.jsp?blogId=<%=userIds.get(i) %>" alt="" ><%=users.get(i).username %></a><br />	
			<%
				}
			%>
		</div>
	</div>
	</div>
	<footer>
		<p>Copyright &copy; 2015- 14FudanSS, All Rights Reserved</p>
		<p>复旦软工 版权所有</p>
	</footer>
</body>
</html>