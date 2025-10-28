package lms.ui;

import lms.dao.BookDao;

import javax.swing.*;
import java.awt.*;

public class AddBookDialog extends JDialog {
    private final JTextField titleField = new JTextField();
    private final JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private boolean success = false;

    public AddBookDialog(JFrame owner) {
        super(owner, "Add New Book", true);
        setPreferredSize(new Dimension(500, 340));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 230, 255));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(46, 125, 50));
        header.setPreferredSize(new Dimension(450, 60));
        JLabel headerLabel = new JLabel("Add New Book");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        header.add(headerLabel);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 230, 255));
        form.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel nameLabel = new JLabel("Book Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(nameLabel, gbc);

        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 1;
        form.add(titleField, gbc);

        JLabel countLabel = new JLabel("Book Count:");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        countLabel.setForeground(new Color(52, 73, 94));
        gbc.gridy = 2;
        form.add(countLabel, gbc);

        countSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        countSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        gbc.gridy = 3;
        form.add(countSpinner, gbc);

        JButton addBtn = createStyledButton("Add", new Color(46, 125, 50), 140, 42);
        JButton cancelBtn = createStyledButton("Cancel", new Color(149, 165, 166), 140, 42);

        addBtn.addActionListener(e -> onAdd());
        cancelBtn.addActionListener(e -> dispose());

        // Bottom sticky button bar so buttons are always visible
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        btnBar.setBackground(new Color(245, 230, 255));
        btnBar.add(addBtn);
        btnBar.add(cancelBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(form, BorderLayout.CENTER);
        mainPanel.add(btnBar, BorderLayout.SOUTH);
        add(mainPanel);

        getRootPane().setDefaultButton(addBtn);
        pack();
        setLocationRelativeTo(owner);
    }

    private JButton createStyledButton(String text, Color color, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setPreferredSize(new Dimension(width, height));
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

    private void onAdd() {
        String title = titleField.getText().trim();
        int count = (Integer) countSpinner.getValue();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty");
            return;
        }
        try {
            new BookDao().upsertByTitle(title, count);
            success = true;
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public boolean isSuccess() { return success; }
}
