package org.example.GUI.Panels.BanHang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

        banHangTabPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        banHangTabPanel.add(createMainPanel(), BorderLayout.CENTER);
        banHangTabPanel.add(createBottomPanel(), BorderLayout.SOUTH);
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

        JLabel titleLabel = new JLabel("Bán hàng");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(200, 35));

        JButton searchButton = new JButton("Tìm");
        searchButton.setPreferredSize(new Dimension(80, 35));
        searchButton.addActionListener(e -> searchProducts());

        actionPanel.add(searchField);
        actionPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

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
        productTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        productTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        productTable.getTableHeader().setDefaultRenderer(leftRenderer);

        productTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(150);

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

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 10)
        ));
        imagePanel.setPreferredSize(new Dimension(200, 300));

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
        // Set minimum width to 694 and preferred height to 200
        cartPanel.setMinimumSize(new Dimension(694, 0));
        cartPanel.setPreferredSize(new Dimension(694, 300));

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
        cartTable.setRowHeight(30); // Reduced from 40 to make table more compact
        cartTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        cartTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < cartTable.getColumnCount() - 1; i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        cartTable.getTableHeader().setDefaultRenderer(leftRenderer);

        cartTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        cartTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        cartTable.getColumnModel().getColumn(5).setPreferredWidth(80);

        cartTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        cartTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // Set preferred size for scroll pane to limit table height (e.g., 3 rows + header)
        cartScrollPane.setPreferredSize(new Dimension(0, cartTable.getRowHeight() * 3 + cartTable.getTableHeader().getPreferredSize().height));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);

        StyledButton selectCustomerButton = new StyledButton("Chọn khách hàng", AppConstants.PRIMARY_COLOR, 150, 30); // Reduced height
        selectCustomerButton.addActionListener(e -> selectCustomer());

        customerLabel = new JLabel("");
        customerLabel.setFont(AppConstants.NORMAL_FONT);
        customerLabel.setPreferredSize(new Dimension(200, 30)); // Reduced height

        promotionComboBox = new JComboBox<>();
        promotionComboBox.setPreferredSize(new Dimension(150, 30)); // Reduced height
        promotionComboBox.addActionListener(e -> updateTotal());

        totalLabel = new JLabel("Tổng tiền: 0 VNĐ");
        totalLabel.setFont(AppConstants.NORMAL_FONT);

        actionPanel.add(selectCustomerButton);
        actionPanel.add(customerLabel);
        actionPanel.add(new JLabel("Khuyến mãi:"));
        actionPanel.add(promotionComboBox);

        JPanel checkoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        checkoutPanel.setOpaque(false);
        StyledButton checkoutButton = new StyledButton("Thanh toán", AppConstants.PRIMARY_COLOR, 150, 30); // Reduced height
        checkoutButton.addActionListener(e -> checkout());
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(checkoutButton);

        cartPanel.add(actionPanel, BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        cartPanel.add(checkoutPanel, BorderLayout.SOUTH);

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

        imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

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
                int maxWidth = 200;
                int maxHeight = 300;
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
                imagePanel.add(imageLabel, BorderLayout.CENTER);
                logger.info("Hiển thị hình ảnh cho sản phẩm {}: {}", maSanPham, imageUrl);
            } catch (Exception e) {
                logger.warn("Không thể tải hình ảnh cho sản phẩm {}: {}", maSanPham, e.getMessage());
                JLabel placeholder = new JLabel("No Image", SwingConstants.CENTER);
                placeholder.setHorizontalAlignment(SwingConstants.CENTER);
                placeholder.setVerticalAlignment(SwingConstants.CENTER);
                imagePanel.add(placeholder, BorderLayout.CENTER);
            }
        } else {
            logger.warn("Không có URL hình ảnh cho sản phẩm {}", maSanPham);
            JLabel placeholder = new JLabel("No Image", SwingConstants.CENTER);
            placeholder.setHorizontalAlignment(SwingConstants.CENTER);
            placeholder.setVerticalAlignment(SwingConstants.CENTER);
            imagePanel.add(placeholder, BorderLayout.CENTER);
        }

        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void addProductToCart(int row) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        int maSanPham = Integer.parseInt(model.getValueAt(row, 0).toString());
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng:", "1");
        if (input == null) {
            return;
        }

        try {
            int soLuong = Integer.parseInt(input);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            banHangBUS.addToCart(maSanPham, soLuong);
            updateCartTable();
            updateTotal();
            logger.info("Thêm sản phẩm {} với số lượng {} vào giỏ hàng.", maSanPham, soLuong);
            loadProductTable();
        } catch (NumberFormatException e) {
            logger.warn("Số lượng không hợp lệ: {}", input);
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
        } catch (RuntimeException e) {
            logger.error("Lỗi khi thêm sản phẩm vào giỏ hàng: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, e.getMessage());
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
                String.format("%,d", (int) item.getSanPham().getGiaLoi()),
                String.format("%,d", (int) (item.getSanPham().getGiaLoi() * item.getSoLuong())),
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
            total += (int) (item.getSanPham().getGiaLoi() * item.getSoLuong());
        }
        KhuyenMai selectedPromo = (KhuyenMai) promotionComboBox.getSelectedItem();
        int discount = (selectedPromo != null && selectedPromo.getMaKhuyenMai() > 0 && total >= selectedPromo.getDieuKienHoaDon()) ? selectedPromo.getSoTienKhuyenMai() : 0;
        totalLabel.setText(String.format("Tổng tiền: %,d VNĐ", total - discount));
    }

    private void checkout() {
        if (banHangBUS.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }
        if (selectedCustomers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một khách hàng!");
            return;
        }

        try {
            KhuyenMai selectedPromo = (KhuyenMai) promotionComboBox.getSelectedItem();
            KhachHangDTO khachHangDTO = selectedCustomers.get(0);

            HoaDon tempHoaDon = new HoaDon();
            tempHoaDon.setMaNguoiDung(NguoiDungBUS.getNguoiDungHienTai().getMaNguoiDung());
            tempHoaDon.setMaKhachHang(khachHangDTO.getMaKhachHang());
            tempHoaDon.setNgayLap(new Date());
            tempHoaDon.setMaHoaDon(0);

            int thanhTien = 0;
            List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
            for (GioHang item : banHangBUS.getCart()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setMaSanPham(item.getSanPham().getMaSanPham());
                chiTiet.setSoLuong(item.getSoLuong());
                chiTiet.setDonGia((int) item.getSanPham().getGiaLoi());
                chiTietHoaDons.add(chiTiet);
                thanhTien += chiTiet.getDonGia() * chiTiet.getSoLuong();
            }

            int tienGiam = 0;
            if (selectedPromo != null && selectedPromo.getMaKhuyenMai() > 0) {
                if (thanhTien >= selectedPromo.getDieuKienHoaDon()) {
                    tempHoaDon.setMaKhuyenMai(selectedPromo.getMaKhuyenMai());
                    tienGiam = selectedPromo.getSoTienKhuyenMai();
                }
            }
            tempHoaDon.setTienGiam(tienGiam);
            tempHoaDon.setThanhTien(thanhTien - tienGiam);
            tempHoaDon.setChiTietHoaDons(chiTietHoaDons);

            HoaDonDialog dialog = new HoaDonDialog(null, tempHoaDon, banHangBUS, true, khachHangDTO, selectedPromo);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                loadProductTable();
                updateCartTable();
                updateTotal();
                resetCartUI();
                loadHoaDonTable();
            }
        } catch (RuntimeException e) {
            logger.error("Lỗi khi chuẩn bị hóa đơn: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void resetCartUI() {
        banHangBUS.resetCart();
        updateCartTable();
        updateTotal();
        selectedCustomers.clear();
        customerLabel.setText("");
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        logger.info("Giỏ hàng UI đã được reset.");
    }

    class ButtonRenderer extends DefaultTableCellRenderer {

        private StyledButton button;

        public ButtonRenderer() {
            button = new StyledButton("Xóa", new Color(239, 68, 68), 80, 30);
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
            button = new StyledButton("Xóa", new Color(239, 68, 68), 80, 30);
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
