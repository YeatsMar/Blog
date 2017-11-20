package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryService {
	// main function
	public static ArrayList<String> getCategories(String blogId) {//no problem
		// local variables
		ArrayList<String> categoryList = new ArrayList<String>();
		// loading JDBC
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
			// 进行查询
			String sql = "SELECT * FROM `blog`.`category_list` WHERE `AUTHORID`='" + blogId +"'";;// TODO
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				categoryList.add(rs.getString("CATEGORY"));
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in CategoryService1");
			se.printStackTrace();
		}
		return categoryList;
	}

	// function
	public static int getNumberOfArticle(String blogId, String category) {
		// local variables
		int number = 0;
		// loading JDBC
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
			// 进行查询
			String sql =  "SELECT * FROM `article_list` WHERE `AUTHORID`='" + blogId +"' AND `CATEGORY` = '" + category + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				number++;
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in CategoryService2");
			se.printStackTrace();
		}
		return number;
	}

	// function
	public static int getNumberAll(String blogId) {
		// local variables
		int number = 0;
		// loading JDBC
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
			// 进行查询
			String sql = "SELECT * FROM `blog`.`article_list` WHERE `AUTHORID` = '"
					+ blogId + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				number++;
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in CategoryService3");
			se.printStackTrace();
		}
		return number;
	}
}
