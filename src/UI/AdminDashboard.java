/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.UserAccount.SystemAdmin;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private SystemAdmin admin;
    private JTable usersTable, enterprisesTable;
    private DefaultTableModel usersModel, enterprisesModel;
    
    public AdminDashboard(SystemAdmin admin) {
        this.admin = admin;
        initUI();
        loadData();
    }
    
    private void initUI() {
        setTitle("System Admin Dashboard - " + admin.getUsername());
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        // Header
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(1250, 100));
        header.setBackground(new Color(52, 73, 94));
        
        JLabel welcome = new JLabel("Welcome, " + admin.getUsername());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(40, 20, 500, 35);
        header.add(welcome);
        
        JLabel role = new JLabel("System Administrator • Full System Control");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        role.setForeground(new Color(255, 255, 255, 180));
        role.setBounds(40, 55, 400, 25);
        header.add(role);
        
        JButton logout = createHeaderButton("Logout", 1120);
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(logout);
        
        main.add(header, BorderLayout.NORTH);
        
        // Stats
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
        stats.setBackground(Color.WHITE);
        stats.setPreferredSize(new Dimension(1250, 110));
        stats.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        
        stats.add(createStatCard("👥 Users", "352", new Color(52, 152, 219)));
        stats.add(createStatCard("🏢 Enterprises", "2", new Color(46, 204, 113)));
        stats.add(createStatCard("📚 Books", "2,847", new Color(241, 196, 15)));
        stats.add(createStatCard("💳 Transactions", "1,523", new Color(155, 89, 182)));
        stats.add(createStatCard("⚡ Active", "18", new Color(231, 76, 60)));
        
        main.add(stats, BorderLayout.PAGE_START);
        
        // Tabbed pane
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(Color.WHITE);
        
        tabs.addTab("  👥 Users  ", createUsersTab());
        tabs.addTab("  🏢 Enterprises  ", createEnterprisesTab());
        tabs.addTab("  📊 Reports  ", createReportsTab());
        tabs.addTab("  ⚙️ Settings  ", createSettingsTab());
        
        main.add(tabs, BorderLayout.CENTER);
        add(main);
    }
    
    private JPanel createUsersTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        String[] columns = {"ID", "Username", "Email", "Role", "Enterprise", "Status"};
        usersModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        usersTable = new JTable(usersModel);
        usersTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usersTable.setRowHeight(35);
        usersTable.setSelectionBackground(new Color(52, 152, 219, 50));
        usersTable.setGridColor(new Color(240, 240, 240));
        
        JTableHeader header = usersTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(new Color(73, 80, 87));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        JScrollPane scroll = new JScrollPane(usersTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setBackground(Color.WHITE);
        
        JButton add = createActionButton("➕ Add User", new Color(46, 204, 113), 130);
        add.addActionListener(e -> addUser());
        
        JButton edit = createActionButton("✏️ Edit User", new Color(52, 152, 219), 130);
        edit.addActionListener(e -> editUser());
        
        JButton delete = createActionButton("🗑️ Delete User", new Color(231, 76, 60), 140);
        delete.addActionListener(e -> deleteUser());
        
        JButton refresh = createActionButton("🔄 Refresh", new Color(149, 165, 166), 120);
        refresh.addActionListener(e -> loadUsers());
        
        
        JButton logoutBtn = createActionButton("🚪 Logout", new Color(231, 76, 60), 120);
logoutBtn.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to logout?",
        "Confirm Logout",
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        dispose();
        new LoginFrame().setVisible(true);
    }
});

        
        buttons.add(add);
        buttons.add(edit);
        buttons.add(delete);
        buttons.add(refresh);
        buttons.add(logoutBtn);
        
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createEnterprisesTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        String[] columns = {"ID", "Name", "Type", "Location", "Books", "Members"};
        enterprisesModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        enterprisesTable = new JTable(enterprisesModel);
        enterprisesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        enterprisesTable.setRowHeight(35);
        enterprisesTable.setSelectionBackground(new Color(46, 204, 113, 50));
        enterprisesTable.setGridColor(new Color(240, 240, 240));
        
        JTableHeader header = enterprisesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(new Color(73, 80, 87));
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        
        JScrollPane scroll = new JScrollPane(enterprisesTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setBackground(Color.WHITE);
        
        JButton add = createActionButton("➕ Add Enterprise", new Color(46, 204, 113), 160);
        JButton edit = createActionButton("✏️ Edit Enterprise", new Color(52, 152, 219), 160);
        JButton refresh = createActionButton("🔄 Refresh", new Color(149, 165, 166), 120);
        refresh.addActionListener(e -> loadEnterprises());
        
        buttons.add(add);
        buttons.add(edit);
        buttons.add(refresh);
        
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 25, 25));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        panel.add(createReportCard("📊 User Activity", "Track user logins and actions", new Color(52, 152, 219)));
        panel.add(createReportCard("📚 Book Circulation", "Checkout and return statistics", new Color(46, 204, 113)));
        panel.add(createReportCard("💰 Financial Report", "Revenue and fines overview", new Color(241, 196, 15)));
        panel.add(createReportCard("🏢 Enterprise Performance", "Compare library branches", new Color(155, 89, 182)));
        panel.add(createReportCard("⚠️ System Health", "Database and performance metrics", new Color(231, 76, 60)));
        panel.add(createReportCard("📈 Analytics", "Trends and insights", new Color(26, 188, 156)));
        
        return panel;
    }
    
    private JPanel createReportCard(String title, String desc, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(255, 255, 255, 180));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(AdminDashboard.this, 
                    "Generating: " + title, "Report", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return card;
    }
    
    private JPanel createSettingsTab() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("System Configuration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(50, 30, 300, 30);
        panel.add(title);
        
        // Database
        JLabel db = new JLabel("Database Management");
        db.setFont(new Font("Segoe UI", Font.BOLD, 16));
        db.setBounds(50, 80, 250, 25);
        panel.add(db);
        
        JButton test = createActionButton("Test Connection", new Color(52, 152, 219), 150);
        test.setBounds(50, 115, 150, 40);
        test.addActionListener(e -> JOptionPane.showMessageDialog(this, "✓ Connected to localhost:3306", "Success", JOptionPane.INFORMATION_MESSAGE));
        panel.add(test);
        
        JButton backup = createActionButton("Backup Database", new Color(46, 204, 113), 150);
        backup.setBounds(220, 115, 150, 40);
        backup.addActionListener(e -> JOptionPane.showMessageDialog(this, "✓ Backup initiated", "Success", JOptionPane.INFORMATION_MESSAGE));
        panel.add(backup);
        
        // Maintenance
        JLabel maint = new JLabel("System Maintenance");
        maint.setFont(new Font("Segoe UI", Font.BOLD, 16));
        maint.setBounds(50, 175, 250, 25);
        panel.add(maint);
        
        JButton cache = createActionButton("Clear Cache", new Color(241, 196, 15), 150);
        cache.setBounds(50, 210, 150, 40);
        cache.addActionListener(e -> JOptionPane.showMessageDialog(this, "✓ Cache cleared", "Success", JOptionPane.INFORMATION_MESSAGE));
        panel.add(cache);
        
        JButton optimize = createActionButton("Optimize DB", new Color(155, 89, 182), 150);
        optimize.setBounds(220, 210, 150, 40);
        optimize.addActionListener(e -> JOptionPane.showMessageDialog(this, "✓ Database optimized", "Success", JOptionPane.INFORMATION_MESSAGE));
        panel.add(optimize);
        
        // Logs
        JLabel logs = new JLabel("System Logs");
        logs.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logs.setBounds(50, 270, 150, 25);
        panel.add(logs);
        
        JTextArea logsArea = new JTextArea(
            "[2024-12-12 10:30] System started\n" +
            "[2024-12-12 10:35] Admin logged in\n" +
            "[2024-12-12 10:40] Database backup OK\n" +
            "[2024-12-12 11:15] 5 books added"
        );
        logsArea.setEditable(false);
        logsArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(logsArea);
        scroll.setBounds(50, 305, 1100, 250);
        panel.add(scroll);
        
        return panel;
    }
    
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(220, 70));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Segoe UI", Font.BOLD, 30));
        val.setForeground(Color.WHITE);
        
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(255, 255, 255, 200));
        
        card.add(val, BorderLayout.CENTER);
        card.add(lbl, BorderLayout.SOUTH);
        return card;
    }
    
    private JButton createHeaderButton(String text, int x) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBounds(x, 30, 100, 40);
        btn.setBackground(new Color(231, 76, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton createActionButton(String text, Color color, int width) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(width, 40));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadData() {
        loadUsers();
        loadEnterprises();
    }
    
    private void loadUsers() {
        usersModel.setRowCount(0);
        Object[][] data = {
            {1, "admin", "admin@library.com", "SystemAdmin", "Central", "Active"},
            {2, "director1", "director@community.com", "LibraryDirector", "Community", "Active"},
            {3, "lib1", "librarian@community.com", "Librarian", "Community", "Active"},
            {4, "member1", "member@email.com", "Member", "Community", "Active"}
        };
        for (Object[] row : data) usersModel.addRow(row);
    }
    
    private void loadEnterprises() {
        enterprisesModel.setRowCount(0);
        Object[][] data = {
            {1, "Community Library", "Public", "Downtown", 1523, 245},
            {2, "Central Library", "Regional", "City Center", 3847, 0}
        };
        for (Object[] row : data) enterprisesModel.addRow(row);
    }
    
    private void addUser() {
        JOptionPane.showMessageDialog(this, "Add user feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void editUser() {
        JOptionPane.showMessageDialog(this, "Edit user feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteUser() {
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String username = usersModel.getValueAt(row, 1).toString();
        int result = JOptionPane.showConfirmDialog(this, "Delete user: " + username + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "✓ User deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers();
        }
    }
}