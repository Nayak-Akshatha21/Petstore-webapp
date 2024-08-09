

package com.petstore.model.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.petstore.connection.DbCon;
import com.petstore.dao.UserDao;
import com.petstore.model.User;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("login-email");
            String password = request.getParameter("login-password");

            try (Connection conn = DbCon.getConnection()) {
                UserDao udao = new UserDao(conn);
                User user = udao.userLogin(email, password);
                if (user != null) {
                    request.getSession().setAttribute("auth", user);
                    response.sendRedirect("index.jsp"); // Redirect to home page
                } else {
                    out.println("Invalid email or password.");
                    response.sendRedirect("login.jsp"); // Redirect back to login page with error
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp"); // Handle the error appropriately
            }
        }
    }
}
