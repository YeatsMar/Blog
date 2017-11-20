package servlet;

import java.io.IOException;
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

import object.Article;
import object.Comment;
import service.LikeService;

/**
 * Servlet implementation class Blog
 */
@WebServlet("/Blog")
public class Blog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) comment function
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Like function
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("doPost is invoked");
		if (request.getHeader("addComment") != null) {// only execute when sent
														// by Ajax
			System.out.println("addComment is invoked");
			String articleId = (String) request.getParameter("articleId");
			String userId = (String) request.getParameter("userId");
			String comment = (String) request.getParameter("commentContent");
			System.out.println(articleId + userId + comment);
			Comment.addComment(articleId, userId, comment);
            response.addHeader("AddCommentSuccess", "true");
		} else if (request.getHeader("deleteComment") != null) {
			System.out.println("deleteComment is invoked");
			String id = (String) request.getParameter("id");
			System.out.println(id);
			Comment.deleteComment(id);
		} else if (request.getHeader("deleteArticle") != null){
			System.out.println("deleteArticle is invoked");
			String id = (String) request.getParameter("articleId");
			System.out.println(id);
			Article.deleteArticle(id);
			System.out.println("success in deleting");
//			// redirect
//			request.setAttribute("url", "blog_list.jsp");
//			request.getRequestDispatcher("success.jsp").forward(
//					request, response);
//			response.addHeader("DeleteArticleSuccess", "true");
		} else {//like
			System.out.println("doPost get no header");

			String userId = (String) request.getParameter("userId");
			String articleId = (String) request.getParameter("articleId");
			String isLike = (String) request.getParameter("isLike");// boolean
			Boolean isLike2 = Boolean.parseBoolean(isLike);

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
				String sql;
				Statement stm = con.createStatement();
				// 对结果集解析
				if (isLike2 == false) {// to like it
					sql = "INSERT INTO `like_list` (`USERID`, `ARTICLEID`) VALUES ('"
							+ userId + "', '" + articleId + "')";
					int rs2 = stm.executeUpdate(sql);
					if (rs2 == 1) {
						response.addHeader("LikeStatus", "like");
						System.out.println("succeed to like");
					} else {
						System.out.println("fail to like");
					}
				} else {// cancel like
					sql = "DELETE FROM `like_list` WHERE `ARTICLEID` = '"
							+ articleId + "' AND `USERID` = '" + userId + "'";
					int rs2 = stm.executeUpdate(sql);
					if (rs2 == 1) {
						response.addHeader("LikeStatus", "dislike");
						System.out.println("succeed to dislike");
					}
				}
				con.close();
			} catch (SQLException se) {
				System.out
						.println("error when connecting database in Blog.java.");
				se.printStackTrace();
			}
		}

	}

}
