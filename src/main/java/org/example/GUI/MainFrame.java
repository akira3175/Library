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
import org.example.GUI.Panels.BanHang.BanHangPanel;
import org.example.GUI.Panels.NhanSu.DoiMatKhauDialog;

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

public class MainFrame extends JFrame {
    private JPanel sidebar, contentPanel, headerPanel;
    private CardLayout cardLayout;
    private Map<String, JButton> navigationButtons;
    private String activeButtonKey = "sell"; // Default active button
    private NguoiDung nguoiDungHienTai;
    private QuyenBUS quyenBUS;
    private VaiTroBUS vaiTroBUS;
    private SanPhamPanel sanPham;
    private ThongKePanel thongKePanel; // Added to store ThongKePanel instance

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
        sanPham = new SanPhamPanel();
        thongKePanel = new ThongKePanel(); // Initialize ThongKePanel
        System.out.println("MainFrame using SanPhamPanel instance: " + sanPham);

        initializeHeader();
        initializeSidebar();
        initializeContentPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        quyenBUS.khoiTaoQuyen();
        vaiTroBUS.khoiTaoVaiTro();
        vaiTroBUS.ganVaiTroChoQuanTriVien();
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

        // Create a panel for user info and dropdown
        JPanel userPanel = new JPanel(new BorderLayout(5, 0));
        userPanel.setOpaque(false);

        // User greeting label
        JLabel userLabel = new JLabel("Xin chào " + nguoiDungHienTai.getHoTen());
        userLabel.setFont(AppConstants.NORMAL_FONT);

        // User icon/avatar
        JLabel userIconLabel = new JLabel();
        try {
            ImageIcon userIcon = new ImageIcon(getClass().getResource("/icons/user.png"));
            Image img = userIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            userIconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Fallback to text icon if image can't be loaded
            userIconLabel.setText("👤");
            userIconLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 16));
        }

        // Create a dropdown arrow icon
        JLabel dropdownArrow = new JLabel("▼");
        dropdownArrow.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 10));
        dropdownArrow.setForeground(new Color(107, 114, 128));

        // Add components to user panel
        userPanel.add(userIconLabel, BorderLayout.WEST);
        userPanel.add(userLabel, BorderLayout.CENTER);
        userPanel.add(dropdownArrow, BorderLayout.EAST);

        // Create a popup menu for user actions
        JPopupMenu userMenu = new JPopupMenu();

        // Add menu items
        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.setFont(AppConstants.NORMAL_FONT);
        changePasswordItem.addActionListener(e -> showChangePasswordDialog());

        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.setFont(AppConstants.NORMAL_FONT);
        logoutItem.addActionListener(e -> {
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

        userMenu.add(changePasswordItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);

        // Make the user panel clickable to show the menu
        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userMenu.show(userPanel, 0, userPanel.getHeight());
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, AppConstants.PRIMARY_COLOR),
                        BorderFactory.createEmptyBorder(0, 0, 1, 0)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                userPanel.setBorder(null);
            }
        });

        // Add a direct button for changing password
        JButton changePasswordButton = new JButton("Đổi mật khẩu");
        changePasswordButton.setFont(AppConstants.NORMAL_FONT);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordButton.addActionListener(e -> showChangePasswordDialog());

        // Add components to profile panel
        profilePanel.add(Box.createHorizontalStrut(15));
        profilePanel.add(userPanel);

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
                                            .collect(Collectors.toSet());

        // Add navigation buttons with icons
        if (danhSachTenQuyen.contains("Bán hàng")) {
            addNavigationButton("sell", "Bán hàng", "cart.png");
        }
        if (danhSachTenQuyen.contains("Quản lý sản phẩm")) {
            addNavigationButton("products", "Sản phẩm", "product.png");
        }
        if (danhSachTenQuyen.contains("Quản lý khuyến mãi")) {
            addNavigationButton("promotions", "Khuyến mãi", "promo.png");
        }
        if (danhSachTenQuyen.contains("Quản lý khách hàng")) {
            addNavigationButton("consumers", "Khách hàng", "customer.png");
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
        contentPanel.add(sanPham, "products"); // Use the sanPham instance
        contentPanel.add(new KhuyenMaiPanel(), "promotions");
        contentPanel.add(new KhachHangPanel(), "consumers");
        contentPanel.add(new NhanSuPanel(), "users");
        contentPanel.add(new NhapKhoPanel(), "import");
        contentPanel.add(thongKePanel, "statistics"); // Use the thongKePanel instance

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
                    if ("products".equals(key)) {
                        sanPham.XuatSanPhamTable();
                    } else if ("statistics".equals(key)) {
                        thongKePanel.refreshData(); 
                    }
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

    private void showChangePasswordDialog() {
        DoiMatKhauDialog dialog = new DoiMatKhauDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            JOptionPane.showMessageDialog(
                this,
                "Mật khẩu đã được thay đổi thành công. Vui lòng sử dụng mật khẩu mới trong lần đăng nhập tiếp theo.",
                "Đổi mật khẩu thành công",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
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