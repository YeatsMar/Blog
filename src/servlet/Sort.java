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
 * Servlet implementation class Sort
 */
@WebServlet("/Sort")
public class Sort extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sort() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sort = request.getParameter("sort");
		HttpSession session = request.getSession();
		ArrayList<Integer> articleIDs = (ArrayList<Integer>) session
				.getAttribute("articleIds");
		System.out.println(articleIDs);
		ArrayList<Integer> articleIDs2 = new ArrayList<Integer>();
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
			if (articleIDs.size() != 0) {
				String where = "`ID` = '" + articleIDs.get(0) + "'";
				for (int i = 1; i < articleIDs.size(); i++) {
					where = where + " or `ID` = '" + articleIDs.get(i) + "'";
				}
				Statement stm = con.createStatement();
				if (sort.equals("timeUp")) {
					sql = "SELECT * FROM `blog`.`article_list` WHERE " + where
							+ " ORDER BY `TIME` ASC";
				} else if (sort.equals("timeDown")) {
					sql = "SELECT * FROM `blog`.`article_list` WHERE " + where
							+ " ORDER BY `TIME` DESC;";
				}
				ResultSet rs = stm.executeQuery(sql);
				// 对结果集解析
				while (rs.next()) {// iteration
					int articleId = rs.getInt("ID");
					articleIDs2.add(articleId);
				}
				request.setAttribute("articleIDs", articleIDs2);
			} else {
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
