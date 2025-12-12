
package UI;

import Business.UserAccount.LibraryDirector;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class DirectorDashboard extends JFrame {
    private LibraryDirector director;
    private JTable table;
    private DefaultTableModel model;
    
    public DirectorDashboard(LibraryDirector director) {
        this.director = director;
        initUI();
        loadRequests();
    }
    
    private void initUI() {
        setTitle("Director Dashboard - " + director.getUsername());
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        // Header
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(1200, 100));
        header.setBackground(new Color(155, 89, 182));
        
        JLabel welcome = new JLabel("Welcome, " + director.getUsername());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(40, 20, 500, 35);
        header.add(welcome);
        
        JLabel role = new JLabel("Library Director • Strategic Management");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        role.setForeground(new Color(255, 255, 255, 180));
        role.setBounds(40, 55, 400, 25);
        header.add(role);
        
        JButton logout = createHeaderButton("Logout", 1070);
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(logout);
        
        main.add(header, BorderLayout.NORTH);
        
        // Stats dashboard
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        stats.setBackground(Color.WHITE);
        stats.setPreferredSize(new Dimension(1200, 120));
        stats.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        
        stats.add(createStatCard("⏳ Pending Requests", "12", new Color(241, 196, 15)));
        stats.add(createStatCard("👥 Active Members", "245", new Color(46, 204, 113)));
        stats.add(createStatCard("📚 Total Books", "1,523", new Color(52, 152, 219)));
        stats.add(createStatCard("👨‍💼 Staff", "8", new Color(155, 89, 182)));
        stats.add(createStatCard("📊 This Month", "+34", new Color(26, 188, 156)));
        
        main.add(stats, BorderLayout.PAGE_START);
        
        // Table
        String[] columns = {"ID", "Type", "Requester", "Date", "Description", "Status"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(155, 89, 182, 50));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(240, 240, 240));
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setBackground(new Color(248, 249, 250));
        tableHeader.setForeground(new Color(73, 80, 87));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 45));
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        main.add(scroll, BorderLayout.CENTER);
        
        // Bottom buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 20));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        JButton approve = createActionButton("✅ Approve", new Color(46, 204, 113), 140);
        approve.addActionListener(e -> approveRequest());
        
        JButton reject = createActionButton("❌ Reject", new Color(231, 76, 60), 130);
        reject.addActionListener(e -> rejectRequest());
        
        JButton acquisition = createActionButton("📦 Request Acquisition", new Color(52, 152, 219), 190);
        acquisition.addActionListener(e -> requestAcquisition());
        
        JButton staff = createActionButton("👥 Manage Staff", new Color(241, 196, 15), 150);
        staff.addActionListener(e -> manageStaff());
        
        JButton reports = createActionButton("📊 View Reports", new Color(26, 188, 156), 150);
        reports.addActionListener(e -> viewReports());
        
        // Add Logout Button
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

        
        bottom.add(approve);
        bottom.add(reject);
        bottom.add(acquisition);
        bottom.add(staff);
        bottom.add(reports);
        bottom.add(logoutBtn);
        
        main.add(bottom, BorderLayout.SOUTH);
        add(main);
    }
    
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(210, 70));
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
        btn.setPreferredSize(new Dimension(width, 45));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadRequests() {
        model.setRowCount(0);
        
        // Sample data
        Object[][] data = {
            {1, "Membership Approval", "John Doe", "2024-12-10", "New member registration", "Pending"},
            {2, "Book Checkout", "Jane Smith", "2024-12-11", "Clean Code request", "Pending"},
            {3, "ILL Request", "Community Lib", "2024-12-12", "From Central Library", "Pending"}
        };
        
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
    
    private void approveRequest() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String type = model.getValueAt(row, 1).toString();
        int result = JOptionPane.showConfirmDialog(this, "Approve: " + type + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            model.setValueAt("Approved", row, 5);
            JOptionPane.showMessageDialog(this, "✓ Request approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void rejectRequest() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String reason = JOptionPane.showInputDialog(this, "Reason for rejection:");
        if (reason != null && !reason.trim().isEmpty()) {
            model.setValueAt("Rejected", row, 5);
            JOptionPane.showMessageDialog(this, "Request rejected", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void requestAcquisition() {
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextArea reason = new JTextArea(3, 20);
        reason.setLineWrap(true);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Book Title:"));
        panel.add(title);
        panel.add(new JLabel("Author:"));
        panel.add(author);
        panel.add(new JLabel("Reason:"));
        panel.add(new JScrollPane(reason));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Request from Central Library", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "✓ Acquisition request sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void manageStaff() {
        JOptionPane.showMessageDialog(this, "Staff management - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void viewReports() {
        String[] reports = {"Monthly Circulation", "Member Activity", "Book Inventory", "Financial Report", "Overdue Books"};
        String selected = (String) JOptionPane.showInputDialog(this, "Select report:", "Reports", 
            JOptionPane.QUESTION_MESSAGE, null, reports, reports[0]);
        
        if (selected != null) {
            JOptionPane.showMessageDialog(this, "Generating: " + selected, "Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}