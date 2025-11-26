/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author USER
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane; 

public class DBUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/student_managment_systemv3_db";
    private static final String USER = "postgres"; // Your PostgreSQL username
    private static final String PASSWORD = "12345"; // !!! ENTER YOUR ACTUAL PASSWORD !!!

    
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly loads the PostgreSQL JDBC driver class
            Class.forName("org.postgresql.Driver"); 
        } catch (ClassNotFoundException e) {
            // This error occurs if the 'postgresql-42.x.x.jar' file is missing from your project's Libraries/classpath.
            JOptionPane.showMessageDialog(null, 
                "Error: The PostgreSQL JDBC Driver was not found. Check that the .jar file is in your Libraries.", 
                "DB Configuration Error", JOptionPane.ERROR_MESSAGE);
            throw new SQLException("PostgreSQL Driver not found.");
        }

        // Attempt to establish the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // If closing fails, display a message
                JOptionPane.showMessageDialog(null, 
                    "Error closing DB connection: " + e.getMessage(), 
                    "DB Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
