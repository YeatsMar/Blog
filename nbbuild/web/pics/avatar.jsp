<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@page contentType="image/jpeg" %>
<%
    final String dir ="/Users/Mar/Documents/workspace/Blog/WebContent/pics/";
    String userId=request.getParameter("userId");
    File avatar=new File(dir+"avatar/"+userId+".jpg");
    if(!avatar.exists()){
        avatar=new File(dir+"head.jpg");
    }
    FileInputStream i=new FileInputStream(avatar);
    ServletOutputStream o = response.getOutputStream();
    byte b[] = new byte[1000000]; 
    while(i.read(b)!=-1) { 
        o.write(b);
    }
    o.flush();
    o.close();
%>
