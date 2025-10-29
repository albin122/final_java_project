package lms.dao;

import lms.db.Database;
import lms.model.BorrowRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BorrowDao {
    public boolean hasActiveBorrow(String regNo, int bookId) throws SQLException {
        String sql = "SELECT 1 FROM borrows WHERE student_reg_no = ? AND book_id = ? AND returned_at IS NULL";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            ps.setInt(2, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void createBorrow(String regNo, int bookId) throws SQLException {
        String sql = "INSERT INTO borrows (student_reg_no, book_id) VALUES (?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        }
    }

    public boolean returnBorrow(String regNo, int bookId) throws SQLException {
        String sql = "UPDATE borrows SET returned_at = CURRENT_TIMESTAMP WHERE student_reg_no = ? AND book_id = ? AND returned_at IS NULL";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<BorrowRecord> listActiveBorrows() throws SQLException {
        String sql = "" +
                "SELECT b.id, b.student_reg_no, b.book_id, bk.title, b.borrowed_at " +
                "FROM borrows b JOIN books bk ON b.book_id = bk.id " +
                "WHERE b.returned_at IS NULL ORDER BY b.borrowed_at DESC";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<BorrowRecord> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new BorrowRecord(
                        rs.getInt("id"),
                        rs.getString("student_reg_no"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getTimestamp("borrowed_at").toLocalDateTime(),
                        null
                ));
            }
            return list;
        }
    }
}


