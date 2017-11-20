<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="object.User"%>
<%@ page import="object.*"%>
<%@ page import="service.CategoryService"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人主页</title>
<link href="index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = (String) session.getAttribute("userId");
        if(id.equals(null)) {
            response.sendRedirect("login.html");
            return;
        }
		String blogId = (String) request.getParameter("blogId");
		session.setAttribute("blogId", blogId);//from now on the blogId is in the session
		User user = new User(blogId);

		ArrayList<String> categoryList = CategoryService
				.getCategories(blogId);

		ArrayList<Integer> articleSelect = Article.getArticles(blogId);
		session.setAttribute("articleIDs", articleSelect);//to provide the function to see the previous and last article
		ArrayList<Article> articleList = new ArrayList<Article>();
		for (int i = 0; i < 5 && i < articleSelect.size(); i++) {
			articleList.add(new Article(articleSelect.get(i)));
			
		}
		ArrayList<String> contentList = Article.abstractArticles(blogId);
		
	%>
	<div id="wrapper">
		<div id="content">
			<div id="name">
				<!--title of the blog  TODO subtitle-->
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
					<% if (id.equals(blogId)) {
					%>
						<a href="selfInfo.jsp">关于我</a>
					<%
					    } %>
				</nav>
			</div>

			<!--the left column-->
			<div id="left">
				<!--the information of the blogger-->
				<div id="info">
					<h4>个人资料</h4>
					<img src="pics/avatar.jsp?userId=<%= blogId %>" width=188px height=188px></img><br />
					<h3><%=user.username%></h3>
					<p><%=user.selfIntroduction%></p>
				</div>
				<!--nav of the articles-->
				<div id="article_nav">
					<h4>分类</h4>
					<ul>
						<li><a href="blog_list.jsp?category=all" alt="">全部博文</a>（<%=CategoryService.getNumberAll(blogId)%>）</li>
						<%
							for (int i = 0; i < categoryList.size(); i++) {
						%>
								<li><a href="blog_list.jsp?category=<%=categoryList.get(i) %>" alt=""><%=categoryList.get(i) %></a>（<%=CategoryService.getNumberOfArticle(blogId, categoryList.get(i)) %>）</li>
						<%
							}
						%>
					</ul>
				</div>
			</div>

			<!--right column-->
			<div id="article">
				<h4>博文</h4>
				<%
					for (int i = 0; i < articleList.size(); i++) {
						ArrayList<Integer> commentIDs = Comment.getComments(articleSelect.get(i) + "");
				%>
				<div id="article<%=i + 1%>">
					<span> <!--title of the article-->
						<h2><%=articleList.get(i).title%></h2> <!--time blogged--> <span
						class="time">(<%=articleList.get(i).time%>)
					</span>
					</span> <br />
					<!--mark-->
					<div class="mark">
						标签：<span class="label"> <%
 	ArrayList<String> tags = articleList.get(i).tags();//传址 引用一处
 		for (int j = 0; j < tags.size(); j++) {
 %> <%=tags.get(j)%> <%
 	}
 %>
						</span> 分类：<span class="classify"><%=articleList.get(i).category%></span>
					</div>
					<!--content-->
					<div class="passage"><%=contentList.get(i)%><!-- only a part --></div>
					<nav> <a class="help">阅读（<%=articleList.get(i).read%>）
					</a> <a>评论（<%=commentIDs.size() %>）</a> <!-- TODO --> <a class="see"
						href="blog.jsp?articleId=<%=articleList.get(i).id%>">查看全文>></a> </nav>
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