package com.kicks.inventory.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection configure(){
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Unable to connect to Database.");
            e.printStackTrace();
        }

        return null;
    }
}
