package com.petstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.petstore.model.User;

public class RegisterDao {
    private Connection con;

    public RegisterDao(Connection con) {
        this.con = con;
    }

    public boolean registerUser(User user) {
        boolean status = false;
        String query = "INSERT INTO register (full_name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword()); // Store plain text password
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
