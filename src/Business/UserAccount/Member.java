/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.UserAccount;

import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Member extends User {
    
    public Member() {
        super();
    }
    
    public Member(String username, String password, String email) {
        super(username, password, email, "Member");
    }
    
    @Override
    public boolean login() {
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM User WHERE username = ? AND password = ? AND role = 'Member'";
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
        System.out.println("Member logged out");
    }
}