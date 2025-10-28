package lms.model;

import java.time.LocalDateTime;

public class BorrowRecord {
    private int id;
    private String studentRegNo;
    private int bookId;
    private String bookTitle;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;

    public BorrowRecord(int id, String studentRegNo, int bookId, String bookTitle,
                        LocalDateTime borrowedAt, LocalDateTime returnedAt) {
        this.id = id;
        this.studentRegNo = studentRegNo;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
    }

    public int getId() { return id; }
    public String getStudentRegNo() { return studentRegNo; }
    public int getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
}
