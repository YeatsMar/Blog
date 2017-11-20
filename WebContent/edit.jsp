<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="service.CategoryService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="object.Article" %>
<!doctype html>
 <head>
  <meta charset="UTF-8">
  <title>Edit</title>
  <link href="edit.css" rel="stylesheet" type="text/css" />
  <script src="edit.js"></script>
 </head>
 <body>
     <%
         request.setCharacterEncoding("UTF-8");
     	 response.setCharacterEncoding("UTF-8");
         String id = (String)session.getAttribute("userId");
         String articleId= (String)request.getParameter("articleId");
         ArrayList<String> tagsList=null;
         String tags=null;
         Article article = null;
         if(articleId==null){
             article=new Article();
         } else {
             article = new Article(Integer.parseInt(articleId));
             tagsList = article.tags();
             tags = tagsList.toString().replaceAll("[,\\[\\]]","");//前一个字符串是正则表达式，后一个是replacement，这句话去掉“[],”三种字符
         }
         ArrayList<String> categoryList = CategoryService.getCategories(id);
     %>
     <form method="POST" action="Edit" id="edit" onsubmit="return validate()">
         <% if(article!=null) { %>
            <input type="hidden" value="<%=articleId %>" id="articleId" name="articleId" />
         <% } %>
		<div id="title_tag">
			<h4>写博文</h4>
		</div>
		<div id="passage_title" >
			<span class="helper">标题</span>
                        <input id="title" value="<%=article.title %>" name="title" type="text" autofocus />
		</div>
		<div id="passage_box">
			<textarea id="passage" name="passage" wrap="virtual"><%=article.content %></textarea>
		</div>
		<div id="detail">
			<span class="helper">分类</span>
			<select id="category" name="category">
				<option value="default_option">选择分类</option>
				<%
							for(int i = 0; i < categoryList.size(); i++) {
						%>
                                                <option value="<%=categoryList.get(i)%>" <% if(article.category.equals(categoryList.get(i))){ %> selected="selected"<% } %>><%=categoryList.get(i) %></option>
						<%
							}
						%>
			</select>
                                                <a onclick="createCategory(<%=id %>)">创建分类</a><!--TODO-->
			<br />
			<span class="helper">标签</span>
                        <input size=50 <% if(tags!=null){ %> value="<%=tags%>" <% } %> placeholder="让更多人看到你的博文（用空格和半角逗号分割）" id="tags" name="tags" />
		</div>
		<div id="btn">
			<input type="submit" value="发博文"/><!--TODO-->
		</div>
	</form>
	<footer>
		<p>Copyright &copy; 2015- 14FudanSS, All Rights Reserved</p>
		<p>复旦软工 版权所有</p>
	</footer>
 </body>
</html>
  