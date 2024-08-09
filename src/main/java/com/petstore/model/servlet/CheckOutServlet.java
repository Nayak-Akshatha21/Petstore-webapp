package com.petstore.model.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import  com.petstore.connection.DbCon;
import  com.petstore.dao.OrderDao;
import  com.petstore.model.*;

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
        User auth = (User) request.getSession().getAttribute("auth");

        if (auth == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (cart_list == null || cart_list.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            OrderDao oDao = new OrderDao(DbCon.getConnection());

            for (Cart c : cart_list) {
                Order order = new Order();
                order.setId(c.getId());
                order.setUid(auth.getId());
                order.setQunatity(c.getQuantity());
                order.setDate(formatter.format(date));

                boolean result = oDao.insertOrder(order);
                if (!result) {
                    response.sendRedirect("error.jsp");
                    return;
                }
            }

            cart_list.clear();
            response.sendRedirect("orders.jsp");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
