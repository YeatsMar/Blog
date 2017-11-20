<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="object.User" %>
<%@ page import="object.Article" %>
<%@ page import="service.CategoryService" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博文目录</title>
<link href="blog_list.css" rel="stylesheet" type="text/css" />
<script src="category.js"></script>
</head>
<body>
<% 
	//get user and blogger
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("userId");
	String blogId = (String)session.getAttribute("blogId");
	User user = new User(blogId);
	
	//get categories
	ArrayList<String> categoryList = CategoryService.getCategories(blogId);
	String category = request.getParameter("category");
	//above right. 
	
	//get articles
	ArrayList<Integer> articleSelect = new ArrayList<Integer>();
	ArrayList<Article> articleList = new ArrayList<Article>();
	if (category.equals("all")) {
		articleSelect = Article.getArticles(blogId);
		for (int i = 0; i < articleSelect.size(); i++) {
			Article article = new Article(articleSelect.get(i));
			articleList.add(article);
		}
	}
	else {
		articleSelect = Article.getArticleCertain(blogId, category);
		for (int i = 0; i< articleSelect.size(); i++) {
			Article article = new Article(articleSelect.get(i));
			articleList.add(article);
		}
	}
	
%>
	<div id="wrapper">
		<div id="content">			
			<div id="name">
				<!--title of the blog  TODO subtitle-->
				<h1><%=user.username %>的博客</h1>
				<h5><%=user.signature %></h5>
				
				<!--nav of the blog-->
				<nav id="nav">
					<a href="main2.jsp">首页</a>
					<a class="self" href="blog_list.jsp?category=all">博文目录</a>
					<%if (id.equals("0")) { %>
					<a href="choice2.jsp">返回</a> 
					<%} else { %>
					<a href="choice.jsp">返回</a> 
					<%} %>
					<% if (id.equals(blogId)) {
					%>
						<a href="selfInfo.jsp">关于我</a>
					<%
					    } %>
				</nav>
			</div><!--above are the same as main page-->
			
			<% if (id.equals(blogId)) {
			%>
			<div id="post_btn">
				<a href="edit.jsp"><input type="submit" value="发博文" /></a>
			</div>
			<%
				} %>
			
			<!--the left column-->
			<div id="left">
				<div id="article_nav">
					<h4>博文</h4>
					<ul>
						<li><a href="blog_list.jsp?category=all" alt="">全部博文</a>(<%=CategoryService.getNumberAll(blogId) %>)</li>
						<%
							for (int i = 0; i < categoryList.size(); i++) {
						%>
                            <li>
                            	<a href="blog_list.jsp?category=<%=categoryList.get(i) %>" alt=""><%=categoryList.get(i) %></a>(<%=CategoryService.getNumberOfArticle(blogId, categoryList.get(i)) %>) <% if(id.equals(blogId)){ %><span class="tag">
                            	<a href="" class="do" onclick="edit(<%=id%>, '<%=categoryList.get(i)%>')"> [编辑]</a> 
                            	<a href="" class="do" onclick="<% if (CategoryService.getNumberOfArticle(id, categoryList.get(i)) == 0) {%> 
                            										del(<%=id%>, '<%=categoryList.get(i)%>')
                            								  <% } else { %>
                            								  	    cannot()
                            								  <% } %>">[删除]</a></span><% } %>
                            </li>
						<%
							}
						%><!-- 事件：选中种类增加class＝“self” -->
					</ul>	
				</div>
				<div id="search">
					<h4>搜博主文章</h4>
					<form method="POST" action="Search">
						<input id="search_bar" type="text" name="key"/>
						<input id="search_btn" type="submit" value="搜索"/><br />
						<input class="mode" type="radio" name="mode" value="title" checked />标题 
			     		<input class="mode" type="radio" name="mode" value="content" />正文
			     		<input class="mode" type="radio" name="mode" value="tag" />标签<br />
			     		<input class="mode" type="radio" name="scope" value="one" />仅博主文章 
			     		<input class="mode" type="radio" name="scope" value="whole" checked />全站搜索
						
					</form>
				</div>
			</div>
			<div id="content_area">
				<!-- show category selected -->
				<%if (category.equals("all")) {%>
					<h4>全部文章</h4>
					<!-- show the titles of a certain category -->
					<ul>
						<%for (int i = 0; i < articleList.size() ;i++) {%>
							<li>
								<a href="blog.jsp?articleId=<%=articleList.get(i).id %>"><%=articleList.get(i).title %></a>
								<span class="see">
									<span class="time"><%=articleList.get(i).time.toString()%></span>
									<% if (id.equals(blogId)) {
									%>
										<a href="edit.jsp?articleId=<%=articleList.get(i).id %>">[编辑]</a>
									<%} %>
								</span>
							</li>
						<%}%>
					</ul>
				<%} 
				  else {
				%>
				    	<h4><%=category %></h4>
				    	<!-- show the titles of a certain category -->
				    	<ul>
						<%for (int i = 0; i < articleList.size() ;i++) {%>
							<li>
								<a href="blog.jsp?articleId=<%=articleList.get(i).id %>"><%=articleList.get(i).title %></a>
								<span class="see">
									<span class="time"><%=articleList.get(i).time.toString()%></span>
									<a href="edit.jsp?articleId=<%=articleList.get(i).id %>">[编辑]</a><!--TODO-->
								</span>
							</li>
						<%}%>
					</ul>
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