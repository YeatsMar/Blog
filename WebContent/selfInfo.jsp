<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="object.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑个人资料</title>
<link href="selfInfo.css" rel="stylesheet" type="text/css" />
<script src="selfInfo.js"></script>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");	
	response.setCharacterEncoding("UTF-8");
	String userId = (String)session.getAttribute("userId");
	User user = new User(userId);
	String blogId = (String)session.getAttribute("blogId");
	User user2 = new User(blogId);
%>
	<div id="wrapper">
		<div id="content">
			<div id="name">
				<!--title of the blog-->
				<h1><%=user2.username%>的博客
				</h1>
				<h5><%=user2.signature%></h5>

				<!--nav of the blog-->
				<nav id="nav"> 
					<a class="self" href="main2.jsp">首页</a> 
					<a href="blog_list.jsp?category=all">博文目录</a> 
					<%if (userId.equals("0")) { %>
					<a href="choice2.jsp">返回</a> 
					<%} else { %>
					<a href="choice.jsp">返回</a> 
					<%} %> 
					<%if (userId.equals(blogId)) {%> 
						<a href="selfInfo.jsp">关于我</a> 
					<%}%> 
				</nav>
			</div>

			<div id="edit">
				<h4>请编辑您的个人资料</h4>
                <form method="POST" action="SelfInfo" enctype="multipart/form-data" id="head">
                     头像：<img src="pics/avatar.jsp?userId=<%= userId %>" width=100px height=100px></img><br>
                     <input type="file" id="avatar" name="avatar" accept="image/*" />
                     <input type="submit" value="上传" />
                </form>
				<form method="POST" action="SelfInfo" onsubmit="return validate()"
				id="info_form">
                                    <input type="hidden" name="pass" value="false" />
					昵称：<textarea rows="1" cols="10" id="username" name="username" onchange="validate()" autofocus><%=user.username %></textarea>
				 	<span class="warnning" id="warn0"></span><br />
					性别：<input type="radio" name="gender" value="female" checked />女 
			     	<input type="radio" name="gender" value="male" />男 
					爱好：<textarea rows="1" cols="20" id="hobby" name="hobby" onchange="validate()"><%=user.hobby %></textarea>
				 	<span class="warnning" id="warn1"></span><br />
					喜欢的颜色：
				 	<textarea rows="1" cols="10" id="color" name="color" onchange="validate()" ><%=user.color %></textarea>
				 	<span class="warnning" id="warn2"></span><br />
		    		个人简介：
		   			 <textarea rows="2" cols="50" name="introduction" id="introduction" form="info_form" onchange="validate()" /><%=user.selfIntroduction %></textarea>
					<span class="warnning" id="warn4"></span><br /> 
					个性签名：
					<textarea rows="2" cols="50" name="signature" id="signature" form="info_form" onchange="validate()" /><%=user.signature %></textarea>
					<span class="warnning" id="warn5"></span><br />
                                        <div id="btn"><!-- float:bottom clear:both -->
						<input id="btn1" type="submit" value="提交" /> 
						<input id="btn2" type="reset" value="重置" />
						<a href="main2.jsp"><input id="btn3" type="button" value="返回" /></a><!-- TODO request.setAttribute("blogId",blogId) -->
					</div>
                                </form>
                                <form method="POST" action="SelfInfo" onsubmit="return validateForm()"
				id="change_pass">
                                    <input type="hidden" name="pass" value="true" />
					修改密码为：<input type="password" size=40 value=""
					placeholder="请输入您新的密码，请勿与旧密码重复" id="newPassword" name="newPassword"
					onchange="validatePass()" /><span class="warnning" id="warn3"></span><br />
					<div id="btn"><!-- float:bottom clear:both -->
						<input id="btn1" type="submit" value="提交" /> 
						<input id="btn2" type="reset" value="重置" />
						<a href="main2.jsp"><input id="btn3" type="button" value="返回" /></a><!-- TODO request.setAttribute("blogId",blogId) -->
					</div>
				</form>
			</div>
		</div>
	</div>
	<footer>
	<p>Copyright &copy; 2015- 14FudanSS, All Rights Reserved</p>
	<p>复旦软工 版权所有</p>
	</footer>
</body>
</html>