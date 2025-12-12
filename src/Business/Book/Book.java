/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Book;

public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private int enterpriseId;
    
    public Book() {}
    
    public Book(String isbn, String title, String author, String category, int totalCopies, int enterpriseId) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.enterpriseId = enterpriseId;
    }
    
    // Getters
    public int getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public int getEnterpriseId() { return enterpriseId; }
    
    // Setters
    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public void setEnterpriseId(int enterpriseId) { this.enterpriseId = enterpriseId; }
    
    public boolean isAvailable() {
        return availableCopies > 0;
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}