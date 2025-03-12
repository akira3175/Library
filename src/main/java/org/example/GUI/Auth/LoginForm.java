package org.example.GUI.Auth;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import org.example.GUI.MainFrame;
import org.example.GUI.Constants.AppConstants;

public class LoginForm extends JFrame {
    private FloatingTextField usernameField;
    private FloatingPasswordField passwordField;
    private JButton loginButton;
    private JLabel exitLabel, registerLabel;
    private JToggleButton showPasswordButton;

    public LoginForm() {
        setUndecorated(true);
        setSize(350, 550);
        setShape(new RoundRectangle2D.Double(0, 0, 350, 550, 15, 15));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel with logo and exit button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Exit button
        exitLabel = new JLabel("×");
        exitLabel.setFont(new Font("Arial", Font.BOLD, 24));
        exitLabel.setForeground(new Color(231, 76, 60));
        exitLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create a new panel for the exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setOpaque(false);
        exitPanel.add(exitLabel);

        // Update the top panel layout
        topPanel.setLayout(new BorderLayout());
        topPanel.add(exitPanel, BorderLayout.NORTH);
        topPanel.add(logoLabel, BorderLayout.CENTER);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Đăng Nhập");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(AppConstants.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = createStyledTextField("Tên đăng nhập");
        JPanel passwordPanel = createStyledPasswordField("Mật khẩu");

        loginButton = createStyledButton("Đăng Nhập", AppConstants.TEXT_COLOR);
        loginButton.setPreferredSize(new Dimension(300, 50));

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setOpaque(false);
        JLabel noAccountLabel = new JLabel("Chưa có tài khoản? ");
        registerLabel = new JLabel("Đăng ký");
        registerLabel.setForeground(AppConstants.PRIMARY_COLOR);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(noAccountLabel);
        registerPanel.add(registerLabel);

        //formPanel.add(Box.createVerticalStrut(50));
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(70)); // Increased from 50 to 70
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(registerPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        setupActions();
    }

    private FloatingTextField createStyledTextField(String placeholder) {
        FloatingTextField field = new FloatingTextField(placeholder);
        field.setMaximumSize(new Dimension(300, 60));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, AppConstants.TEXT_COLOR),
                BorderFactory.createEmptyBorder(25, 5, 5, 5)));
        return field;
    }

    private JPanel createStyledPasswordField(String placeholder) {
        passwordField = new FloatingPasswordField(placeholder);
        passwordField.setMaximumSize(new Dimension(270, 60));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, AppConstants.TEXT_COLOR),
                BorderFactory.createEmptyBorder(25, 5, 5, 5)));

        // Thêm nút hiển thị mật khẩu
        showPasswordButton = new JToggleButton();
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPasswordButton.setPreferredSize(new Dimension(20, 20));

        ImageIcon eyeIcon = new ImageIcon(getClass().getResource("/images/eye-icon.png"));
        Image scaledEyeImage = eyeIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        showPasswordButton.setIcon(new ImageIcon(scaledEyeImage));

        showPasswordButton.addActionListener(e -> {
            if (showPasswordButton.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setMaximumSize(new Dimension(300, 60));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField);
        passwordPanel.add(showPasswordButton);

        return passwordPanel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(300, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void setupActions() {
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ("admin".equals(username) && "1234".equals(password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginForm.this, "Chuyển đến trang đăng ký");
                // Thêm code để mở form đăng ký ở đây
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><u>Đăng ký</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("Đăng ký");
            }
        });

        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }
}