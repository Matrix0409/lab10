/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Business.UserAccount.Librarian;
import Business.Book.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class LibrarianDashboard extends JFrame {
    private Librarian librarian;
    private JTable table;
    private DefaultTableModel model;
    
    public LibrarianDashboard(Librarian librarian) {
        this.librarian = librarian;
        initUI();
        loadBooks();
    }
    
    private void initUI() {
        setTitle("Librarian Dashboard - " + librarian.getUsername());
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        // Header
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(1200, 100));
        header.setBackground(new Color(46, 204, 113));
        
        JLabel welcome = new JLabel("Welcome, " + librarian.getUsername());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(40, 20, 500, 35);
        header.add(welcome);
        
        JLabel role = new JLabel("Librarian • Book Management & Operations");
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
        
        // Stats cards
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        stats.setBackground(Color.WHITE);
        stats.setPreferredSize(new Dimension(1200, 100));
        stats.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        
        stats.add(createStatCard("📚 Total Books", "156", new Color(52, 152, 219)));
        stats.add(createStatCard("✅ Available", "142", new Color(46, 204, 113)));
        stats.add(createStatCard("📤 Checked Out", "14", new Color(241, 196, 15)));
        stats.add(createStatCard("⚠️ Overdue", "3", new Color(231, 76, 60)));
        
        main.add(stats, BorderLayout.PAGE_START);
        
        // Table
        String[] columns = {"ID", "ISBN", "Title", "Author", "Category", "Total", "Available", "Enterprise"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(46, 204, 113, 50));
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
        
        // Button panels
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // CRUD buttons
        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        crudPanel.setBackground(Color.WHITE);
        
        JButton add = createActionButton("➕ Add Book", new Color(46, 204, 113), 140);
        add.addActionListener(e -> addBook());
        
        JButton update = createActionButton("✏️ Update Book", new Color(52, 152, 219), 150);
        update.addActionListener(e -> updateBook());
        
        JButton delete = createActionButton("🗑️ Delete Book", new Color(231, 76, 60), 140);
        delete.addActionListener(e -> deleteBook());
        
        JButton refresh = createActionButton("🔄 Refresh", new Color(149, 165, 166), 120);
        refresh.addActionListener(e -> loadBooks());
        
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
        
        
        crudPanel.add(add);
        crudPanel.add(update);
        crudPanel.add(delete);
        crudPanel.add(refresh);
        crudPanel.add(logoutBtn); 
        
        // Transaction buttons
        JPanel transPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        transPanel.setBackground(Color.WHITE);
        
        JButton checkout = createActionButton("📤 Process Checkout", new Color(241, 196, 15), 170);
        checkout.addActionListener(e -> processCheckout());
        
        JButton returnBook = createActionButton("📥 Process Return", new Color(155, 89, 182), 160);
        returnBook.addActionListener(e -> processReturn());
        
        JButton ill = createActionButton("🔗 ILL Request", new Color(26, 188, 156), 140);
        ill.addActionListener(e -> createILL());
        
        transPanel.add(checkout);
        transPanel.add(returnBook);
        transPanel.add(ill);
        
        bottomPanel.add(crudPanel);
        bottomPanel.add(transPanel);
        
        main.add(bottomPanel, BorderLayout.SOUTH);
        add(main);
    }
    
    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(200, 60));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
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
        btn.setPreferredSize(new Dimension(width, 42));
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
                b.getBookId(), b.getIsbn(), b.getTitle(), b.getAuthor(),
                b.getCategory(), b.getTotalCopies(), b.getAvailableCopies(), b.getEnterpriseId()
            });
        }
    }
    
    private void addBook() {
        JTextField isbn = new JTextField();
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField category = new JTextField();
        JTextField copies = new JTextField("1");
        JTextField enterprise = new JTextField("1");
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("ISBN:"));
        panel.add(isbn);
        panel.add(new JLabel("Title:"));
        panel.add(title);
        panel.add(new JLabel("Author:"));
        panel.add(author);
        panel.add(new JLabel("Category:"));
        panel.add(category);
        panel.add(new JLabel("Copies:"));
        panel.add(copies);
        panel.add(new JLabel("Enterprise ID:"));
        panel.add(enterprise);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Book book = new Book(isbn.getText(), title.getText(), author.getText(),
                    category.getText(), Integer.parseInt(copies.getText()), 
                    Integer.parseInt(enterprise.getText()));
                
                if (new BookDAO(book).create()) {
                    JOptionPane.showMessageDialog(this, "✓ Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadBooks();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) model.getValueAt(row, 0);
        Book book = (Book) new BookDAO(new Book()).read(id);
        
        if (book != null) {
            JTextField title = new JTextField(book.getTitle());
            JTextField author = new JTextField(book.getAuthor());
            JTextField category = new JTextField(book.getCategory());
            JTextField total = new JTextField(String.valueOf(book.getTotalCopies()));
            JTextField avail = new JTextField(String.valueOf(book.getAvailableCopies()));
            
            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.add(new JLabel("Title:"));
            panel.add(title);
            panel.add(new JLabel("Author:"));
            panel.add(author);
            panel.add(new JLabel("Category:"));
            panel.add(category);
            panel.add(new JLabel("Total:"));
            panel.add(total);
            panel.add(new JLabel("Available:"));
            panel.add(avail);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Update Book", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                book.setTitle(title.getText());
                book.setAuthor(author.getText());
                book.setCategory(category.getText());
                book.setTotalCopies(Integer.parseInt(total.getText()));
                book.setAvailableCopies(Integer.parseInt(avail.getText()));
                
                if (new BookDAO(book).update()) {
                    JOptionPane.showMessageDialog(this, "✓ Book updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadBooks();
                }
            }
        }
    }
    
    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) model.getValueAt(row, 0);
        String title = model.getValueAt(row, 2).toString();
        
        int result = JOptionPane.showConfirmDialog(this, "Delete: " + title + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            if (new BookDAO(new Book()).delete(id)) {
                JOptionPane.showMessageDialog(this, "✓ Book deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks();
            }
        }
    }
    
    private void processCheckout() {
        JOptionPane.showMessageDialog(this, "Checkout feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void processReturn() {
        JOptionPane.showMessageDialog(this, "Return feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void createILL() {
        JOptionPane.showMessageDialog(this, "ILL Request feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
}