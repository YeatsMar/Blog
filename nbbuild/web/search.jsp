<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="object.*"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果</title>
<link href="search.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = (String) session.getAttribute("userId");
		if (id.equals(null)) {
			response.sendRedirect("login.html");
			return;
		}
		String blogId = (String) session.getAttribute("blogId");
		User user = new User(blogId);
	
		ArrayList<Integer> articleIDs = (ArrayList<Integer>)request.getAttribute("articleIDs");
		ArrayList<Article> articleList = new ArrayList<Article>();
		System.out.println(articleIDs.size());
		for (int i = 0; i < articleIDs.size(); i++) {
			articleList.add(new Article(articleIDs.get(i)));
		}
		//ArrayList<String> contentList = Article.abstractArticles(blogId);
		session.setAttribute("articleIds", articleIDs);//not suggested, only for Sort.java to get it
	%>

	<div id="wrapper">
		<div id="content">
			<div id="name">
				<!--title of the blog-->
				<h1><%=user.username%>的博客
				</h1>
				<h5><%=user.signature%></h5>

				<!--nav of the blog-->
				<nav id="nav"> 
					<a class="self" href="main2.jsp">首页</a> 
					<a href="blog_list.jsp?category=all">博文目录</a> 
					<%if (id.equals("0")) { %>
					<a href="choice2.jsp">返回</a> 
					<%} else { %>
					<a href="choice.jsp">返回</a> 
					<%} %> 
					<%if (id.equals(blogId)) {%> 
						<a href="selfInfo.jsp">关于我</a> 
					<%}%> 
				</nav>
			</div>
			
			<!-- top column -->
			<div id="top_column">
			<div id="search_column" class="top">
				<form id="search" name="name" method="POST" action="Search">
					<input id="search_bar" type="text" name="key"/>
					<input type="radio" name="mode" value="title" checked />标题 
		     		<input type="radio" name="mode" value="content" />正文
		     		<input type="radio" name="mode" value="tag" checked />标签			     		
		     		<input type="radio" name="scope" value="one" checked />仅博主文章 
			    		<input type="radio" name="scope" value="whole" />全站搜索
					<input id="search_btn" type="submit" value="搜索"/>
				</form>
			</div>
			<div id="sort_column" class="top">
				<form id="sort" name="name" method="POST" action="Sort">
					<input type="radio" name="sort" value="timeUp" />按时间升序
		     		<input type="radio" name="sort" value="timeDown" checked />按时间降序
					<input id="sort_btn" type="submit" value="排序"/>
					<input type="hidden" name="articleIDs" value="<%=articleIDs %>"/>
				</form>
			</div>
			</div>
			<!-- show results -->
			<div id="article">
				<h4>博文</h4>
				<%
					if (articleIDs.size() == 0) {
				%>
				<p id="none">未搜索到任何结果</p>
				<%
					}
				%>
				<%
					for (int i = 0; i < articleList.size(); i++) {
						ArrayList<Integer> commentIDs = Comment.getComments(articleIDs.get(i) + "");
				%>
				<div id="article<%=i+1 %>">
					<span> <!--title of the article-->
						<h2><%=articleList.get(i).title%></h2> <!--time blogged--> <span
						class="time">(<%=articleList.get(i).time%>)
					</span>
					</span> <br />
					<!--mark-->
					<div class="mark">
						标签：<span class="label"> 
						<%
 							ArrayList<String> tags = articleList.get(i).tags();//传址 引用一处
 							for (int j = 0; j < tags.size(); j++) {
 						%> 
 						<%=tags.get(j)%> 
 						<%} %>
						</span> 分类：<span class="classify"><%=articleList.get(i).category%></span>
					</div>
					<!--content-->
					<div class="passage"><%=articleList.get(i).content%></div>
					<nav> 
					<a class="help">阅读（<%=articleList.get(i).read%>）
					</a> 
					<a>评论（<%=commentIDs.size() %>）</a> 
				</div>
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