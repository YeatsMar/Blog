<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="object.*" %>
<%@ page import="object.Article" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="service.LikeService" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看文章</title>
</head>
<link href="blog.css" rel="stylesheet" type="text/css" />
<script src="blog.js" type="text/javascript"></script>
<body>
<%
	//get user and blogger
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("userId");
	String blogId = (String)session.getAttribute("blogId");
	User user = new User(blogId);
	
	//get articles
	String articleIdStr = (String)request.getParameter("articleId");
	int articleId = Integer.parseInt(articleIdStr);
	Article article = new Article(articleId);
        if(article.time==null){
            request.setAttribute("url", "blog_list.jsp?category=all");
            request.getRequestDispatcher("success.jsp").forward(request, response);
        }
	
	//get comments
	ArrayList<Integer> commentIDs = Comment.getComments(articleIdStr);
	ArrayList<Comment> commentList = new ArrayList<Comment>();
	for (int i = 0; i < commentIDs.size(); i++) {
		commentList.add(new Comment(commentIDs.get(i)));
	}
	
	//readAmount
	if (!id.equals(blogId)) {
		article.read++;
		article.readAmount();
	}
	
	//like
	boolean isLike = LikeService.isLike(id, articleIdStr);
	String isLikeStr;
	if (isLike) {
		isLikeStr = "取消赞";
	}
	else {
		isLikeStr = "赞";
	}
	
	ArrayList<Integer> articleIDs = (ArrayList<Integer>)session.getAttribute("articleIDs");
	int articleID0 = articleIDs.get(0);
%>
	<div id="wrapper">
		<div id="content">
			<div id="name">
				<!--title of the blog  TODO subtitle-->
				<h1><%=user.username %>的博客</h1>
				<h5><%=user.signature %></h5>
				
				<!--nav of the blog-->
				<nav id="nav">
					<a href="main2.jsp" alt="">首页</a>
					<a href="blog_list.jsp?category=all" alt="">博文目录</a>
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
					<img src="pics/head.jpg" width=188px height=188px></img><br />
					<h3><%=user.username %></h3>
					<p><%=user.selfIntroduction %></p>
				</div>
			</div>
			
			<!-- the whole content of an article -->
			<div id="article">
				<h4>博文</h4>
				<div id="article_detail">
					<span>
						<h2><%=article.title %></h2>
						<span class="time"><%=article.time %></span>
					</span>
					<br />
					<div class="mark">
						标签：<span class="label">
								<% ArrayList<String> tags = article.tags();//传址 引用一处
								for (int j = 0; j < tags.size(); j++) {
								%>
								<%=tags.get(j) %>  
								<%	
								} %>  
							 </span>
						分类：<span class="classify"><%=article.category %></span>
					</div><!--above are the same as main page-->
					<!--content-->
					<div class="passage"><%=article.content %></div>
					<nav>
						<a class="help" >阅读(<%=article.read %>)</a><!--TODO with the number-->
						<% if (id.equals("0")) {
						%>
						<a class="help" href="" onclick="cannot()">赞(<%=LikeService.getLikeNumber(articleIdStr) %>)</a>
						<%
					    } else {%>
					    <!-- 赞 --><a class="help" onclick="like(<%=isLike %>, <%=id %>, <%=articleId %>)" id="like" href="" alt=""><%=isLikeStr %>(<%=LikeService.getLikeNumber(articleIdStr) %>)</a>
					    <%
					    } %>
					    <%if (id.equals("0")) { %>
						<a class="help" href="#put_tag">评论(<%=commentIDs.size() %>)</a>
						<%} %>
						<% if (id.equals(blogId)) {
						%>
						<a class="help" href="edit.jsp?articleId=<%=articleId %>">编辑</a>
						<a href="" onclick="deleteArticle(<%=articleId %>)">删除</a>
						<%
					    } %>
					</nav>
				</div><!--content236 236 243-->
				<div id="comments">
					<div id="comments_tag">
						<h5>评论</h5>
					</div>
					<!-- show comments -->
					<%for (int i = 0; i < commentList.size(); i++) { 
						Comment comment = commentList.get(i);
					%>
					<div class="comment">
						<p><%=comment.comment %></p>
					    <div class="comment_help">
							<p class="time"><%=comment.time %></p>
							<%if(id.equals(comment.userId + "") || id.equals(blogId)) { %>
							<a href="" alt="" onclick="deleteComment(<%=comment.id %>)">删除</a>
							<%} %>
						</div>	
					</div>
					<%} %>
					<%if (!(id.equals("0"))) { %>
					<!-- add new comment -->
					<div id="put_tag">
						<h5>发评论</h5>
					</div>
					<form id="comment"  method="GET" action="Blog" onsubmit="return addComment(<%=articleId %>, <%=id %>)">
					<textarea id="commentContent" name="commentContent" wrap="virtual" form="comment"></textarea>
					<div id="btn">
						<input type="submit" value="发评论" />
					</div>
					</form>
					<%} %>
				</div>
				<div id="last_next">
					<%if (articleId != articleIDs.get(0)) { %>
					<a class="last" href="blog.jsp?articleId=<%=articleId + 1 %>" alt="">《前一篇</a><!--TODO if this article is the first one-->
					<%} %>
					<%if (articleId != articleID0 + 1 - articleIDs.size()) { %>
					<a class="next" href="blog.jsp?articleId=<%=articleId - 1 %>" alt="">后一篇》</a>
					<%} %>
				</div>
				<div id="article_title">
					<%if (articleId != articleIDs.get(0)) {%>
					<span class="last"><%=new Article(articleId + 1).title %></span>
					<%} %>
					<%if (articleId != articleID0 + 1 - articleIDs.size()) { %>
					<span class="next"><%=new Article(articleId - 1).title %></span>
					<%} %>
				</div>
			</div><!--div=article-->
		</div>
	</div>
	<footer>
		<p>Copyright &copy; 2015- 14FudanSS, All Rights Reserved</p>
		<p>复旦软工 版权所有</p>
	</footer>
</body>
</html>