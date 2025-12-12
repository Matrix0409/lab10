/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.UserAccount;

import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDirector extends User {
    
    public LibraryDirector() {
        super();
    }
    
    public LibraryDirector(String username, String password, String email) {
        super(username, password, email, "LibraryDirector");
    }
    
    @Override
    public boolean login() {
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM User WHERE username = ? AND password = ? AND role = 'LibraryDirector'";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.password);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.userId = rs.getInt("userId");
                this.email = rs.getString("email");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void logout() {
        System.out.println("Library Director " + username + " logged out.");
    }
}
