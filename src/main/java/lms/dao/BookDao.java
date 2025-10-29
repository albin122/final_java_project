package lms.dao;

import lms.db.Database;
import lms.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public List<Book> getAll() throws SQLException {
        String sql = "SELECT id, title, available_count FROM books ORDER BY title";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Book> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("available_count")
                ));
            }
            return list;
        }
    }

    public Book findById(int id) throws SQLException {
        String sql = "SELECT id, title, available_count FROM books WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("available_count")
                    );
                }
                return null;
            }
        }
    }

    public Book findByTitle(String title) throws SQLException {
        String sql = "SELECT id, title, available_count FROM books WHERE title = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("available_count")
                    );
                }
                return null;
            }
        }
    }

    public void insert(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, available_count) VALUES (?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAvailableCount());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    book.setId(keys.getInt(1));
                }
            }
        }
    }

    public void upsertByTitle(String title, int countToAdd) throws SQLException {
        Book existing = findByTitle(title);
        if (existing == null) {
            insert(new Book(title, countToAdd));
        } else {
            String sql = "UPDATE books SET available_count = available_count + ? WHERE id = ?";
            try (Connection con = Database.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, countToAdd);
                ps.setInt(2, existing.getId());
                ps.executeUpdate();
            }
        }
    }

    public boolean decrementIfAvailable(int bookId) throws SQLException {
        String sql = "UPDATE books SET available_count = available_count - 1 WHERE id = ? AND available_count > 0";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        }
    }

    public void increment(int bookId) throws SQLException {
        String sql = "UPDATE books SET available_count = available_count + 1 WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
        }
    }

    public boolean deleteById(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        }
    }
}
