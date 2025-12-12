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
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.WorkRequestDAO;

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
        
     JButton checkout = createActionButton("📤 View Requests", new Color(241, 196, 15), 170);
checkout.addActionListener(e -> viewPendingRequests());
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
    private void viewPendingRequests() {
    JFrame requestsFrame = new JFrame("Pending Checkout Requests");
    requestsFrame.setSize(1000, 500);
    requestsFrame.setLocationRelativeTo(this);
    
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    panel.setBackground(Color.WHITE);
    
    JLabel titleLabel = new JLabel("Pending Checkout Requests from Members");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
    panel.add(titleLabel, BorderLayout.NORTH);
    
    String[] columns = {"ID", "Type", "Member", "Description", "Date", "Status"};
    DefaultTableModel requestModel = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int col) { return false; }
    };
    
    JTable requestTable = new JTable(requestModel);
    requestTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    requestTable.setRowHeight(35);
    requestTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    requestTable.getTableHeader().setBackground(new Color(46, 204, 113));
    requestTable.getTableHeader().setForeground(Color.WHITE);
    
    List<WorkRequest> requests = WorkRequestDAO.getPendingRequests();
    for (WorkRequest wr : requests) {
        requestModel.addRow(new Object[]{
            wr.getRequestId(),
            wr.getType(),
            wr.getRequesterId(),
            wr.getDescription(),
            wr.getRequestDate(),
            wr.getStatus()
        });
    }
    
    JScrollPane scroll = new JScrollPane(requestTable);
    panel.add(scroll, BorderLayout.CENTER);
    
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttons.setBackground(Color.WHITE);
    
    JButton approveBtn = new JButton("✅ Approve Request");
    approveBtn.setBackground(new Color(46, 204, 113));
    approveBtn.setForeground(Color.WHITE);
    approveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    approveBtn.setPreferredSize(new Dimension(160, 40));
    approveBtn.setFocusPainted(false);
    
    approveBtn.addActionListener(e -> {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(requestsFrame, "Please select a request");
            return;
        }
        
        int requestId = (int) requestModel.getValueAt(selectedRow, 0);
        String desc = requestModel.getValueAt(selectedRow, 3).toString();
        
        int confirm = JOptionPane.showConfirmDialog(requestsFrame,
            "Approve: " + desc + "?",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            WorkRequestDAO dao = new WorkRequestDAO(new WorkRequest());
            WorkRequest wr = (WorkRequest) dao.read(requestId);
            
            if (wr != null) {
                wr.setStatus("Approved");
                wr.setApproverId(librarian.getUserId());
                
                WorkRequestDAO updateDao = new WorkRequestDAO(wr);
                if (updateDao.update()) {
                    JOptionPane.showMessageDialog(requestsFrame, "✓ Request approved!");
                    requestModel.setValueAt("Approved", selectedRow, 5);
                }
            }
        }
    });
    
    JButton rejectBtn = new JButton("❌ Reject Request");
    rejectBtn.setBackground(new Color(231, 76, 60));
    rejectBtn.setForeground(Color.WHITE);
    rejectBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    rejectBtn.setPreferredSize(new Dimension(150, 40));
    rejectBtn.setFocusPainted(false);
    
    rejectBtn.addActionListener(e -> {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(requestsFrame, "Please select a request");
            return;
        }
        
        String reason = JOptionPane.showInputDialog(requestsFrame, "Reason for rejection:");
        if (reason != null && !reason.trim().isEmpty()) {
            int requestId = (int) requestModel.getValueAt(selectedRow, 0);
            WorkRequestDAO dao = new WorkRequestDAO(new WorkRequest());
            WorkRequest wr = (WorkRequest) dao.read(requestId);
            
            if (wr != null) {
                wr.setStatus("Rejected");
                
                WorkRequestDAO updateDao = new WorkRequestDAO(wr);
                if (updateDao.update()) {
                    JOptionPane.showMessageDialog(requestsFrame, "Request rejected");
                    requestModel.setValueAt("Rejected", selectedRow, 5);
                }
            }
        }
    });
    
    JButton refreshBtn = new JButton("🔄 Refresh");
    refreshBtn.setBackground(new Color(52, 152, 219));
    refreshBtn.setForeground(Color.WHITE);
    refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    refreshBtn.setPreferredSize(new Dimension(120, 40));
    refreshBtn.setFocusPainted(false);
    refreshBtn.addActionListener(e -> {
        requestModel.setRowCount(0);
        List<WorkRequest> updated = WorkRequestDAO.getPendingRequests();
        for (WorkRequest wr : updated) {
            requestModel.addRow(new Object[]{
                wr.getRequestId(), wr.getType(), wr.getRequesterId(),
                wr.getDescription(), wr.getRequestDate(), wr.getStatus()
            });
        }
    });
    
    JButton closeBtn = new JButton("Close");
    closeBtn.setBackground(new Color(149, 165, 166));
    closeBtn.setForeground(Color.WHITE);
    closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    closeBtn.setPreferredSize(new Dimension(100, 40));
    closeBtn.setFocusPainted(false);
    closeBtn.addActionListener(e -> requestsFrame.dispose());
    
    buttons.add(approveBtn);
    buttons.add(rejectBtn);
    buttons.add(refreshBtn);
    buttons.add(closeBtn);
    
    panel.add(buttons, BorderLayout.SOUTH);
    
    requestsFrame.add(panel);
    requestsFrame.setVisible(true);
}
  
    
    private void processReturn() {
        JOptionPane.showMessageDialog(this, "Return feature - Coming soon!", "Feature", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void createILL() {
    JTextField bookTitle = new JTextField();
    JTextField author = new JTextField();
    JTextField isbn = new JTextField();
    JTextArea reason = new JTextArea(3, 20);
    reason.setLineWrap(true);
    
    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    panel.add(new JLabel("Book Title:"));
    panel.add(bookTitle);
    panel.add(new JLabel("Author:"));
    panel.add(author);
    panel.add(new JLabel("ISBN:"));
    panel.add(isbn);
    panel.add(new JLabel("Reason:"));
    panel.add(new JScrollPane(reason));
    
    int result = JOptionPane.showConfirmDialog(this, panel, 
        "Inter-Library Loan Request to Central Library", 
        JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        String description = "ILL Request: " + bookTitle.getText() + " by " + author.getText() + 
                           " (ISBN: " + isbn.getText() + ")\nReason: " + reason.getText();
        
        WorkRequest illRequest = new WorkRequest(
            "ILL Request",
            librarian.getUserId(),
            1, // From Community Library
            2, // To Central Library (INTER-ENTERPRISE!)
            description
        );
        
        WorkRequestDAO dao = new WorkRequestDAO(illRequest);
        if (dao.create()) {
            JOptionPane.showMessageDialog(this, 
                "✓ ILL Request sent to Central Library!\nRequest ID: " + illRequest.getRequestId(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
}