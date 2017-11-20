package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserService {
	
	public static ArrayList<String> getUsers() {
		//the length of array need increase dynamically - ArrayList<datatype>
		ArrayList<String> users = new ArrayList<String>();
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
			String sql = "SELECT * FROM `blog`.`user_list`";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			for (int i = 0; rs.next(); i++) {// iteration
				users.add(rs.getString("USERNAME"));
			}
			//TODO release JDBC
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
		return users;
	}

	public static ArrayList<Integer> getUserIds() {
		//the length of array need increase dynamically - ArrayList<datatype>
		ArrayList<Integer> userIds = new ArrayList<Integer>();
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
			String sql = "SELECT * FROM `blog`.`user_list`";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			for (int i = 0; rs.next(); i++) {// iteration
				userIds.add(rs.getInt("ID"));
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
		return userIds;
	}
}
