/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import java.util.Date;
import java.text.SimpleDateFormat;
import object.Article;

@WebServlet("/Edit")
public class Edit extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
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
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// get parameter
		String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(new Date());
		String title = new String(request.getParameter("title").getBytes(
				"ISO-8859-1"), "UTF-8");
		String passage = new String(request.getParameter("passage").getBytes(
				"ISO-8859-1"), "UTF-8");
		String category = new String(request.getParameter("category").getBytes(
				"ISO-8859-1"), "UTF-8");
		String tags = new String(request.getParameter("tags").getBytes(
				"ISO-8859-1"), "UTF-8");
		String userId = (String) request.getSession().getAttribute("userId");
		String articleId = request.getParameter("articleId");
		if (articleId.equals("null")) {// articleId is null if new passage
			articleId = null;
		}
		System.out.println("processing new article " + title);
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
			String sql = null;
			if (articleId == null) {
				sql = "INSERT INTO `blog`.`article_list` (`TITLE`,`CONTENT`,`AUTHORID`,`CATEGORY`,`TIME`) VALUES('"
						+ title
						+ "','"
						+ passage
						+ "','"
						+ userId
						+ "','"
						+ category + "','" + time + "')";
			} else {
				sql = "UPDATE `blog`.`article_list` SET `TITLE`='" + title
						+ "',`CONTENT`='" + passage + "',`CATEGORY`='"
						+ category + "',`TIME`='" + time + "' WHERE `ID`='"
						+ articleId + "'";
			}
			Statement stm = con.createStatement();
			if (stm.executeUpdate(sql) != 1) {
				System.out.println("fail in inserting/updating the passage");
			}

			// 如果是修改，删除已有的tag
			if (articleId != null) {
				sql = "SELECT `ID` FROM `blog`.`article_tag` WHERE `ARTICLEID`='"
						+ articleId + "'";
				ResultSet rs = stm.executeQuery(sql);
				if (rs.next()) {
					sql = "DELETE FROM `blog`.`article_tag` WHERE `ID`='"
							+ rs.getString("ID") + "'";
					while (rs.next()) {
						sql += "OR `ID` = '" + rs.getString("ID") + "'";
					}
					stm.executeUpdate(sql);
				}
			}

			// 查询插入情况
			sql = "SELECT `ID` FROM `blog`.`article_list` WHERE (`TITLE`,`CONTENT`,`AUTHORID`,`CATEGORY`,`TIME`) =('"
					+ title
					+ "','"
					+ passage
					+ "','"
					+ userId
					+ "','"
					+ category + "','" + time + "')";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {// 如果成功插入，更新article_tag
				articleId = rs.getString("ID");
				String[] tag = tags.split(" ");
				for (String t : tag) {
					sql = "INSERT INTO `blog`.`article_tag` (`ARTICLEID`,`TAG`,`AUTHORID`) VALUES('"
							+ articleId + "','" + t + "','" + userId + "')";
					if (stm.executeUpdate(sql) != 1) {
						System.out.println("fail in inserting new tag " + t);
					}
				}
			}
			request.getSession().setAttribute("articleIDs",
					Article.getArticles(userId));
			request.setAttribute("url", "blog.jsp?articleId=" + articleId);
			request.getRequestDispatcher("success.jsp").forward(request,
					response);
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
	}

	private void createCategory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

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
		// get parameter
		String category = null;
		try {
			category = new String(request.getParameter("category").getBytes(
					"ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = request.getParameter("id");
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
			String sql = "SELECT * FROM `blog`.`category_list` WHERE `AUTHORID` = '"
					+ id + "' AND `CATEGORY` = '" + category + "'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				System.out.println("已经存在此分类！");
				response.addHeader("CreateStatus", "Error");
			} else {
				sql = "INSERT INTO `blog`.`category_list` (`AUTHORID`,`CATEGORY`) VALUES('"
						+ id + "','" + category + "')";
				int rs1 = stm.executeUpdate(sql);
				if (rs1 != 1) {
					System.out.println("failed in creating category!");
				}
			}
			con.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("create") != null) {
			createCategory(request, response);
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
		if (request.getParameter("create") != null) {
			createCategory(request, response);
		} else {
			processRequest(request, response);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
