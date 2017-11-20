package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import object.User;

import com.sun.glass.ui.Window;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String passwordTrue = null;
		String userId = null;

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
			String sql = "SELECT * FROM `blog`.`user_list` WHERE `ACCOUNT` = '"
					+ account + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			// 对结果集解析
			if (rs.next()) {// iteration
				passwordTrue = rs.getString("PASSWORD");
				userId = rs.getString("ID");
				if (password.equals(passwordTrue)) {// cannot use ==
					// set session attribute
					HttpSession session = request.getSession();
					session.setAttribute("userId", userId);
					// redirect
					request.setAttribute("url", "choice.jsp");
					request.getRequestDispatcher("success.jsp").forward(
							request, response);
				} else {
					response.addHeader("LoginStatus", "Error");
				}
				con.close();
			} else {
				System.out
						.println("Mistake in account or password! This user does not exist.");
				response.addHeader("LoginStatus", "Error");
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}

	}
	
	protected void visit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = (String)request.getParameter("userId");
//		System.out.println(userId);
		// set session attribute
		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
//		String test = (String)session.getAttribute("userId");
//		System.out.println(test);
		// redirect
		request.setAttribute("url", "choice.jsp");
//		response.sendRedirect("choice.jsp");
//		request.getRequestDispatcher("success.jsp").forward(request, response);
		response.addHeader("VisitStatus", "iadhaiohd");
		response.sendRedirect("choice.jsp");
		
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(request.getHeader("Visit")!=null){
            visit(request,response);
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
		if (request.getHeader("Visit") != null) {
			visit(request, response);
		} else {
			processRequest(request, response);
		}
	}

}
