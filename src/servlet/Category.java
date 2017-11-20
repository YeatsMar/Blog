package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Category")
public class Category extends HttpServlet {

    public Category() {
        super();
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("editing category");
        request.setCharacterEncoding("UTF-8");
        String userId = new String(request.getParameter("id").getBytes("ISO8859_1"),"UTF-8");
        String oldName = new String(request.getParameter("oldName").getBytes("ISO8859_1"),"UTF-8");
        String newName = new String(request.getParameter("newName").getBytes("ISO8859_1"),"UTF-8");
        System.out.println(oldName);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("error when loading jdbc");
            e.printStackTrace();
        }
        // connect database
        String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8";
        String DBusername = "root";
        String DBpassword = "Ma960904";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, DBusername, DBpassword);
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM `blog`.`category_list` WHERE `AUTHORID`='" + userId + "' AND `CATEGORY`='" + newName + "'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {//要改的名字重名
                response.addHeader("Fail", "true");
            } else {
                //修改已有article中的分类
                sql = "SELECT `ID` FROM `blog`.`article_list` WHERE `AUTHORID`='" + userId + "' AND `CATEGORY`='" + oldName + "'";
                rs = stm.executeQuery(sql);
                if (rs.next()) {
                    sql = "UPDATE `blog`.`article_list` SET `CATEGORY`='" + newName + "' WHERE `ID`='" + rs.getString("ID") + "'";
                    while (rs.next()) {
                        sql += " OR `ID`='" + rs.getString("ID") + "'";
                    }
                    stm.executeUpdate(sql);
                }
                
                //修改category
                sql = "SELECT `ID` FROM `blog`.`category_list` WHERE `AUTHORID`='" + userId + "' AND `CATEGORY`='" + oldName + "'";
                rs = stm.executeQuery(sql);
                rs.next();
                String id = rs.getString("ID");
                sql = "UPDATE `blog`.`category_list` SET `CATEGORY`='" + newName + "' WHERE `ID`='" + id + "'";
                if (stm.executeUpdate(sql) == 1) {
                    response.addHeader("Success", "true");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("deleting category");
        request.setCharacterEncoding("UTF-8");
        String userId = new String(request.getParameter("id").getBytes("ISO8859_1"),"UTF-8");
        String name = new String(request.getParameter("name").getBytes("ISO8859_1"),"UTF-8");
        System.out.println(name);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("error when loading jdbc");
            e.printStackTrace();
        }
        // connect database
        String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8";
        String DBusername = "root";
        String DBpassword = "Ma960904";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, DBusername, DBpassword);
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM `blog`.`category_list` WHERE `AUTHORID`='" + userId + "' AND `CATEGORY`='" + name + "'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {//存在该分类
                String id = rs.getString("ID");
                sql = "DELETE FROM `blog`.`category_list` WHERE `ID`='"+id+"'";
                if (stm.executeUpdate(sql) == 1) {
                    response.addHeader("Success", "true");
                }
            }
            con.close();
        } catch (SQLException se) {
        		se.printStackTrace();
        }
    }

    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("type").equals("edit")) {
            edit(request, response);
        } else {
            del(request,response);
        }
    }
}
