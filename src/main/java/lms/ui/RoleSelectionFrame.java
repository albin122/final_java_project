package lms.ui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionFrame extends JFrame {
    public RoleSelectionFrame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        // Main panel with gradient-like styling
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));

        // Header with icon
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(52, 73, 94));
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);
        header.setPreferredSize(new Dimension(500, 70));

        // Button panel with beautiful buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel label = new JLabel("Select Your Role", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(label, gbc);

        // Student Button - Green/Blue
        JButton studentBtn = new JButton("🎓 Student Login");
        styleButton(studentBtn, new Color(46, 125, 50), new Color(39, 174, 96), 180, 50);
        gbc.gridy = 1;
        buttonPanel.add(studentBtn, gbc);

        // Admin Button - Purple/Blue
        JButton adminBtn = new JButton("👨‍💼 Admin Login");
        styleButton(adminBtn, new Color(142, 68, 173), new Color(155, 89, 182), 180, 50);
        gbc.gridy = 2;
        buttonPanel.add(adminBtn, gbc);

        studentBtn.addActionListener(e -> {
            dispose();
            new StudentLoginFrame().setVisible(true);
        });
        adminBtn.addActionListener(e -> {
            dispose();
            new AdminLoginFrame().setVisible(true);
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
    }

    private void styleButton(JButton button, Color bgColor, Color hoverColor, int width, int height) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
}
