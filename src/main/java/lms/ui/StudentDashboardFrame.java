package lms.ui;

import lms.dao.BookDao;
import lms.dao.BorrowDao;
import lms.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentDashboardFrame extends JFrame {
    private final String studentRegNo;
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Available", "Status"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    public StudentDashboardFrame(String studentRegNo) {
        this.studentRegNo = studentRegNo;
        setTitle("🎓 Student Panel - " + studentRegNo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Main layout with header
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(26, 188, 156));
        header.setPreferredSize(new Dimension(900, 60));
        JLabel headerLabel = new JLabel("Available Books - " + studentRegNo);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.add(headerLabel, BorderLayout.WEST);
        mainPanel.add(header, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.75);
        split.setDividerSize(4);
        split.setBackground(new Color(240, 248, 255));

        // Left: Available Books
        JPanel booksPanel = new JPanel(new BorderLayout());
        booksPanel.setBackground(new Color(240, 248, 255));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        booksPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        split.setLeftComponent(booksPanel);

        // Right: Actions
        JPanel actions = new JPanel();
        actions.setBackground(new Color(240, 248, 255));
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        JButton borrowBtn = createStyledButton("Borrow", new Color(46, 125, 50));
        JButton returnBtn = createStyledButton("↩Return", new Color(243, 156, 18));
        JButton logoutBtn = createStyledButton("Logout", new Color(231, 76, 60));
        
        actions.add(Box.createVerticalStrut(30));
        actions.add(borrowBtn);
        actions.add(Box.createVerticalStrut(15));
        actions.add(returnBtn);
        actions.add(Box.createVerticalStrut(15));
        actions.add(logoutBtn);
        actions.add(Box.createVerticalStrut(30));
        split.setRightComponent(actions);

        mainPanel.add(split, BorderLayout.CENTER);
        add(mainPanel);

        borrowBtn.addActionListener(e -> onBorrow());
        returnBtn.addActionListener(e -> onReturn());
        logoutBtn.addActionListener(e -> {
            dispose();
            new RoleSelectionFrame().setVisible(true);
        });

        refreshBooks();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(150, 50));
        btn.setMaximumSize(new Dimension(150, 50));
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

    private void refreshBooks() {
        try {
            tableModel.setRowCount(0);
            List<Book> books = new BookDao().getAll();
            for (Book b : books) {
                tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAvailableCount(), b.getAvailabilityStatus()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load books: " + ex.getMessage());
        }
    }

    private Integer getSelectedBookId() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        Object val = tableModel.getValueAt(row, 0);
        return (val instanceof Integer) ? (Integer) val : Integer.parseInt(val.toString());
    }

    private void onBorrow() {
        Integer bookId = getSelectedBookId();
        if (bookId == null) {
            JOptionPane.showMessageDialog(this, "Please select a book.");
            return;
        }
        try {
            BorrowDao borrowDao = new BorrowDao();
            if (borrowDao.hasActiveBorrow(studentRegNo, bookId)) {
                JOptionPane.showMessageDialog(this, "You already borrowed this book.");
                return;
            }
            BookDao bookDao = new BookDao();
            boolean decremented = bookDao.decrementIfAvailable(bookId);
            if (!decremented) {
                JOptionPane.showMessageDialog(this, "Book not available.");
                return;
            }
            borrowDao.createBorrow(studentRegNo, bookId);
            JOptionPane.showMessageDialog(this, "Borrowed successfully.");
            refreshBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onReturn() {
        Integer bookId = getSelectedBookId();
        if (bookId == null) {
            JOptionPane.showMessageDialog(this, "Please select a book.");
            return;
        }
        try {
            BorrowDao borrowDao = new BorrowDao();
            boolean updated = borrowDao.returnBorrow(studentRegNo, bookId);
            if (!updated) {
                JOptionPane.showMessageDialog(this, "No active borrow found for this book.");
                return;
            }
            new BookDao().increment(bookId);
            JOptionPane.showMessageDialog(this, "Returned successfully.");
            refreshBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

