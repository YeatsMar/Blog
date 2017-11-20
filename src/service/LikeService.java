package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

public class LikeService {
	// get the number of LIKE
	public static int getLikeNumber(String articleId) {
		int number = 0;

		// loading JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("error when loading jdbc");
			e.printStackTrace();
		}
		// connect database
		String url = "jdbc:mysql://localhost:3306/blog";// TODO?useUnicode=true&characterEncoding=UTF-8
		String DBusername = "root";
		String DBpassword = "Ma960904";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
			// 进行查询
			String sql = "SELECT * FROM `blog`.`like_list` WHERE `ARTICLEID` = '"
					+ articleId + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {
				number++;
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
		return number;
	}

	
	public static boolean isLike(String userId, String articleId) {
		boolean islike = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("error when loading jdbc");
			e.printStackTrace();
		}
		// connect database
		String url = "jdbc:mysql://localhost:3306/blog";// TODO?useUnicode=true&characterEncoding=UTF-8
		String DBusername = "root";
		String DBpassword = "Ma960904";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
			// 进行查询
			String sql = "SELECT * FROM `blog`.`like_list` WHERE `ARTICLEID` = '"
					+ articleId + "' AND `USERID` = '" + userId + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			if (rs.next()) {
				islike = true;
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
		return islike;
	}
}
