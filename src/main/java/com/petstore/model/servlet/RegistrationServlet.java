package com.petstore.model.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.petstore.connection.DbCon;
import com.petstore.dao.RegisterDao;
import com.petstore.model.User;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("full-name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setPassword(password); // Use plain text password

        try (Connection conn = DbCon.getConnection()) {
            RegisterDao registrationDao = new RegisterDao(conn);
            boolean isRegistered = registrationDao.registerUser(user);
            if (isRegistered) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
