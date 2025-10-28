package lms.ui;

import lms.dao.BookDao;
import lms.dao.BorrowDao;
import lms.model.Book;
import lms.model.BorrowRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    // Table for Borrowed Books
    private final DefaultTableModel borrowTableModel = new DefaultTableModel(new Object[]{"Borrow ID", "Reg No", "Book Title", "Borrowed At"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private JTable borrowTable = new JTable(borrowTableModel);

    // Table for All Books
    private final DefaultTableModel bookTableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Available Count"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private JTable bookTable = new JTable(bookTableModel);

    public AdminDashboardFrame() {
        setTitle("👨‍💼 Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 250, 255));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(142, 68, 173));
        header.setPreferredSize(new Dimension(950, 70));
        JLabel title = new JLabel("👨‍💼 Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        header.add(title, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // Tabbed pane for different views
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tab 1: Active Borrowed Books
        JPanel borrowPanel = createBorrowPanel();
        tabbedPane.addTab("📚 Borrowed Books", borrowPanel);

        // Tab 2: All Books Management
        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("📖 Manage Books", booksPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Action buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        actions.setBackground(new Color(245, 230, 255));
        actions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton addBookBtn = createStyledButton("➕ Add Book", new Color(46, 125, 50));
        JButton removeBookBtn = createStyledButton("🗑️ Remove Book", new Color(231, 76, 60));
        JButton refreshBtn = createStyledButton("🔄 Refresh", new Color(52, 152, 219));
        JButton logoutBtn = createStyledButton("🚪 Logout", new Color(149, 165, 166));
        
        actions.add(addBookBtn);
        actions.add(removeBookBtn);
        actions.add(refreshBtn);
        actions.add(logoutBtn);

        addBookBtn.addActionListener(e -> onAddBook());
        removeBookBtn.addActionListener(e -> onRemoveBook());
        refreshBtn.addActionListener(e -> refreshAll());
        logoutBtn.addActionListener(e -> { dispose(); new RoleSelectionFrame().setVisible(true); });

        mainPanel.add(actions, BorderLayout.SOUTH);
        add(mainPanel);

        refreshAll();
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 250, 255));
        
        borrowTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        borrowTable.setRowHeight(30);
        borrowTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        borrowTable.getTableHeader().setBackground(new Color(142, 68, 173));
        borrowTable.getTableHeader().setForeground(Color.WHITE);
        borrowTable.setGridColor(new Color(189, 195, 199));
        
        JScrollPane scroll = new JScrollPane(borrowTable);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 250, 255));
        
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bookTable.setRowHeight(30);
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        bookTable.getTableHeader().setBackground(new Color(46, 125, 50));
        bookTable.getTableHeader().setForeground(Color.WHITE);
        bookTable.setGridColor(new Color(189, 195, 199));
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scroll = new JScrollPane(bookTable);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    private void refreshAll() {
        refreshBorrowedBooks();
        refreshAllBooks();
    }

    private void refreshBorrowedBooks() {
        try {
            borrowTableModel.setRowCount(0);
            List<BorrowRecord> list = new BorrowDao().listActiveBorrows();
            for (BorrowRecord r : list) {
                borrowTableModel.addRow(new Object[]{r.getId(), r.getStudentRegNo(), r.getBookTitle(), r.getBorrowedAt()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading borrowed books: " + ex.getMessage());
        }
    }

    private void refreshAllBooks() {
        try {
            bookTableModel.setRowCount(0);
            List<Book> books = new BookDao().getAll();
            for (Book b : books) {
                bookTableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAvailableCount()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + ex.getMessage());
        }
    }

    private void onAddBook() {
        AddBookDialog dlg = new AddBookDialog(this);
        dlg.setVisible(true);
        if (dlg.isSuccess()) {
            JOptionPane.showMessageDialog(this, "✓ Book added successfully!");
            refreshAllBooks();
        }
    }

    private void onRemoveBook() {
        int row = bookTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a book to remove.");
            return;
        }
        
        Object idObj = bookTableModel.getValueAt(row, 0);
        int bookId = (idObj instanceof Integer) ? (Integer) idObj : Integer.parseInt(idObj.toString());
        String bookTitle = (String) bookTableModel.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove\n\"" + bookTitle + "\"?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                BookDao dao = new BookDao();
                if (dao.deleteById(bookId)) {
                    JOptionPane.showMessageDialog(this, "✓ Book removed successfully!");
                    refreshAllBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove book.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

