package object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

public class User {
	//data field - user_info
	public String username = null;
	public String id = null;
	public String password = null;
	public String gender = null;
	public String account = null;
	public String hobby = null;
	public String color = null;
	public String selfIntroduction = null;
	public String signature = null;
	
	// constructor
	public User(String id) {
		//to set information of this user
		this.id = id;
		
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
			String sql = "SELECT * FROM `blog`.`user_list` WHERE `ID` = '" + id + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			while (rs.next()) {// iteration
				username = rs.getString("USERNAME");
				password = rs.getString("PASSWORD");
				gender = rs.getString("GENDER");
				account = rs.getString("ACCOUNT");
				hobby = rs.getString("HOBBY");
				color = rs.getString("COLOR");
				selfIntroduction = rs.getString("SELFINTRODUCTION");
				signature = rs.getString("SIGNATURE");
			} 
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
	}
}
