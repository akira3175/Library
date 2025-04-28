package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class SanPham_Loc_Dialog extends JDialog {
    private SanPhamBUS sanPhamBUS;
    private JTable tbSanPham;
    private JComboBox<String> loaiSanPhamComboBox;
    private JTextField minGiaVonField, maxGiaVonField;
    private JTextField minGiaLoiField, maxGiaLoiField;
    private JTextField minSoLuongField, maxSoLuongField;
    private JComboBox<String> trangThaiComboBox;

    public SanPham_Loc_Dialog(Frame parent, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham) {
        super(parent, "Lọc sản phẩm", modal);
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
        initComponents();
        loadLoaiSanPham();
        setMinimumSize(new Dimension(600, 450));
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Loại sản phẩm:", taoLoaiSanPhamComboBox(), gbc, 0);
        addFormField(formPanel, "Giá vốn từ:", taoMinGiaVonField(), gbc, 1);
        addFormField(formPanel, "Giá vốn đến:", taoMaxGiaVonField(), gbc, 2);
        addFormField(formPanel, "Giá lời từ:", taoMinGiaLoiField(), gbc, 3);
        addFormField(formPanel, "Giá lời đến:", taoMaxGiaLoiField(), gbc, 4);
        addFormField(formPanel, "Số lượng từ:", taoMinSoLuongField(), gbc, 5);
        addFormField(formPanel, "Số lượng đến:", taoMaxSoLuongField(), gbc, 6);
        addFormField(formPanel, "Trạng thái:", taoTrangThaiComboBox(), gbc, 7);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);
        StyledButton applyButton = new StyledButton("Áp dụng", AppConstants.PRIMARY_COLOR, 100, 35);

        cancelButton.addActionListener(e -> dispose());
        applyButton.addActionListener(e -> applyFilter());

        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private JComboBox<String> taoLoaiSanPhamComboBox() {
        loaiSanPhamComboBox = new JComboBox<>();
        loaiSanPhamComboBox.addItem("Tất cả");
        loaiSanPhamComboBox.setFont(AppConstants.NORMAL_FONT);
        return loaiSanPhamComboBox;
    }

    private JTextField taoMinGiaVonField() {
        minGiaVonField = new JTextField();
        minGiaVonField.setFont(AppConstants.NORMAL_FONT);
        return minGiaVonField;
    }

    private JTextField taoMaxGiaVonField() {
        maxGiaVonField = new JTextField();
        maxGiaVonField.setFont(AppConstants.NORMAL_FONT);
        return maxGiaVonField;
    }

    private JTextField taoMinGiaLoiField() {
        minGiaLoiField = new JTextField();
        minGiaLoiField.setFont(AppConstants.NORMAL_FONT);
        return minGiaLoiField;
    }

    private JTextField taoMaxGiaLoiField() {
        maxGiaLoiField = new JTextField();
        maxGiaLoiField.setFont(AppConstants.NORMAL_FONT);
        return maxGiaLoiField;
    }

    private JTextField taoMinSoLuongField() {
        minSoLuongField = new JTextField();
        minSoLuongField.setFont(AppConstants.NORMAL_FONT);
        return minSoLuongField;
    }

    private JTextField taoMaxSoLuongField() {
        maxSoLuongField = new JTextField();
        maxSoLuongField.setFont(AppConstants.NORMAL_FONT);
        return maxSoLuongField;
    }

    private JComboBox<String> taoTrangThaiComboBox() {
        trangThaiComboBox = new JComboBox<>(new String[]{"Tất cả", "Đang hoạt động", "Không hoạt động"});
        trangThaiComboBox.setFont(AppConstants.NORMAL_FONT);
        return trangThaiComboBox;
    }

    private void loadLoaiSanPham() {
        List<SanPhamDTO> loaiSanPhamList = sanPhamBUS.layDanhSachLoaiSanPham();
        for (SanPhamDTO loai : loaiSanPhamList) {
            loaiSanPhamComboBox.addItem(loai.getTenLoaiSanPham());
        }
    }

    private void applyFilter() {
        String tenLoaiSanPham = loaiSanPhamComboBox.getSelectedItem().toString();
        String minGiaVon = minGiaVonField.getText().trim();
        String maxGiaVon = maxGiaVonField.getText().trim();
        String minGiaLoi = minGiaLoiField.getText().trim();
        String maxGiaLoi = maxGiaLoiField.getText().trim();
        String minSoLuong = minSoLuongField.getText().trim();
        String maxSoLuong = maxSoLuongField.getText().trim();
        String trangThai = trangThaiComboBox.getSelectedItem().toString();

        sanPhamBUS.hienThiSanPhamLocNangCao(tbSanPham, tenLoaiSanPham, minGiaVon, maxGiaVon, minGiaLoi, maxGiaLoi, minSoLuong, maxSoLuong, trangThai);
        dispose();
    }
}