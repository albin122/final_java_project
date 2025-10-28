package lms.ui;

import lms.dao.StudentDao;

import javax.swing.*;
import java.awt.*;

public class StudentLoginFrame extends JFrame {
    private final JTextField regField = new JTextField();
    private final JTextField nameField = new JTextField();

    public StudentLoginFrame() {
        setTitle("🎓 Student Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(26, 188, 156));
        header.setPreferredSize(new Dimension(500, 80));
        JLabel titleLabel = new JLabel("🎓 Student Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);

        // Form panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(240, 248, 255));
        form.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel regLabel = new JLabel("Registration Number:");
        regLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        regLabel.setForeground(new Color(52, 73, 94));
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(regLabel, gbc);

        regField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        regField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 1;
        form.add(regField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(52, 73, 94));
        gbc.gridy = 2;
        form.add(nameLabel, gbc);

        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 3;
        form.add(nameField, gbc);

        JButton loginBtn = new JButton("🚀 Login");
        styleButton(loginBtn, new Color(46, 125, 50), new Color(39, 174, 96), 200, 45);
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
        String reg = regField.getText().trim();
        String name = nameField.getText().trim();
        if (reg.isEmpty()) {
            showMessage("Please enter Registration Number.", UIManager.getColor("OptionPane.errorDialog.titlePane.background"));
            return;
        }
        try {
            StudentDao dao = new StudentDao();
            if (dao.existsByRegNo(reg)) {
                showMessage("✓ Login Successful", new Color(39, 174, 96));
                dispose();
                new StudentDashboardFrame(reg).setVisible(true);
            } else {
                if (name.isEmpty()) {
                    showMessage("Please enter your Name to register and login.", UIManager.getColor("OptionPane.errorDialog.titlePane.background"));
                    return;
                }
                // Auto-create student record (no separate signup)
                lms.model.Student s = new lms.model.Student(reg, name);
                dao.insertIfNotExists(s);
                showMessage("✓ Registered and logged in", new Color(39, 174, 96));
                dispose();
                new StudentDashboardFrame(reg).setVisible(true);
            }
        } catch (Exception ex) {
            showMessage("Error: " + ex.getMessage(), Color.RED);
        }
    }

    private void showMessage(String msg, Color bgColor) {
        JOptionPane.showMessageDialog(this, msg);
    }
}

