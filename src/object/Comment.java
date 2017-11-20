package object;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Comment {
	public int id;
	public int articleId;
	public int userId;
	public String comment;
	public String time;
	public String username;

	// constructor
	public Comment(int id) {
		this.id = id;

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
			String sql = "SELECT * FROM `blog`.`comment_list` WHERE `ID` = '"
					+ id + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			if (rs.next()) {
				articleId = rs.getInt("ARTICLEID");
				userId = rs.getInt("USERID");
				comment = rs.getString("COMMENT");
				time = rs.getString("TIME");
			} else {
				System.out.println("no result set in Comment.java1");
			}
			sql = "SELECT * FROM `blog`.`user_list` WHERE `ID` = '" + userId
					+ "'";
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				username = rs.getString("USERNAME");
			} else {
				System.out.println("no result set in Comment.java2");
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Comment");
			se.printStackTrace();
		}
	}

	// to get articles of a certain author
	public static ArrayList<Integer> getComments(String articleId) {
		ArrayList<Integer> IDList = new ArrayList<Integer>();
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
			String sql = "SELECT * FROM `comment_list` WHERE `ARTICLEID` = '"
					+ articleId + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				IDList.add(rs.getInt("ID"));
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database in Comment1");
			se.printStackTrace();
		}
		return IDList;
	}

	//above are right
	public static void addComment(String articleId, String userId, String comment) {
		
		System.out.println(articleId + "\n" + userId + "\n" + comment);
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
			String sql = "INSERT INTO `blog`.`comment_list` (`ARTICLEID`, `USERID`, `COMMENT`, `TIME`) VALUES ('"
					+ articleId
					+ "', '"
					+ userId
					+ "', '"
					+ comment
					+ "', now())";
			Statement stm = con.createStatement();
			int rs = stm.executeUpdate(sql);
			// 对结果集解析
			if (rs == 1) {
				System.out.println("comment success!");
			} else {
				System.out.println("mistake in inserting comment");
			}
		} catch (SQLException se) {
			System.out
					.println("error when connecting database in Comment.java");
			se.printStackTrace();
		}
	}

	public static void deleteComment(String id) {
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
			String sql = "DELETE FROM `blog`.`comment_list` WHERE `ID`='" + id +"'";
			Statement stm = con.createStatement();
			int rs = stm.executeUpdate(sql);
			// 对结果集解析
			if (rs == 1) {
				System.out.println("\n\n\n delete comment success! \n\n\n");
			} else {
				System.out.println("\n\n\n mistake in deleteing comment \n\n\n");
			}
		} catch (SQLException se) {
			System.out
					.println("error when connecting database in Comment.java");
			se.printStackTrace();
		}
	}
}
