//package com.petstore.connection;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DbCon {
//	private static Connection connection=null;
//	 
//	public static Connection getConnection() throws ClassNotFoundException, SQLException {
//		if(connection==null){
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/petstore","root","Ak@21042004");
//		    System.out.println("connected");
//		}
//		return connection;
//	}
//
//}



package com.petstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCon {

    private static final String URL = "jdbc:mysql://localhost:3306/petstore";
    private static final String USER = "root";
    private static final String PASSWORD = "*****";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}




