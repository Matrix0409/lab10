/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import UI.LoginFrame;
import Database.Database;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Initialize database connection
        Database db = Database.getInstance();
        
        // Launch UI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}