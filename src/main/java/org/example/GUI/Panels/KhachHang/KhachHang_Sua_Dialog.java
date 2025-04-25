package org.example.GUI.Panels.KhachHang;

import org.example.BUS.KhachHangBUS;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KhachHang_Sua_Dialog extends JDialog {

    private JTextField maKhachHangField;
    private JTextField hoTenField;
    private JTextField soDienThoaiField;
    private JTextField diaChiField;
    private JCheckBox trangThaiCheckBox;
    private KhachHangBUS khachHangBUS;
    private JTable tbKhachHang;
    private KhachHangDTO khachHang;

    public KhachHang_Sua_Dialog(Window owner, boolean modal, KhachHangBUS khachHangBUS, JTable tbKhachHang, KhachHangDTO khachHang) {
        super(owner, "Thông tin khách hàng");
        this.khachHangBUS = khachHangBUS;
        this.tbKhachHang = tbKhachHang;
        this.khachHang = khachHang;
        initComponents();
        hienThiThongTin();
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

        addFormField(formPanel, "Mã khách hàng:", taoMaKhachHangField(), gbc, 0);
        addFormField(formPanel, "Họ tên:", taoHoTenField(), gbc, 1);
        addFormField(formPanel, "Số điện thoại:", taoSoDienThoaiField(), gbc, 2);
        addFormField(formPanel, "Địa chỉ:", taoDiaChiField(), gbc, 3);
        addFormField(formPanel, "Trạng thái:", taoTrangThaiCheckBox(), gbc, 4);

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

    private JTextField taoMaKhachHangField() {
        maKhachHangField = new JTextField();
        maKhachHangField.setFont(AppConstants.NORMAL_FONT);
        maKhachHangField.setEditable(false);
        return maKhachHangField;
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

    private JCheckBox taoTrangThaiCheckBox() {
        trangThaiCheckBox = new JCheckBox("Hoạt động");
        trangThaiCheckBox.setFont(AppConstants.NORMAL_FONT);
        return trangThaiCheckBox;
    }

    private void hienThiThongTin() {
        maKhachHangField.setText(String.valueOf(khachHang.getMaKhachHang()));
        hoTenField.setText(khachHang.getHoTen());
        soDienThoaiField.setText(khachHang.getSoDienThoai());
        diaChiField.setText(khachHang.getDiaChi());
        trangThaiCheckBox.setSelected(khachHang.getTrangThai());
    }

    private void luuKhachHang() {
        try {
            if (hoTenField.getText().trim().isEmpty() ||
                soDienThoaiField.getText().trim().isEmpty() ||
                diaChiField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHangDTO updatedKhachHang = new KhachHangDTO(khachHang);
            updatedKhachHang.setHoTen(hoTenField.getText().trim());
            updatedKhachHang.setSoDienThoai(soDienThoaiField.getText().trim());
            updatedKhachHang.setDiaChi(diaChiField.getText().trim());
            updatedKhachHang.setTrangThai(trangThaiCheckBox.isSelected());

            if (khachHangBUS.suaKhachHang(updatedKhachHang)) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                khachHangBUS.hienThiKhachHangLenTable(tbKhachHang, "Tất cả");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}