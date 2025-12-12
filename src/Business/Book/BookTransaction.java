/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Book;

import java.util.Date;

public class BookTransaction {
    private int transactionId;
    private int bookId;
    private int memberId;
    private int librarianId;
    private Date checkoutDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private double fine;
    
    public BookTransaction() {}
    
    public BookTransaction(int bookId, int memberId, int librarianId, Date checkoutDate, Date dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.librarianId = librarianId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.status = "Active";
        this.fine = 0.0;
    }
    
    // Getters
    public int getTransactionId() { return transactionId; }
    public int getBookId() { return bookId; }
    public int getMemberId() { return memberId; }
    public int getLibrarianId() { return librarianId; }
    public Date getCheckoutDate() { return checkoutDate; }
    public Date getDueDate() { return dueDate; }
    public Date getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
    public double getFine() { return fine; }
    
    // Setters
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public void setLibrarianId(int librarianId) { this.librarianId = librarianId; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setStatus(String status) { this.status = status; }
    public void setFine(double fine) { this.fine = fine; }
    
    public void checkout() {
        this.status = "Active";
    }
    
    public void returnBook() {
        this.returnDate = new Date();
        this.status = "Returned";
    }
    
    public double calculateFine() {
        if (returnDate != null && returnDate.after(dueDate)) {
            long diff = returnDate.getTime() - dueDate.getTime();
            long daysLate = diff / (1000 * 60 * 60 * 24);
            this.fine = daysLate * 1.0; // $1 per day late
        }
        return fine;
    }
}