package org.example.GUI;

import org.example.BUS.NguoiDungBUS;
import org.example.BUS.VaiTroBUS;
import org.example.BUS.QuyenBUS;
import org.example.DTO.NguoiDung;
import org.example.DTO.Quyen;
import org.example.GUI.Auth.LoginForm;
import org.example.GUI.Constants.AppConstants;
import org.example.GUI.Panels.KhachHang.KhachHangPanel;
import org.example.GUI.Panels.NhapKho.NhapKhoPanel;
import org.example.GUI.Panels.NhanSu.NhanSuPanel;
import org.example.GUI.Panels.SanPham.SanPhamPanel;
import org.example.GUI.Panels.ThongKe.ThongKePanel;
import org.example.GUI.Panels.KhuyenMai.KhuyenMaiPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.GUI.Panels.BanHang.BanHangPanel;


public class MainFrame extends JFrame {
    private JPanel sidebar, contentPanel, headerPanel;
    private CardLayout cardLayout;
    private Map<String, JButton> navigationButtons;
    private String activeButtonKey = "sell"; // Default active button
    private NguoiDung nguoiDungHienTai;
    private QuyenBUS quyenBUS;
    private VaiTroBUS vaiTroBUS;


    public MainFrame() {
        setTitle("Quản lý bán hàng");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppConstants.BACKGROUND_COLOR);

        nguoiDungHienTai = NguoiDungBUS.getNguoiDungHienTai();
        quyenBUS = new QuyenBUS();
        vaiTroBUS = new VaiTroBUS();

        initializeHeader();
        initializeSidebar();
        initializeContentPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        quyenBUS.khoiTaoQuyen();
        vaiTroBUS.khoiTaoVaiTro();
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
        JLabel userLabel = new JLabel("Xin chào " + nguoiDungHienTai.getHoTen());
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
        sidebar.setPreferredSize(new Dimension(200, getHeight())); // Increased width

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
        List<Quyen> danhSachQuyen = quyenBUS.layDanhSachQuyenTheoVaiTro(nguoiDungHienTai.getMaVaiTro());
        Set<String> danhSachTenQuyen = danhSachQuyen.stream()
                                            .filter(Quyen::isChecked)
                                            .map(Quyen::getTenQuyen)
                                            .collect(Collectors.toSet());;

        // Add navigation buttons with icons
        if (danhSachTenQuyen.contains("Bán hàng")) {
            addNavigationButton("sell", "Bán hàng", "book.png");
        }
        if (danhSachTenQuyen.contains("Quản lý sản phẩm")) {
            addNavigationButton("products", "Sản phẩm", "book.png");
        }
        if (danhSachTenQuyen.contains("Quản lý khuyến mãi")) {
            addNavigationButton("promotions", "Khuyến mãi", "book.png");
        }
        if (danhSachTenQuyen.contains("Quản lý khách hàng")) {
            addNavigationButton("consumers", "Khách hàng", "book.png");
        }
        if (danhSachTenQuyen.contains("Quản lý nhân sự")) {
            addNavigationButton("users", "Nhân sự", "user.png");
        }
        if (danhSachTenQuyen.contains("Quản lý kho")) {
            addNavigationButton("import", "Nhập kho", "loan.png");
        }
        if (danhSachTenQuyen.contains("Thống kê")) {
            addNavigationButton("statistics", "Thống kê", "home.png");
        }

        sidebar.add(Box.createVerticalGlue());

        // Logout button at bottom
        addNavigationButton("logout", "Đăng xuất", "logout.png");

        // Set initial active button
        updateActiveButton(activeButtonKey);
    }

    private void addNavigationButton(String key, String label, String iconName) {
        JButton button = new JButton(label);

        // Use a larger, bolder font
        Font buttonFont = new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14);
        button.setFont(buttonFont);

        button.setForeground(Color.WHITE);
        button.setBackground(AppConstants.SIDEBAR_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Make buttons larger
        button.setMaximumSize(new Dimension(260, 50));
        button.setPreferredSize(new Dimension(260, 50));

        // Add rounded corners and padding with a custom border
        button.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Load and set icon with larger size
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + iconName));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // Larger icon
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(15); // More space between icon and text
            button.setHorizontalAlignment(SwingConstants.LEFT);
        } catch (Exception e) {
            System.out.println("Could not load icon: " + iconName);
        }

        // Enhanced hover effect with transition-like appearance
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!key.equals(activeButtonKey)) {
                    button.setBackground(new Color(55, 65, 81));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!key.equals(activeButtonKey)) {
                    button.setBackground(AppConstants.SIDEBAR_COLOR);
                }
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 55, 71)); // Darker when pressed
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (key.equals(activeButtonKey)) {
                    button.setBackground(new Color(55, 65, 81));
                } else if (button.contains(evt.getPoint())) {
                    button.setBackground(new Color(55, 65, 81));
                } else {
                    button.setBackground(AppConstants.SIDEBAR_COLOR);
                }
            }
        });

        navigationButtons.put(key, button);

        // Create a wrapper panel for the button to add a left indicator for active state
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(280, 60));
        buttonPanel.add(button, BorderLayout.CENTER);

        // Add some vertical spacing between buttons
        sidebar.add(buttonPanel);
        sidebar.add(Box.createVerticalStrut(8));
    }

    private void initializeContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add panels
        contentPanel.add(new BanHangPanel(), "sell");
        contentPanel.add(new SanPhamPanel(), "products");
        contentPanel.add(new KhuyenMaiPanel(), "promotions");
        contentPanel.add(new KhachHangPanel(), "consumers");
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
                    activeButtonKey = key;
                    updateActiveButton(key);
                });
            }
        });
    }

    private void updateActiveButton(String activeKey) {
        activeButtonKey = activeKey;
        navigationButtons.forEach((key, button) -> {
            if (key.equals(activeKey)) {
                // Active button styling
                button.setBackground(new Color(55, 65, 81));

                // Add a left border indicator for active button
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(129, 140, 248)), // Left indicator
                        new EmptyBorder(10, 11, 10, 15) // Adjust left padding to compensate for the border
                ));
            } else {
                // Inactive button styling
                button.setBackground(AppConstants.SIDEBAR_COLOR);
                button.setBorder(new EmptyBorder(10, 15, 10, 15));
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

