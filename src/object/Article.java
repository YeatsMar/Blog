package object;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Article {
	// data field
	public int id;
	public String content;
	public String title;
	public String category;
	public String time;
	public int authorId;
	public int read;

	// default constructor
	public Article() {
		content = new String();
		title = new String();
		category = new String();
		time = new String();
	}

	// constructor
	public Article(int id) {
		// articles
		// get the id of blogger to know to drawout whose article
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
			String sql = "SELECT * FROM `blog`.`article_list` WHERE `ID` = '"
					+ id + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			if (rs.next()) {// iteration
				authorId = rs.getInt("AUTHORID");
				title = rs.getString("TITLE");
				category = rs.getString("CATEGORY");
				time = rs.getString("TIME");
				content = rs.getString("CONTENT");
				read = rs.getInt("READ");
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article1");
			se.printStackTrace();
		}
	}

	// function for match the tag of the article
	public ArrayList<String> tags() {
		ArrayList<String> tagList = new ArrayList<String>();
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
			String sql = "SELECT * FROM `blog`.`article_tag` WHERE `ARTICLEID` = '"
					+ id + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				tagList.add(rs.getString("TAG"));// no volume limit for tag
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article3");
			se.printStackTrace();
		}
		return tagList;
	}

	// to get articles of a certain author and at the same time the certain
	// category
	public static ArrayList<Integer> getArticleCertain(String blogId,
			String category) {
		ArrayList<Integer> articleList = new ArrayList<Integer>();
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
					+ blogId + "' AND `CATEGORY`='" + category + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				articleList.add(rs.getInt("ID"));
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article4");
			se.printStackTrace();
		}
		return articleList;
	}

	// to get articles of a certain author
	public static ArrayList<Integer> getArticles(String blogId) {
		ArrayList<Integer> articleList = new ArrayList<Integer>();
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
			String sql = "SELECT * FROM `article_list` WHERE `AUTHORID` = '"
					+ blogId + "' ORDER BY `TIME` DESC";

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				articleList.add(rs.getInt("ID"));
			}
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article5");
			se.printStackTrace();
		}
		return articleList;
	}

	// abstract only a part of an article --used by Main.jsp
	public static ArrayList<String> abstractArticles(String blogId) {
		ArrayList<String> content = new ArrayList<String>();
		ArrayList<Integer> articleIdList = getArticles(blogId);

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
			for (int i = 0; i < articleIdList.size(); i++) {
				String sql = "SELECT left((SELECT `CONTENT` FROM `blog`.`article_list` where `ID` = '"
						+ articleIdList.get(i) + "'  ORDER BY `TIME` DESC), 100)";
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				if (rs.next()) {
					content.add(rs
							.getString("left((select `CONTENT` FROM `blog`.`article_list` where `ID` = '"
									+ articleIdList.get(i) + "'  ORDER BY `TIME` DESC), 100)"));
				}
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article5");
			se.printStackTrace();
		}
		return content;
	}

	// readAmount
	public void readAmount() {
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
			String sql = "UPDATE `blog`.`article_list` SET `READ`='" + read
					+ "' WHERE `ID`='" + this.id + "'";
			Statement stm = con.createStatement();
			int rs = stm.executeUpdate(sql);
			if (rs == 1) {
				System.out.println("readAmount++");
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article5");
			se.printStackTrace();
		}
	}

	public static void deleteArticle(String id) {
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
			String sql = "DELETE FROM `blog`.`article_list` WHERE `ID`='" + id
					+ "'";
			Statement stm = con.createStatement();
			int rs = stm.executeUpdate(sql);
			sql = "DELETE FROM `blog`.`article_tag` WHERE `ARTICLEID`='" + id + "'";
			int rs1 = stm.executeUpdate(sql);
			sql = "DELETE FROM `blog`.`comment_list` WHERE `ARTICLEID`='" + id + "'";
			int rs2 = stm.executeUpdate(sql);
			if (rs == 1 && rs1 == 1 && rs2 == 1) {
				System.out.println("delete article success");
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in Article6");
			se.printStackTrace();
		}
	}
}
