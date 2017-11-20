package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class SelfInfo
 */
@WebServlet("/SelfInfo")
@MultipartConfig(location="/Users/Mar/Documents/workspace/Blog/WebContent/pics/avatar")
public class SelfInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelfInfo() {
		super();
		// Auto-generated constructor stub
	}
        
        protected void upload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
            System.out.println("uploading file");
            
            String userId = (String) request.getSession().getAttribute("userId");
            
            for(Part part : request.getParts()) {
                
                part.write(userId+".jpg");
                System.out.println(userId+".jpg is saved");
            }
            System.out.println("uploading finished");
            response.sendRedirect("main2.jsp");
            
            
            
        }

	protected void getOldPassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get userId
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

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
			String sql = "SELECT * FROM `blog`.`user_list` WHERE `ID` = '"
					+ userId + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				String oldPassword = rs.getString("PASSWORD");
				String password = request.getParameter("newPassword");
				if (oldPassword.equals(password)) {
					response.addHeader("PasswordRepeat",
							"New password cannot be the same as old password!");
				}
				if (password == null){
					System.out.println("cannot get the new Password in SelfInfo.");
				}
			}
		} catch (SQLException se) {
			System.out.println("error when connecting database in SelfInfo1");
			se.printStackTrace();
		}
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String gender = request.getParameter("gender");
		String hobby = request.getParameter("hobby");
		String color = request.getParameter("color");
		String password = request.getParameter("newPassword");
		String selfIntroduction = request.getParameter("introduction");
		String signature = request.getParameter("signature");
		HttpSession session = request.getSession();// different from JSP
		String userId = (String) session.getAttribute("userId");

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
			Statement stm = con.createStatement();
			// to update
                        String sql=null;
                        if(request.getParameter("pass").equals("false")){
			sql = "UPDATE `blog`.`user_list` SET `USERNAME` = '" + username
					+ "', `GENDER` = '" + gender + "', `HOBBY` = '" + hobby
					+ "', `COLOR` = '" + color 
					+ "', `SELFINTRODUCTION` = '" + selfIntroduction
					+ "', `SIGNATURE` = '" + signature + "' WHERE `ID` = '"
					+ userId + "'";
                        } else {
                            sql="UPDATE `blog`.`user_list` SET `PASSWORD` = '" + password
					+ "' WHERE `ID` = '"
					+ userId + "'";
                        }
			int rs2 = stm.executeUpdate(sql);
			// 对结果集解析
			if (rs2 == 1) {
				System.out.println("succeed to update!");
				request.setAttribute("url", "main2.jsp");
				request.getRequestDispatcher("success.jsp").forward(request, response);
			} else {

			}
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database in SelfInfo2");
			se.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getHeader("Execute") != null) {
			getOldPassword(request, response);// first time
		} else {
			processRequest(request, response);// second time
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
            System.out.println(request.getContentType().toLowerCase());
                if(request.getContentType().toLowerCase().startsWith("multipart")){
                    upload(request,response);
                }
                else if (request.getHeader("Execute") != null) {
			getOldPassword(request, response);// send from JS
		} else {
			processRequest(request, response);// send from HTML
		}
	}

}
