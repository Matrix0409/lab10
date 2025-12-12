/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.UserAccount.Member;
import Business.Book.Book;
import Business.Book.BookDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MemberDashboard extends JFrame {
    private Member member;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    
    public MemberDashboard(Member member) {
        this.member = member;
        initUI();
        loadBooks();
    }
    
    private void initUI() {
        setTitle("Member Dashboard - " + member.getUsername());
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        // Header
      // Header
JPanel header = new JPanel(new BorderLayout());
header.setPreferredSize(new Dimension(1100, 100));
header.setBackground(new Color(52, 152, 219));

JPanel leftPanel = new JPanel();
leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
leftPanel.setBackground(new Color(52, 152, 219));
leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 20));

JLabel welcome = new JLabel("Welcome, " + member.getUsername());
welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
welcome.setForeground(Color.WHITE);
leftPanel.add(welcome);

JLabel roleLabel = new JLabel("Member • Book Catalog & Requests");
roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
roleLabel.setForeground(new Color(255, 255, 255, 180));
leftPanel.add(roleLabel);

JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
rightPanel.setBackground(new Color(52, 152, 219));


        
        // Search panel
        JPanel searchPanel = new JPanel(null);
        searchPanel.setPreferredSize(new Dimension(1100, 80));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        searchIcon.setBounds(40, 25, 30, 30);
        searchPanel.add(searchIcon);
        
        searchField = new JTextField("Search by title, author, ISBN...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.GRAY);
        searchField.setBounds(80, 20, 700, 40);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().startsWith("Search")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search by title, author, ISBN...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        searchPanel.add(searchField);
        
        JButton searchBtn = createActionButton("Search", new Color(52, 152, 219));
        searchBtn.setBounds(800, 20, 120, 40);
        searchBtn.addActionListener(e -> searchBooks());
        searchPanel.add(searchBtn);
        
        JButton clearBtn = createActionButton("Clear", new Color(149, 165, 166));
        clearBtn.setBounds(940, 20, 120, 40);
        clearBtn.addActionListener(e -> {
            searchField.setText("Search by title, author, ISBN...");
            searchField.setForeground(Color.GRAY);
            loadBooks();
        });
        searchPanel.add(clearBtn);
        
        main.add(searchPanel, BorderLayout.PAGE_START);
        
        // Table
        String[] columns = {"ID", "ISBN", "Title", "Author", "Category", "Available"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(52, 152, 219, 50));
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
        
        // Bottom panel
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        JButton checkout = createActionButton("📖 Request Checkout", new Color(46, 204, 113));
        checkout.setPreferredSize(new Dimension(180, 45));
        checkout.addActionListener(e -> requestCheckout());
        
        JButton history = createActionButton("📚 My History", new Color(155, 89, 182));
        history.setPreferredSize(new Dimension(150, 45));
        history.addActionListener(e -> viewHistory());
        
        JButton refresh = createActionButton("🔄 Refresh", new Color(52, 152, 219));
        refresh.setPreferredSize(new Dimension(130, 45));
        refresh.addActionListener(e -> loadBooks());
        
        // Add Logout Button
JButton logoutBtn = createActionButton("🚪 Logout", new Color(231, 76, 60));
logoutBtn.setPreferredSize(new Dimension(120, 45));
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
        
        bottom.add(checkout);
        bottom.add(history);
        bottom.add(refresh);
        bottom.add(logoutBtn);
        
        main.add(bottom, BorderLayout.SOUTH);
        add(main);
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
    
    private JButton createActionButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void loadBooks() {
        model.setRowCount(0);
        List<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            model.addRow(new Object[]{
                b.getBookId(), b.getIsbn(), b.getTitle(), 
                b.getAuthor(), b.getCategory(), 
                b.getAvailableCopies() + "/" + b.getTotalCopies()
            });
        }
    }
    
    private void searchBooks() {
        String term = searchField.getText().trim().toLowerCase();
        if (term.isEmpty() || term.startsWith("Search")) {
            loadBooks();
            return;
        }
        
        
        
        model.setRowCount(0);
        List<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(term) ||
                b.getAuthor().toLowerCase().contains(term) ||
                b.getIsbn().toLowerCase().contains(term) ||
                b.getCategory().toLowerCase().contains(term)) {
                model.addRow(new Object[]{
                    b.getBookId(), b.getIsbn(), b.getTitle(), 
                    b.getAuthor(), b.getCategory(), 
                    b.getAvailableCopies() + "/" + b.getTotalCopies()
                });
            }
        }
    }
    
    private void requestCheckout() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String title = model.getValueAt(row, 2).toString();
        int result = JOptionPane.showConfirmDialog(this, 
            "Request checkout for:\n" + title + "?", 
            "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "✓ Checkout request sent to librarian!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

private void viewHistory() {
    JOptionPane.showMessageDialog(this, 
        "Transaction history feature coming soon!", 
        "My History", JOptionPane.INFORMATION_MESSAGE);
}
}
    
    