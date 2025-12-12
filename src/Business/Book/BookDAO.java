/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Book;

import Database.CRUD;
import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements CRUD {
    private Book book;
    
    public BookDAO(Book book) {
        this.book = book;
    }
    
    @Override
    public boolean create() {
        try {
            Database db = Database.getInstance();
            String query = "INSERT INTO Book (isbn, title, author, category, totalCopies, availableCopies, enterpriseId) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = db.prepareStatement(query);
            
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getCategory());
            pstmt.setInt(5, book.getTotalCopies());
            pstmt.setInt(6, book.getAvailableCopies());
            pstmt.setInt(7, book.getEnterpriseId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    book.setBookId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Object read(int id) {
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM Book WHERE bookId = ?";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("bookId"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCategory(rs.getString("category"));
                b.setTotalCopies(rs.getInt("totalCopies"));
                b.setAvailableCopies(rs.getInt("availableCopies"));
                b.setEnterpriseId(rs.getInt("enterpriseId"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean update() {
        try {
            Database db = Database.getInstance();
            String query = "UPDATE Book SET isbn=?, title=?, author=?, category=?, totalCopies=?, availableCopies=?, enterpriseId=? WHERE bookId=?";
            PreparedStatement pstmt = db.prepareStatement(query);
            
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getCategory());
            pstmt.setInt(5, book.getTotalCopies());
            pstmt.setInt(6, book.getAvailableCopies());
            pstmt.setInt(7, book.getEnterpriseId());
            pstmt.setInt(8, book.getBookId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        try {
            Database db = Database.getInstance();
            String query = "DELETE FROM Book WHERE bookId = ?";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setInt(1, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM Book";
            ResultSet rs = db.executeQuery(query);
            
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("bookId"));
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setCategory(rs.getString("category"));
                b.setTotalCopies(rs.getInt("totalCopies"));
                b.setAvailableCopies(rs.getInt("availableCopies"));
                b.setEnterpriseId(rs.getInt("enterpriseId"));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}