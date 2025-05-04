package org.example.GUI.Panels.BanHang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.BanHangBUS;
import org.example.BUS.NguoiDungBUS;
import org.example.DTO.ChiTietHoaDon;
import org.example.DTO.HoaDon;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.KhuyenMai;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoaDonDialog extends JDialog {
    private static final Logger logger = LoggerFactory.getLogger(HoaDonDialog.class);
    private HoaDon hoaDon;
    private BanHangBUS banHangBUS;
    private boolean isCheckoutMode;
    private boolean confirmed;
    private KhachHangDTO khachHangDTO;
    private KhuyenMai khuyenMai;
    private JTable chiTietTable;

    public HoaDonDialog(JFrame parent, HoaDon hoaDon, BanHangBUS banHangBUS, boolean isCheckoutMode,
                        KhachHangDTO khachHangDTO, KhuyenMai khuyenMai) {
        super(parent, "Chi Tiết Hóa Đơn", true);
        this.hoaDon = hoaDon;
        this.banHangBUS = banHangBUS;
        this.isCheckoutMode = isCheckoutMode;
        this.confirmed = false;
        this.khachHangDTO = khachHangDTO;
        this.khuyenMai = khuyenMai;
        initComponents();
    }

    private void initComponents() {
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(AppConstants.BACKGROUND_COLOR);

        // Header Panel with invoice information
        JPanel headerPanel = new JPanel(new BorderLayout(0, 10));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        // Title
        JLabel titleLabel = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        titleLabel.setFont(new Font(AppConstants.HEADER_FONT.getFamily(), Font.BOLD, 18));
        titleLabel.setForeground(AppConstants.PRIMARY_COLOR);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(229, 231, 235));

        // Invoice info panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Left column
        JPanel leftInfoPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        leftInfoPanel.setOpaque(false);

        JPanel invoiceIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        invoiceIdPanel.setOpaque(false);
        JLabel invoiceIdLabel = new JLabel("Mã hóa đơn:");
        invoiceIdLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel invoiceIdValue = new JLabel(String.valueOf(hoaDon.getMaHoaDon()));
        invoiceIdValue.setFont(AppConstants.NORMAL_FONT);
        invoiceIdPanel.add(invoiceIdLabel);
        invoiceIdPanel.add(invoiceIdValue);

        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        customerPanel.setOpaque(false);
        JLabel customerLabel = new JLabel("Khách hàng:");
        customerLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel customerValue = new JLabel(khachHangDTO != null ? khachHangDTO.getHoTen() : "Khách hàng ID " + hoaDon.getMaKhachHang());
        customerValue.setFont(AppConstants.NORMAL_FONT);
        customerPanel.add(customerLabel);
        customerPanel.add(customerValue);

        leftInfoPanel.add(invoiceIdPanel);
        leftInfoPanel.add(customerPanel);

        // Right column
        JPanel rightInfoPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        rightInfoPanel.setOpaque(false);

        JPanel staffPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        staffPanel.setOpaque(false);
        JLabel staffLabel = new JLabel("Nhân viên:");
        staffLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel staffValue = new JLabel(NguoiDungBUS.getNguoiDungHienTai().getHoTen());
        staffValue.setFont(AppConstants.NORMAL_FONT);
        staffPanel.add(staffLabel);
        staffPanel.add(staffValue);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setOpaque(false);
        JLabel dateLabel = new JLabel("Ngày lập:");
        dateLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel dateValue = new JLabel(sdf.format(hoaDon.getNgayLap()));
        dateValue.setFont(AppConstants.NORMAL_FONT);
        datePanel.add(dateLabel);
        datePanel.add(dateValue);

        rightInfoPanel.add(staffPanel);
        rightInfoPanel.add(datePanel);

        infoPanel.add(leftInfoPanel);
        infoPanel.add(rightInfoPanel);

        JPanel headerContentPanel = new JPanel(new BorderLayout(0, 5));
        headerContentPanel.setOpaque(false);
        headerContentPanel.add(titleLabel, BorderLayout.NORTH);
        headerContentPanel.add(separator, BorderLayout.CENTER);
        headerContentPanel.add(infoPanel, BorderLayout.SOUTH);

        headerPanel.add(headerContentPanel, BorderLayout.CENTER);

        // Table Panel with invoice details
        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Table title
        JLabel tableTitle = new JLabel("Chi tiết sản phẩm");
        tableTitle.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));

        String[] columns = {"STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietTable = new JTable(model);
        chiTietTable.setRowHeight(35);
        chiTietTable.getTableHeader().setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        chiTietTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        chiTietTable.setSelectionBackground(new Color(243, 244, 246));
        chiTietTable.setSelectionForeground(new Color(17, 24, 39));
        chiTietTable.setShowVerticalLines(false);
        chiTietTable.setGridColor(new Color(229, 231, 235));

        // Left-align all cells and headers
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < chiTietTable.getColumnCount(); i++) {
            chiTietTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        chiTietTable.getTableHeader().setDefaultRenderer(leftRenderer);

        // Set column widths
        chiTietTable.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        chiTietTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Tên sản phẩm
        chiTietTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Số lượng
        chiTietTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Đơn giá
        chiTietTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Thành tiền

        loadChiTietHoaDon();

        JScrollPane scrollPane = new JScrollPane(chiTietTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setOpaque(false);
        JLabel totalLabel = new JLabel("Tổng tiền:");
        totalLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel totalValue = new JLabel(String.format("%,d VNĐ", hoaDon.getThanhTien() + hoaDon.getTienGiam()));
        totalValue.setFont(AppConstants.NORMAL_FONT);
        totalPanel.add(totalLabel);
        totalPanel.add(totalValue);

        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        discountPanel.setOpaque(false);
        JLabel discountLabel = new JLabel("Giảm giá:");
        discountLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        JLabel discountValue = new JLabel(String.format("%,d VNĐ", hoaDon.getTienGiam()));
        discountValue.setFont(AppConstants.NORMAL_FONT);
        discountPanel.add(discountLabel);
        discountPanel.add(discountValue);

        JPanel finalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        finalPanel.setOpaque(false);
        JLabel finalLabel = new JLabel("Thành tiền:");
        finalLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 16));
        JLabel finalValue = new JLabel(String.format("%,d VNĐ", hoaDon.getThanhTien()));
        finalValue.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 16));
        finalValue.setForeground(new Color(220, 38, 38));
        finalPanel.add(finalLabel);
        finalPanel.add(finalValue);

        summaryPanel.add(totalPanel);
        summaryPanel.add(discountPanel);
        summaryPanel.add(finalPanel);

        JPanel tableContentPanel = new JPanel(new BorderLayout());
        tableContentPanel.setOpaque(false);
        tableContentPanel.add(tableTitle, BorderLayout.NORTH);
        tableContentPanel.add(scrollPane, BorderLayout.CENTER);
        tableContentPanel.add(summaryPanel, BorderLayout.SOUTH);

        tablePanel.add(tableContentPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        StyledButton printButton = new StyledButton("In Hóa Đơn", new Color(59, 130, 246), 150, 35);
        printButton.addActionListener(e -> printHoaDon());

        StyledButton closeButton = new StyledButton("Đóng", new Color(107, 114, 128), 100, 35);
        closeButton.addActionListener(e -> dispose());

        if (isCheckoutMode) {
            StyledButton confirmButton = new StyledButton("Xác Nhận", AppConstants.PRIMARY_COLOR, 150, 35);
            confirmButton.addActionListener(e -> confirmHoaDon());
            buttonPanel.add(confirmButton);
        }

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        // Add all panels to the dialog
        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadChiTietHoaDon() {
        DefaultTableModel model = (DefaultTableModel) chiTietTable.getModel();
        model.setRowCount(0);

        int stt = 1;
        for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDons()) {
            SanPhamDTO sanPham = banHangBUS.laySanPhamTheoMa(chiTiet.getMaSanPham());
            model.addRow(new Object[]{
                    stt++,
                    sanPham != null ? sanPham.getTenSanPham() : "Unknown",
                    chiTiet.getSoLuong(),
                    String.format("%,d", chiTiet.getDonGia()),
                    String.format("%,d", chiTiet.getDonGia() * chiTiet.getSoLuong())
            });
        }
    }

    private void printHoaDon() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu hóa đơn PDF");
        fileChooser.setSelectedFile(new File("HoaDon_" + hoaDon.getMaHoaDon() + ".pdf"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                // Here you would implement the PDF export functionality
                banHangBUS.exportHoaDonToPDF(hoaDon, filePath);
                // For now, we'll just show a success message
                JOptionPane.showMessageDialog(this,
                        "Hóa đơn đã được xuất ra file: " + filePath,
                        "Xuất PDF thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                logger.info("Xuất hóa đơn {} thành công: {}", hoaDon.getMaHoaDon(), filePath);
            } catch (Exception e) {
                logger.error("Lỗi khi xuất hóa đơn PDF: {}", e.getMessage(), e);
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất hóa đơn: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void confirmHoaDon() {
        try {
            if (khachHangDTO == null) {
                throw new RuntimeException("Không có thông tin khách hàng!");
            }
            // Save the invoice
            HoaDon savedHoaDon = banHangBUS.taoHoaDon(khachHangDTO, khuyenMai);
            confirmed = true;
            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Mã hóa đơn: " + savedHoaDon.getMaHoaDon());
            logger.info("Xác nhận hóa đơn thành công: {}", savedHoaDon.getMaHoaDon());
            dispose();
        } catch (RuntimeException e) {
            logger.error("Lỗi khi xác nhận hóa đơn: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi xác nhận hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
