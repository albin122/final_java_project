package lms.ui;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private final JTextField userField = new JTextField();
    private final JPasswordField passField = new JPasswordField();

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 380);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 230, 255));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(142, 68, 173));
        header.setPreferredSize(new Dimension(500, 80));
        JLabel titleLabel = new JLabel("👨‍💼 Admin Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);

        // Form panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 230, 255));
        form.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(userLabel, gbc);

        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 1;
        form.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(new Color(52, 73, 94));
        gbc.gridy = 2;
        form.add(passLabel, gbc);

        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 3;
        form.add(passField, gbc);

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(142, 68, 173), new Color(155, 89, 182), 200, 45);
        gbc.gridy = 4;
        form.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> onLogin());

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(form, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void styleButton(JButton button, Color bgColor, Color hoverColor, int width, int height) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void onLogin() {
        String u = userField.getText().trim();
        String p = new String(passField.getPassword());
        // Predefined credentials
        if ("admin".equals(u) && "Java@123#".equals(p)) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            dispose();
            new AdminDashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }
}
