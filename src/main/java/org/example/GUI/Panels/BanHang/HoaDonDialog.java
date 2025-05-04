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
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setBackground(AppConstants.BACKGROUND_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CHI TIẾT HÓA ĐƠN");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setOpaque(false);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        infoPanel.add(new JLabel("Mã hóa đơn: " + hoaDon.getMaHoaDon()));
        infoPanel.add(new JLabel("Nhân viên: " + NguoiDungBUS.getNguoiDungHienTai().getHoTen()));
        infoPanel.add(new JLabel("Khách hàng: " + (khachHangDTO != null ? khachHangDTO.getHoTen() : "Khách hàng ID " + hoaDon.getMaKhachHang())));
        infoPanel.add(new JLabel("Ngày lập: " + sdf.format(hoaDon.getNgayLap())));
        logger.info("Khach1 hang: " + (khachHangDTO != null ? khachHangDTO.getHoTen() : "Khách hàng ID " + hoaDon.getMaKhachHang()));

        headerPanel.add(infoPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] columns = {"STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietTable = new JTable(model);
        chiTietTable.setRowHeight(30);
        chiTietTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        chiTietTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Left-align all cells and headers
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < chiTietTable.getColumnCount(); i++) {
            chiTietTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
        chiTietTable.getTableHeader().setDefaultRenderer(leftRenderer);

        // Set column widths
        chiTietTable.getColumnModel().getColumn(0).setPreferredWidth(50); // STT
        chiTietTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên sản phẩm
        chiTietTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Số lượng
        chiTietTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Đơn giá
        chiTietTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Thành tiền

        loadChiTietHoaDon();

        JScrollPane scrollPane = new JScrollPane(chiTietTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        summaryPanel.setOpaque(false);
        summaryPanel.add(new JLabel("Tổng tiền: " + String.format("%,d", hoaDon.getThanhTien() + hoaDon.getTienGiam())));
        summaryPanel.add(new JLabel("Giảm giá: " + String.format("%,d", hoaDon.getTienGiam())));
        summaryPanel.add(new JLabel("Thành tiền: " + String.format("%,d", hoaDon.getThanhTien())));
        tablePanel.add(summaryPanel, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);

        // Button Panel (for checkout mode only)
        if (isCheckoutMode) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            StyledButton printButton = new StyledButton("In Hóa Đơn", AppConstants.PRIMARY_COLOR, 150, 35);
//            printButton.addActionListener(e -> printHoaDon());

            StyledButton confirmButton = new StyledButton("Xác Nhận", AppConstants.PRIMARY_COLOR, 150, 35);
            confirmButton.addActionListener(e -> confirmHoaDon());

            buttonPanel.add(printButton);
            buttonPanel.add(confirmButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
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

//    private void printHoaDon() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setSelectedFile(new File("HoaDon_" + hoaDon.getMaHoaDon() + ".pdf"));
//        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
//            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
//            try {
//                banHangBUS.exportHoaDonToPDF(hoaDon, filePath);
//                JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu tại: " + filePath);
//                logger.info("In hóa đơn {} thành công: {}", hoaDon.getMaHoaDon(), filePath);
//            } catch (RuntimeException e) {
//                logger.error("Lỗi khi in hóa đơn: {}", e.getMessage(), e);
//                JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }

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