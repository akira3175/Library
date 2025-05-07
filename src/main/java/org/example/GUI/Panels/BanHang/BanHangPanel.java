package org.example.GUI.Panels.BanHang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.BanHangBUS;
import org.example.BUS.KhachHangBUS;
import org.example.BUS.NguoiDungBUS;
import org.example.BUS.SanPhamBUS;
import org.example.BUS.KhuyenMaiBUS;
import org.example.DTO.ChiTietHoaDon;
import org.example.DTO.GioHang;
import org.example.DTO.HoaDon;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.KhuyenMai;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BanHangPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(BanHangPanel.class);
    private JTable productTable;
    private JTable cartTable;
    private JTable hoaDonTable;
    private JTextField searchField;
    private BanHangBUS banHangBUS;
    private SanPhamBUS sanPhamBUS;
    private KhachHangBUS khachHangBUS;
    private KhuyenMaiBUS khuyenMaiBUS;
    private JLabel totalLabel;
    private JComboBox<KhuyenMai> promotionComboBox;
    private List<KhachHangDTO> selectedCustomers;
    private JLabel customerLabel;
    private JPanel imagePanel;
    private JLabel promoStatusLabel;

    public BanHangPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        banHangBUS = new BanHangBUS();
        sanPhamBUS = new SanPhamBUS();
        khachHangBUS = new KhachHangBUS();
        khuyenMaiBUS = new KhuyenMaiBUS();
        selectedCustomers = new ArrayList<>();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bán Hàng", createBanHangTab());
        tabbedPane.addTab("Quản Lý Hóa Đơn", createQuanLyHoaDonTab());
        add(tabbedPane, BorderLayout.CENTER);

        loadProductTable();
        loadPromotions();
        loadHoaDonTable();
    }

    private JPanel createBanHangTab() {
        JPanel banHangTabPanel = new JPanel(new BorderLayout(20, 20));
        banHangTabPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        banHangTabPanel.setBorder(BorderFactory.createEmptyBorder());

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        // Top panel contains header and product table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setOpaque(false);
        topPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        topPanel.add(createMainPanel(), BorderLayout.CENTER);

        // Bottom panel contains product image and cart
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(createBottomPanel(), BorderLayout.CENTER);

        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);

        banHangTabPanel.add(splitPane, BorderLayout.CENTER);
        return banHangTabPanel;
    }

    private JPanel createQuanLyHoaDonTab() {
        JPanel hoaDonTabPanel = new JPanel(new BorderLayout(10, 10));
        hoaDonTabPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        hoaDonTabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Mã Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Lập", "Tiền Giảm", "Thành Tiền", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        hoaDonTable = new JTable(model);
        hoaDonTable.setRowHeight(40);
        hoaDonTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        hoaDonTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < hoaDonTable.getColumnCount(); i++) {
            hoaDonTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        hoaDonTable.getTableHeader().setDefaultRenderer(leftRenderer);

        hoaDonTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        hoaDonTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        hoaDonTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        hoaDonTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        hoaDonTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        hoaDonTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        hoaDonTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        hoaDonTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = hoaDonTable.getSelectedRow();
                    if (row >= 0) {
                        int maHoaDon = (Integer) hoaDonTable.getValueAt(row, 0);
                        showHoaDonDialog(maHoaDon);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(hoaDonTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        hoaDonTabPanel.add(scrollPane, BorderLayout.CENTER);

        return hoaDonTabPanel;
    }

    private void showHoaDonDialog(int maHoaDon) {
        List<HoaDon> hoaDons = banHangBUS.getAllHoaDon();
        HoaDon selectedHoaDon = null;
        for (HoaDon hd : hoaDons) {
            if (hd.getMaHoaDon() == maHoaDon) {
                selectedHoaDon = hd;
                break;
            }
        }
        if (selectedHoaDon != null) {
            KhachHangDTO khachHangDTO = khachHangBUS.layKhachHangTheoMa(selectedHoaDon.getMaKhachHang());
            HoaDonDialog dialog = new HoaDonDialog(null, selectedHoaDon, banHangBUS, false, khachHangDTO, null);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("Bán hàng");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(AppConstants.NORMAL_FONT);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addActionListener(e -> searchProducts());

        StyledButton searchButton = new StyledButton("Tìm", new Color(59, 130, 246), 80, 35);
        searchButton.addActionListener(e -> searchProducts());

        StyledButton refreshButton = new StyledButton("Làm mới", new Color(107, 114, 128), 100, 35);
        refreshButton.addActionListener(e -> {
            loadProductTable();
            searchField.setText(""); // Clear the search field
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Add a title for the product table
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel tableTitle = new JLabel("Danh sách sản phẩm");
        tableTitle.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        titlePanel.add(tableTitle, BorderLayout.WEST);

        String[] columns = {"Mã SP", "Loại SP", "Tên SP", "Số lượng", "Giá bán"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 3:
                        return Integer.class;
                    case 4:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };
        productTable = new JTable(model);
        productTable.setRowHeight(40);
        productTable.getTableHeader().setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        productTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        productTable.setSelectionBackground(new Color(243, 244, 246));
        productTable.setSelectionForeground(new Color(17, 24, 39));
        productTable.setShowVerticalLines(false);
        productTable.setGridColor(new Color(229, 231, 235));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        productTable.getTableHeader().setDefaultRenderer(leftRenderer);

        productTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = productTable.getSelectedRow();
                if (row >= 0) {
                    if (e.getClickCount() == 1) {
                        displayProductImage(row);
                    } else if (e.getClickCount() == 2) {
                        addProductToCart(row);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.setOpaque(false);
        JLabel instructionLabel = new JLabel("Nhấp đúp để thêm sản phẩm vào giỏ hàng");
        instructionLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.ITALIC, 12));
        instructionLabel.setForeground(new Color(107, 114, 128));
        instructionPanel.add(instructionLabel);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(instructionPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(15, 0));
        bottomPanel.setOpaque(false);

        // Product image panel
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        imagePanel.setPreferredSize(new Dimension(200, 300));

        JLabel imageTitle = new JLabel("Hình ảnh sản phẩm", SwingConstants.CENTER);
        imageTitle.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        imagePanel.add(imageTitle, BorderLayout.NORTH);

        JLabel placeholderImage = new JLabel("Chọn sản phẩm để xem hình ảnh", SwingConstants.CENTER);
        placeholderImage.setVerticalAlignment(SwingConstants.CENTER);
        placeholderImage.setForeground(new Color(107, 114, 128));
        imagePanel.add(placeholderImage, BorderLayout.CENTER);

        JPanel cartPanel = createCartPanel();

        bottomPanel.add(imagePanel, BorderLayout.WEST);
        bottomPanel.add(cartPanel, BorderLayout.CENTER);

        return bottomPanel;
    }

    private JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cartPanel.setMinimumSize(new Dimension(694, 0));
        cartPanel.setPreferredSize(new Dimension(694, 300));

        // Cart title panel
        JPanel cartTitlePanel = new JPanel(new BorderLayout());
        cartTitlePanel.setOpaque(false);
        JLabel cartTitle = new JLabel("Giỏ hàng");
        cartTitle.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        cartTitlePanel.add(cartTitle, BorderLayout.WEST);

        // Cart table
        String[] cartColumns = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền", "Xóa"};
        DefaultTableModel cartModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return JButton.class;
                }
                switch (columnIndex) {
                    case 0:
                    case 2:
                        return Integer.class;
                    case 3:
                    case 4:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(30);
        cartTable.getTableHeader().setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        cartTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        cartTable.setSelectionBackground(new Color(243, 244, 246));
        cartTable.setSelectionForeground(new Color(17, 24, 39));
        cartTable.setShowVerticalLines(false);
        cartTable.setGridColor(new Color(229, 231, 235));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < cartTable.getColumnCount() - 1; i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        cartTable.getTableHeader().setDefaultRenderer(leftRenderer);

        cartTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        cartTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        cartTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        cartTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
        cartScrollPane.setPreferredSize(new Dimension(0, cartTable.getRowHeight() * 3 + cartTable.getTableHeader().getPreferredSize().height));
        cartScrollPane.getViewport().setBackground(Color.WHITE);

        // Customer and promotion panel
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        customerPanel.setOpaque(false);

        StyledButton selectCustomerButton = new StyledButton("Chọn khách hàng", new Color(59, 130, 246), 150, 30);
        selectCustomerButton.addActionListener(e -> selectCustomer());

        customerLabel = new JLabel("Chưa chọn khách hàng");
        customerLabel.setFont(AppConstants.NORMAL_FONT);
        customerLabel.setPreferredSize(new Dimension(150, 30));

        JLabel promoLabel = new JLabel("Khuyến mãi:");
        promoLabel.setFont(AppConstants.NORMAL_FONT);

        promotionComboBox = new JComboBox<>();
        promotionComboBox.setPreferredSize(new Dimension(200, 30));
        promotionComboBox.addActionListener(e -> updateTotal());

        // Promotion status label
        promoStatusLabel = new JLabel("");
        promoStatusLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.ITALIC, 12));
        promoStatusLabel.setForeground(new Color(107, 114, 128));

        customerPanel.add(selectCustomerButton);
        customerPanel.add(customerLabel);
        customerPanel.add(promoLabel);
        customerPanel.add(promotionComboBox);
        customerPanel.add(promoStatusLabel);

        // Checkout panel
        JPanel checkoutPanel = new JPanel(new BorderLayout());
        checkoutPanel.setOpaque(false);
        checkoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setOpaque(false);

        totalLabel = new JLabel("Tổng tiền: 0 VNĐ");
        totalLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 16));
        totalLabel.setForeground(new Color(220, 38, 38));
        totalPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        StyledButton clearCartButton = new StyledButton("Xóa giỏ hàng", new Color(239, 68, 68), 120, 35);
        clearCartButton.addActionListener(e -> {
            banHangBUS.resetCart();
            updateCartTable();
            updateTotal();
        });

        StyledButton checkoutButton = new StyledButton("Thanh toán", AppConstants.PRIMARY_COLOR, 150, 35);
        checkoutButton.addActionListener(e -> checkout());

        buttonPanel.add(clearCartButton);
        buttonPanel.add(checkoutButton);

        checkoutPanel.add(totalPanel, BorderLayout.WEST);
        checkoutPanel.add(buttonPanel, BorderLayout.EAST);

        // Add all components to cart panel
        cartPanel.add(cartTitlePanel, BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel bottomCartPanel = new JPanel(new BorderLayout());
        bottomCartPanel.setOpaque(false);
        bottomCartPanel.add(customerPanel, BorderLayout.NORTH);
        bottomCartPanel.add(checkoutPanel, BorderLayout.SOUTH);

        cartPanel.add(bottomCartPanel, BorderLayout.SOUTH);

        return cartPanel;
    }

    public void loadProductTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            model.setRowCount(0);
            sanPhamBUS.hienThiSanPhamLenTableBanHang(productTable);

            logger.info("Số hàng trong model sau khi tải: {}", model.getRowCount());

            if (model.getRowCount() == 0) {
                logger.warn("Không có sản phẩm đang hoạt động nào được tải.");
                JOptionPane.showMessageDialog(this, "Không có sản phẩm đang hoạt động!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logger.info("Đã tải {} sản phẩm đang hoạt động.", model.getRowCount());
            }

            productTable.revalidate();
            productTable.repaint();
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách sản phẩm: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadHoaDonTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
            model.setRowCount(0);
            List<HoaDon> hoaDons = banHangBUS.getAllHoaDon();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (HoaDon hd : hoaDons) {
                KhachHangDTO khachHang = khachHangBUS.layKhachHangTheoMa(hd.getMaKhachHang());
                model.addRow(new Object[]{
                        hd.getMaHoaDon(),
                        NguoiDungBUS.getNguoiDungHienTai().getHoTen(),
                        khachHang != null ? khachHang.getHoTen() : "Khách kê",
                        sdf.format(hd.getNgayLap()),
                        String.format("%,d", hd.getTienGiam()),
                        String.format("%,d", hd.getThanhTien()),
                        hd.isTrangThai() ? "Hoạt động" : "Hủy"
                });
            }

            logger.info("Đã tải {} hóa đơn.", hoaDons.size());
            hoaDonTable.revalidate();
            hoaDonTable.repaint();
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách hóa đơn: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchProducts() {
        String tuKhoa = searchField.getText().trim();
        try {
            sanPhamBUS.HienThiSanPhamTimKiem(productTable, tuKhoa);
            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            logger.info("Tìm kiếm sản phẩm với từ khóa '{}', tìm thấy {} kết quả.", tuKhoa, model.getRowCount());
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            productTable.revalidate();
            productTable.repaint();
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm sản phẩm: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProductImage(int row) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        int maSanPham = Integer.parseInt(model.getValueAt(row, 0).toString());
        String tenSanPham = model.getValueAt(row, 2).toString();

        String imageUrl = sanPhamBUS.getAnhSanPhamURL(maSanPham);
        imagePanel.removeAll();

        // Add title
        JLabel titleLabel = new JLabel(tenSanPham, SwingConstants.CENTER);
        titleLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        imagePanel.add(titleLabel, BorderLayout.NORTH);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                java.net.URL resourceUrl = getClass().getResource(imageUrl);
                ImageIcon originalIcon;
                if (resourceUrl != null) {
                    originalIcon = new ImageIcon(resourceUrl);
                } else {
                    String filePath = "src/main/resources" + imageUrl;
                    File imageFile = new File(filePath);
                    if (imageFile.exists()) {
                        originalIcon = new ImageIcon(filePath);
                    } else {
                        throw new Exception("Image file not found: " + imageUrl);
                    }
                }

                if (originalIcon.getImage() == null) {
                    throw new Exception("Failed to load image: " + imageUrl);
                }

                Image img = originalIcon.getImage();
                int maxWidth = 180;
                int maxHeight = 220;
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();

                int newWidth, newHeight;
                if ((float) originalWidth / originalHeight > (float) maxWidth / maxHeight) {
                    newWidth = maxWidth;
                    newHeight = (int) (maxWidth * originalHeight / originalWidth);
                } else {
                    newHeight = maxHeight;
                    newWidth = (int) (maxHeight * originalWidth / originalHeight);
                }

                Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);

                JPanel centerPanel = new JPanel(new BorderLayout());
                centerPanel.setOpaque(false);
                centerPanel.add(imageLabel, BorderLayout.CENTER);
                imagePanel.add(centerPanel, BorderLayout.CENTER);

                logger.info("Hiển thị hình ảnh cho sản phẩm {}: {}", maSanPham, imageUrl);
            } catch (Exception e) {
                logger.warn("Không thể tải hình ảnh cho sản phẩm {}: {}", maSanPham, e.getMessage());
                JLabel placeholder = new JLabel("Không có hình ảnh", SwingConstants.CENTER);
                placeholder.setHorizontalAlignment(SwingConstants.CENTER);
                placeholder.setVerticalAlignment(SwingConstants.CENTER);
                placeholder.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.ITALIC, 14));
                placeholder.setForeground(new Color(107, 114, 128));
                imagePanel.add(placeholder, BorderLayout.CENTER);
            }
        } else {
            logger.warn("Không có URL hình ảnh cho sản phẩm {}", maSanPham);
            JLabel placeholder = new JLabel("Không có hình ảnh", SwingConstants.CENTER);
            placeholder.setHorizontalAlignment(SwingConstants.CENTER);
            placeholder.setVerticalAlignment(SwingConstants.CENTER);
            placeholder.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.ITALIC, 14));
            placeholder.setForeground(new Color(107, 114, 128));
            imagePanel.add(placeholder, BorderLayout.CENTER);
        }

        // Add a button to add to cart
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        StyledButton addToCartButton = new StyledButton("Thêm vào giỏ", AppConstants.PRIMARY_COLOR, 120, 30);
        addToCartButton.addActionListener(e -> addProductToCart(row));
        buttonPanel.add(addToCartButton);
        imagePanel.add(buttonPanel, BorderLayout.SOUTH);

        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void addProductToCart(int row) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        int maSanPham = Integer.parseInt(model.getValueAt(row, 0).toString());
        String tenSanPham = model.getValueAt(row, 2).toString();
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng:", "1");
        if (input == null) {
            return;
        }

        try {
            int soLuong = Integer.parseInt(input);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check available stock
            SanPhamDTO sanPham = sanPhamBUS.laySanPhamTheoMa(maSanPham);
            if (sanPham == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate current quantity in cart for this product
            int currentCartQuantity = 0;
            for (GioHang item : banHangBUS.getCart()) {
                if (item.getSanPham().getMaSanPham() == maSanPham) {
                    currentCartQuantity += item.getSoLuong();
                }
            }

            // Check if total quantity (cart + requested) exceeds stock
            if (currentCartQuantity + soLuong > sanPham.getSoLuong()) {
                JOptionPane.showMessageDialog(this, 
                    String.format("Số lượng của '%s' không đủ! (Tồn kho: %d, Đã trong giỏ: %d, Yêu cầu thêm: %d)", 
                        tenSanPham, sanPham.getSoLuong(), currentCartQuantity, soLuong), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                logger.warn("Không thể thêm sản phẩm {}: Tổng số lượng (giỏ: {} + yêu cầu: {}) vượt quá tồn kho ({}).", 
                    maSanPham, currentCartQuantity, soLuong, sanPham.getSoLuong());
                return;
            }

            banHangBUS.addToCart(maSanPham, soLuong);
            updateCartTable();
            updateTotal();
            logger.info("Thêm sản phẩm {} với số lượng {} vào giỏ hàng.", maSanPham, soLuong);
            loadProductTable();
        } catch (NumberFormatException e) {
            logger.warn("Số lượng không hợp lệ: {}", input);
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            logger.error("Lỗi khi thêm sản phẩm vào giỏ hàng: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCartTable() {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0);
        imagePanel.removeAll();

        for (GioHang item : banHangBUS.getCart()) {
            model.addRow(new Object[]{
                    item.getSanPham().getMaSanPham(),
                    item.getSanPham().getTenSanPham(),
                    item.getSoLuong(),
                    String.format("%,d", (int) item.getSanPham().getGiaLoi() + item.getSanPham().getGiaVon()),
                    String.format("%,d", (int) (item.getSanPham().getGiaLoi() + item.getSanPham().getGiaVon()) * item.getSoLuong()),
                    "Xóa"
            });
        }

        cartTable.revalidate();
        cartTable.repaint();
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void removeFromCart(int maSanPham) {
        try {
            int soLuong = 0;
            for (GioHang item : banHangBUS.getCart()) {
                if (item.getSanPham().getMaSanPham() == maSanPham) {
                    soLuong = item.getSoLuong();
                    break;
                }
            }

            banHangBUS.removeFromCart(maSanPham);

            SanPhamDTO sanPham = sanPhamBUS.laySanPhamTheoMa(maSanPham);
            if (sanPham != null) {
                sanPham.setSoLuong(sanPham.getSoLuong() + soLuong);
                sanPhamBUS.suaSanPham(sanPham);
                logger.info("Khôi phục {} đơn vị cho sản phẩm {} trong kho.", soLuong, maSanPham);
            }

            updateCartTable();
            updateTotal();
            loadProductTable();
            logger.info("Xóa sản phẩm {} khỏi giỏ hàng.", maSanPham);
        } catch (Exception e) {
            logger.error("Lỗi khi xóa sản phẩm {} khỏi giỏ hàng: {}", maSanPham, e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectCustomer() {
        CustomerSelectionDialog dialog = new CustomerSelectionDialog(null);
        dialog.setVisible(true);
        selectedCustomers = dialog.getSelectedCustomers();

        if (!selectedCustomers.isEmpty()) {
            StringBuilder customerNames = new StringBuilder();
            for (int i = 0; i < selectedCustomers.size(); i++) {
                customerNames.append(selectedCustomers.get(i).getHoTen());
                if (i < selectedCustomers.size() - 1) {
                    customerNames.append(", ");
                }
            }
            customerLabel.setText(customerNames.toString());
            logger.info("Đã chọn {} khách hàng.", selectedCustomers.size());
        } else {
            customerLabel.setText("");
            logger.info("Không có khách hàng nào được chọn.");
        }
    }

    private void loadPromotions() {
        try {
            promotionComboBox.removeAllItems();

            KhuyenMai noPromo = new KhuyenMai();
            noPromo.setMaKhuyenMai(0);
            noPromo.setTenKhuyenMai("Không áp dụng");
            promotionComboBox.addItem(noPromo);

            List<KhuyenMai> activePromotions = khuyenMaiBUS.layDanhSachKhuyenMaiHoatDong();
            for (KhuyenMai km : activePromotions) {
                promotionComboBox.addItem(km);
            }

            logger.info("Đã tải {} khuyến mãi hoạt động từ cơ sở dữ liệu.", activePromotions.size());

            if (activePromotions.isEmpty()) {
                logger.warn("Không có khuyến mãi hoạt động nào được tìm thấy trong cơ sở dữ liệu.");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách khuyến mãi: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khuyến mãi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotal() {
        int total = 0;
        for (GioHang item : banHangBUS.getCart()) {
            total += (int) ((item.getSanPham().getGiaLoi() + item.getSanPham().getGiaVon()) * item.getSoLuong());
        }
        KhuyenMai selectedPromo = (KhuyenMai) promotionComboBox.getSelectedItem();
        int discount = 0;
        String promoStatus = "";

        if (selectedPromo != null && selectedPromo.getMaKhuyenMai() > 0) {
            if (total >= selectedPromo.getDieuKienHoaDon()) {
                discount = selectedPromo.getSoTienKhuyenMai();
                promoStatus = "Khuyến mãi được áp dụng";
                promoStatusLabel.setForeground(new Color(34, 139, 34)); // Green for success
            } else {
                promoStatus = String.format("Cần hóa đơn tối thiểu %,d VNĐ để áp dụng khuyến mãi", selectedPromo.getDieuKienHoaDon());
                promoStatusLabel.setForeground(new Color(220, 38, 38)); // Red for error
                // Show notification only once per invalid selection
                JOptionPane.showMessageDialog(this, 
                    String.format("Không thể áp dụng khuyến mãi '%s': Tổng hóa đơn (%,d VNĐ) chưa đạt yêu cầu tối thiểu (%,d VNĐ).", 
                        selectedPromo.getTenKhuyenMai(), total, selectedPromo.getDieuKienHoaDon()), 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            promoStatus = "Không áp dụng khuyến mãi";
            promoStatusLabel.setForeground(new Color(107, 114, 128)); // Gray for neutral
        }

        totalLabel.setText(String.format("Tổng tiền: %,d VNĐ", total - discount));
        promoStatusLabel.setText(promoStatus);
    }

    private void checkout() {
        if (banHangBUS.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedCustomers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            KhuyenMai selectedPromo = (KhuyenMai) promotionComboBox.getSelectedItem();
            KhachHangDTO khachHangDTO = selectedCustomers.get(0);

            // Create the invoice immediately
            HoaDon savedHoaDon = banHangBUS.taoHoaDon(khachHangDTO, selectedPromo);

            // Show the invoice details dialog
            HoaDonDialog dialog = new HoaDonDialog(null, savedHoaDon, banHangBUS, false, khachHangDTO, selectedPromo);
            dialog.setVisible(true);

            // Reset the cart and UI
            resetCartUI();
            loadHoaDonTable();

            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Mã hóa đơn: " + savedHoaDon.getMaHoaDon(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            logger.info("Tạo hóa đơn thành công: {}", savedHoaDon.getMaHoaDon());
        } catch (RuntimeException e) {
            logger.error("Lỗi khi thanh toán: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetCartUI() {
        customerLabel.setText("Chưa chọn khách hàng");
        banHangBUS.resetCart();
        updateCartTable();
        updateTotal();
        selectedCustomers.clear();
        customerLabel.setText("");
        loadProductTable();
        loadPromotions();
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        promoStatusLabel.setText("");
        logger.info("Giỏ hàng UI đã được reset.");
    }

    class ButtonRenderer extends DefaultTableCellRenderer {
        private StyledButton button;

        public ButtonRenderer() {
            button = new StyledButton("Xóa", new Color(239, 68, 68), 70, 25);
            button.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return button;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private StyledButton button;
        private String label;
        private boolean isPushed;
        private int maSanPham;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new StyledButton("Xóa", new Color(239, 68, 68), 70, 25);
            button.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 12));
            button.addActionListener(e -> {
                isPushed = true;
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Xóa" : value.toString();
            button.setText(label);
            if (row >= 0 && row < table.getRowCount()) {
                maSanPham = (Integer) table.getValueAt(row, 0);
            } else {
                maSanPham = -1;
            }
            isPushed = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed && maSanPham != -1) {
                if (cartTable.getRowCount() > 0) {
                    removeFromCart(maSanPham);
                } else {
                    logger.warn("Không thể xóa sản phẩm: Giỏ hàng rỗng hoặc hàng không hợp lệ.");
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}