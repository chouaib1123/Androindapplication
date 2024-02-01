package com.example.myapplication.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final int PORT = 3306;
    private static final String DB_NAME = "rentcardb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "admin";
    private static final String JDBC_URL = "jdbc:mysql://localhost:" + PORT + "/" + DB_NAME;

    public static Connection connect() {
        try {
            return DriverManager.getConnection(JDBC_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection Closed!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
