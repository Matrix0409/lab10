/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.UserAccount.User;
import Business.UserAccount.Librarian;
import Business.UserAccount.LibraryDirector;
import Business.UserAccount.SystemAdmin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    
    public LoginFrame() {
        initUI();
    }
    
    private void initUI() {
        setTitle("Library Management System");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(66, 134, 244);
                Color color2 = new Color(102, 187, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);
        
        JLabel lblIcon = new JLabel("📚");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setBounds(220, 30, 80, 80);
        mainPanel.add(lblIcon);
        
        JLabel lblTitle = new JLabel("Library System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 110, 500, 35);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Welcome back! Please login to continue");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(255, 255, 255, 200));
        lblSubtitle.setBounds(0, 145, 500, 20);
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblSubtitle);
        
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(50, 190, 400, 310);
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsername.setForeground(new Color(100, 100, 100));
        lblUsername.setBounds(30, 20, 340, 20);
        cardPanel.add(lblUsername);
        
        JTextField txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBounds(30, 45, 340, 40);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        cardPanel.add(txtUsername);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setForeground(new Color(100, 100, 100));
        lblPassword.setBounds(30, 100, 340, 20);
        cardPanel.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(30, 125, 340, 40);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        cardPanel.add(txtPassword);
        
        JLabel lblRole = new JLabel("Login As");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRole.setForeground(new Color(100, 100, 100));
        lblRole.setBounds(30, 180, 340, 20);
        cardPanel.add(lblRole);
        
        String[] roles = {"Member", "Librarian", "LibraryDirector", "SystemAdmin"};
        JComboBox<String> cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbRole.setBounds(30, 205, 340, 40);
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        cardPanel.add(cmbRole);
        
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBounds(30, 260, 340, 45);
        btnLogin.setBackground(new Color(66, 134, 244));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(51, 119, 229));
            }
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(66, 134, 244));
            }
        });
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(txtUsername, txtPassword, cmbRole);
            }
        });
        
        cardPanel.add(btnLogin);
        mainPanel.add(cardPanel);
        
        add(mainPanel);
    }
    
    private void handleLogin(JTextField txtUsername, JPasswordField txtPassword, JComboBox<String> cmbRole) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) cmbRole.getSelectedItem();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter username and password", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = createUser(username, password, role);
        
        if (user != null && user.login()) {
            JOptionPane.showMessageDialog(this,
                "Welcome " + username + "!",
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            openDashboard(user);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials or role!", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
    
    private User createUser(String username, String password, String role) {
        switch (role) {
            case "Member":
                return new Business.UserAccount.Member(username, password, "");
            case "Librarian":
                return new Librarian(username, password, "");
            case "LibraryDirector":
                return new LibraryDirector(username, password, "");
            case "SystemAdmin":
                return new SystemAdmin(username, password, "");
            default:
                return null;
        }
    }
    
    private void openDashboard(User user) {
        if (user instanceof Business.UserAccount.Member member) {
            MemberDashboard dashboard = new MemberDashboard(member);
            dashboard.setVisible(true);
        } else if (user instanceof Librarian) {
            LibrarianDashboard dashboard = new LibrarianDashboard((Librarian) user);
            dashboard.setVisible(true);
        } else if (user instanceof LibraryDirector) {
            DirectorDashboard dashboard = new DirectorDashboard((LibraryDirector) user);
            dashboard.setVisible(true);
        } else if (user instanceof SystemAdmin) {
            AdminDashboard dashboard = new AdminDashboard((SystemAdmin) user);
            dashboard.setVisible(true);
        }
    }
}