package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		String mode = request.getParameter("mode");
		HttpSession session = request.getSession();
		String blogId = (String) session.getAttribute("blogId");
		String scope = request.getParameter("scope");
		System.out.println(key);
		System.out.println(mode);
		System.out.println(scope);
		ArrayList<Integer> articleIDs = new ArrayList<Integer>();

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
			String sql = null;
			Statement stm = con.createStatement();
			if(!(mode.equals("tag"))) {
				if (scope.equals("one")) {
					if (mode.equals("content")) {
						sql = "SELECT * FROM `blog`.`article_list` WHERE `CONTENT` like '%"
								+ key + "%' AND AUTHORID = '" + blogId + "'";
					} else if(mode.equals("title")) {
						sql = "SELECT * FROM `blog`.`article_list` WHERE `TITLE` like '%"
								+ key + "%' AND AUTHORID = '" + blogId + "'";
					} else {
						System.out.println("error1");
					}
				}
				else if (scope.equals("whole")){
					if (mode.equals("content")) {
						sql = "SELECT * FROM `blog`.`article_list` WHERE `CONTENT` like '%"
								+ key + "%'";
					} else if(mode.equals("title")) {
						sql = "SELECT * FROM `blog`.`article_list` WHERE `TITLE` like '%"
								+ key + "%'";
					} else {
						System.out.println("error2");
					}
				}
				ResultSet rs = stm.executeQuery(sql);
				// 对结果集解析
				while (rs.next()) {// iteration
					int articleId = rs.getInt("ID");
					articleIDs.add(articleId);
				} 
				request.setAttribute("articleIDs", articleIDs);
			} 
			else {
				if (scope.equals("one")) {
					sql = "SELECT * FROM `blog`.`article_tag` WHERE `TAG` like '%"
							+ key + "%' AND AUTHORID = '" + blogId + "'";
				}
				else if(mode.equals("tag")){
					sql = "SELECT * FROM `blog`.`article_tag` WHERE `TAG` like '%"
							+ key + "%'";
				}
				else {
					System.out.println("error3");
				}
				ResultSet rs = stm.executeQuery(sql);
				// 对结果集解析
				while (rs.next()) {// iteration
					int articleId = rs.getInt("ARTICLEID");
					articleIDs.add(articleId);
				}
				request.setAttribute("articleIDs", articleIDs);
			}
			con.close();
		} catch (SQLException se) {
			System.out.println("error when connecting database");
			se.printStackTrace();
		}
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

}
