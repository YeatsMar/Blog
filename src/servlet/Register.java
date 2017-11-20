package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void validateAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get parameters
		String account = request.getParameter("account");
		// loading JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("error when loading jdbc");
			e.printStackTrace();
		}
		// connect database
		String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8";// TODO
		String DBusername = "root";
		String DBpassword = "Ma960904";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
			String sql = "SELECT ACCOUNT FROM `blog`.`user_list` WHERE `ACCOUNT` = '"
					+ account + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				System.out.println("已经存在此用户！");// TODO new html
				response.addHeader("RegisterStatus", "User already exists!");
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get parameters
		String account = request.getParameter("account");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		int id = 0;

		// loading JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("error when loading jdbc");
			e.printStackTrace();
		}
		// connect database
		String url = "jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8";// TODO
		String DBusername = "root";
		String DBpassword = "Ma960904";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
			// ensure that the account doesn't exist
			String sql = "SELECT * FROM `blog`.`user_list` WHERE `ACCOUNT` = '"
					+ account + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				System.out.println("已经存在此用户！");
				response.addHeader("RegisterStatus", "User already exists!");// TODO
																				// new
																				// html
			} else {
				// input the info with the sql
				sql = "INSERT INTO `blog`.`user_list` (`USERNAME`, `PASSWORD`, `GENDER`, `ACCOUNT`) VALUES ('"
						+ username
						+ "', '"
						+ password
						+ "', '"
						+ gender
						+ "', '" + account + "')";

				int rs1 = stm.executeUpdate(sql);
				if (rs1 == 1) {
					System.out.println("succeed in inputing.");
					// get the userID
					sql = "SELECT * FROM `blog`.`user_list` WHERE `ACCOUNT` = '"
							+ account + "'";
					rs = stm.executeQuery(sql);
					if (rs.next()) {
						id = rs.getInt("ID");
					} else {
						System.out.println("error when get resultSet int!");
					}
					// set session attribute
					HttpSession session = request.getSession();
					String idStr = id + "";
					session.setAttribute("userId", idStr);
					// redirect
					response.sendRedirect("choice.jsp");
					// response.setContentType("text/html;charset=UTF-8");

				} else {
					System.out.println("Fail to input!");
				}
			}
			con.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getHeader("ValidateAccount") != null) {
			validateAccount(request, response);
		} else {
			processRequest(request, response);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getHeader("ValidateAccount") != null) {
			validateAccount(request, response);
		} else {
			processRequest(request, response);
		}
	}

}
