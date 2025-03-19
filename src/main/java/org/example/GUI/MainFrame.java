package org.example.GUI;

import org.example.GUI.Auth.LoginForm;
import org.example.GUI.Constants.AppConstants;
import org.example.GUI.Panels.BanHang.BanHangPanel;
import org.example.GUI.Panels.NhapKho.NhapKhoPanel;
import org.example.GUI.Panels.NhanSu.NhanSuPanel;
import org.example.GUI.Panels.SanPham.SanPhamPanel;
import org.example.GUI.Panels.ThongKe.ThongKePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainFrame extends JFrame {
    private JPanel sidebar, contentPanel, headerPanel;
    private CardLayout cardLayout;
    private Map<String, JButton> navigationButtons;

    public MainFrame() {
        setTitle("Quản lý thư viện");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppConstants.BACKGROUND_COLOR);

        initializeHeader();
        initializeSidebar();
        initializeContentPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);

        // Load and set logo
        try {
            ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/logo-water.png")));
            Image img = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));
            logoPanel.add(logoLabel);

            // Add app name next to logo
//            JLabel appNameLabel = new JLabel("Quản lý Thư viện");
//            appNameLabel.setFont(AppConstants.TITLE_FONT);
//            appNameLabel.setForeground(AppConstants.PRIMARY_COLOR);
//            logoPanel.add(Box.createHorizontalStrut(10)); // Add some space between logo and text
//            logoPanel.add(appNameLabel);
        } catch (Exception e) {
            System.out.println("Could not load logo: " + e.getMessage());
            // Fallback to text if logo can't be loaded
            JLabel fallbackLabel = new JLabel("Đại lý nước");
            fallbackLabel.setFont(AppConstants.TITLE_FONT);
            fallbackLabel.setForeground(AppConstants.PRIMARY_COLOR);
            logoPanel.add(fallbackLabel);
        }

        // User profile panel
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setOpaque(false);
        JLabel userLabel = new JLabel("Admin");
        userLabel.setFont(AppConstants.NORMAL_FONT);
        profilePanel.add(userLabel);

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(profilePanel, BorderLayout.EAST);
    }

    private void initializeSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(AppConstants.SIDEBAR_COLOR);
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        JLabel logoLabel = new JLabel("ĐẠI LÝ");
        logoLabel.setFont(AppConstants.TITLE_FONT);
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(30));

        navigationButtons = new HashMap<>();

        // Add navigation buttons with icons
        addNavigationButton("sell", "Bán hàng", "book.png");
        addNavigationButton("products", "Sản phẩm", "book.png");
        addNavigationButton("users", "Nhân sự", "user.png");
        addNavigationButton("import", "Nhập kho", "loan.png");
        addNavigationButton("statistics", "Thống kê", "home.png");

        sidebar.add(Box.createVerticalGlue());

        // Logout button at bottom
        addNavigationButton("logout", "Đăng xuất", "logout.png");
    }

    private void addNavigationButton(String key, String label, String iconName) {
        JButton button = new JButton(label);
        button.setFont(AppConstants.NORMAL_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(AppConstants.SIDEBAR_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(230, 40));

        // Load and set icon
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + iconName));
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(10);
        } catch (Exception e) {
            System.out.println("Could not load icon: " + iconName);
        }

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(55, 65, 81));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(AppConstants.SIDEBAR_COLOR);
            }
        });

        navigationButtons.put(key, button);
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private void initializeContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add panels
        contentPanel.add(new BanHangPanel(), "sell");
        contentPanel.add(new SanPhamPanel(), "products");
        contentPanel.add(new NhanSuPanel(), "users");
        contentPanel.add(new NhapKhoPanel(), "import");
        contentPanel.add(new ThongKePanel(), "statistics");

        setupActionListeners();
    }

    private void setupActionListeners() {
        navigationButtons.forEach((key, button) -> {
            if (key.equals("logout")) {
                button.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc chắn muốn đăng xuất?",
                            "Xác nhận đăng xuất",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        new LoginForm().setVisible(true);
                        dispose();
                    }
                });
            } else {
                button.addActionListener(e -> {
                    cardLayout.show(contentPanel, key);
                    updateActiveButton(key);
                });
            }
        });
    }

    private void updateActiveButton(String activeKey) {
        navigationButtons.forEach((key, button) -> {
            if (key.equals(activeKey)) {
                button.setBackground(new Color(55, 65, 81));
            } else {
                button.setBackground(AppConstants.SIDEBAR_COLOR);
            }
        });
    }

    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
        updateActiveButton(panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}