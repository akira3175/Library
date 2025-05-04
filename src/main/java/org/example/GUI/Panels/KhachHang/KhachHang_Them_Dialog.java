package org.example.GUI.Panels.KhachHang;

import org.example.BUS.KhachHangBUS;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KhachHang_Them_Dialog extends JDialog {

    private JTextField hoTenField;
    private JTextField soDienThoaiField;
    private JTextField diaChiField;
    private KhachHangBUS khachHangBUS;
    private JTable tbKhachHang;

    public KhachHang_Them_Dialog(Window owner, boolean modal, KhachHangBUS khachHangBUS, JTable tbKhachHang) {
        super(owner, "Thêm khách hàng mới");
        this.khachHangBUS = khachHangBUS;
        this.tbKhachHang = tbKhachHang;
        initComponents();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Họ tên:", taoHoTenField(), gbc, 0);
        addFormField(formPanel, "Số điện thoại:", taoSoDienThoaiField(), gbc, 1);
        addFormField(formPanel, "Địa chỉ:", taoDiaChiField(), gbc, 2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);
        StyledButton saveButton = new StyledButton("Lưu", AppConstants.PRIMARY_COLOR, 100, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> luuKhachHang());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(400, 300));
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

    private JTextField taoHoTenField() {
        hoTenField = new JTextField();
        hoTenField.setFont(AppConstants.NORMAL_FONT);
        return hoTenField;
    }

    private JTextField taoSoDienThoaiField() {
        soDienThoaiField = new JTextField();
        soDienThoaiField.setFont(AppConstants.NORMAL_FONT);
        return soDienThoaiField;
    }

    private JTextField taoDiaChiField() {
        diaChiField = new JTextField();
        diaChiField.setFont(AppConstants.NORMAL_FONT);
        return diaChiField;
    }

    private void luuKhachHang() {
        try {
            StringBuilder errorMessage = new StringBuilder();

            if (hoTenField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập họ tên!\n");
            }
            if (soDienThoaiField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập số điện thoại!\n");
            } else if (!soDienThoaiField.getText().trim().matches("\\d{10,11}")) {
                errorMessage.append("- Số điện thoại phải là 10 hoặc 11 chữ số!\n");
            }
            if (diaChiField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập địa chỉ!\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng hoàn thành các thông tin sau:\n" + errorMessage.toString(),
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHangDTO khachHang = new KhachHangDTO();
            int maKhachHang = khachHangBUS.layMaKhachHangTiepTheo();
            if (maKhachHang == -1) {
                JOptionPane.showMessageDialog(this, "Không thể tạo mã khách hàng mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            khachHang.setMaKhachHang(maKhachHang);
            khachHang.setHoTen(hoTenField.getText().trim());
            khachHang.setSoDienThoai(soDienThoaiField.getText().trim());
            khachHang.setDiaChi(diaChiField.getText().trim());
            khachHang.setTrangThai(true);
            
            if (khachHangBUS.themKhachHang(khachHang)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                khachHangBUS.hienThiKhachHangLenTable(tbKhachHang, "Tất cả");
                tbKhachHang.repaint();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("Số điện thoại đã tồn tại")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}