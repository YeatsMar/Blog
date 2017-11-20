<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请稍等……</title>
</head>
<link href="index.css" rel="stylesheet" type="text/css" />
<body>
	<%
		String url = (String) request.getAttribute("url");

		if (url != null) {
			String urlTemp = "3;URL=" + url;
			response.setHeader("refresh", urlTemp);
		}
		else {
			System.out.println("request失效 success.jsp报错");
		}
	%>
	<h1>操作成功！</h1>
	<a href="<%=url%>"><p>3秒后将跳转页面，如没有跳转请点击</p></a>
</body>
</html>