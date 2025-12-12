/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database instance;
    private Connection conn;
    
    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/library_db";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✓ Database connected successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("✗ Database connection failed!");
            e.printStackTrace();
        }
    }
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public ResultSet executeQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int executeUpdate(String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }
}