package org.example.GUI.Panels.KhuyenMai;

import org.example.BUS.KhuyenMaiBUS;
import org.example.DTO.KhuyenMai;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.example.GUI.Utils.DateUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KhuyenMaiDialog extends JDialog {
    private JTextField tenKhuyenMaiField;
    private JFormattedTextField soTienKhuyenMaiField;
    private JFormattedTextField dieuKienHoaDonField;
    private JFormattedTextField ngayBatDauField;
    private JFormattedTextField ngayKetThucField;
    private JCheckBox trangThaiCheckBox;
    private boolean isConfirmed = false;
    private KhuyenMaiBUS khuyenMaiBUS;
    private KhuyenMai khuyenMai;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public KhuyenMaiDialog(Window owner, KhuyenMai khuyenMai) {
        super(owner, khuyenMai == null ? "Thêm khuyến mãi mới" : "Chỉnh sửa khuyến mãi", ModalityType.APPLICATION_MODAL);
        this.khuyenMaiBUS = new KhuyenMaiBUS();
        this.khuyenMai = khuyenMai;

        initComponents();

        if (khuyenMai != null) {
            populateFields();
        }

        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tên khuyến mãi
        addFormField(formPanel, "Tên khuyến mãi:", createTenKhuyenMaiField(), gbc, 0);

        // Số tiền khuyến mãi
        addFormField(formPanel, "Số tiền khuyến mãi (VNĐ):", createSoTienKhuyenMaiField(), gbc, 1);

        // Điều kiện hóa đơn
        addFormField(formPanel, "Điều kiện hóa đơn (VNĐ):", createDieuKienHoaDonField(), gbc, 2);

        // Ngày bắt đầu
        addFormField(formPanel, "Ngày bắt đầu:", createNgayBatDauField(), gbc, 3);

        // Ngày kết thúc
        addFormField(formPanel, "Ngày kết thúc:", createNgayKetThucField(), gbc, 4);

        // Trạng thái
        addFormField(formPanel, "Trạng thái:", createTrangThaiCheckBox(), gbc, 5);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);
        StyledButton saveButton = new StyledButton("Lưu", AppConstants.PRIMARY_COLOR, 100, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveKhuyenMai());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(450, 450));
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        gbc.gridx = 0;
        gbc.gridy = row * 2;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = row * 2 + 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private JTextField createTenKhuyenMaiField() {
        tenKhuyenMaiField = new JTextField();
        tenKhuyenMaiField.setFont(AppConstants.NORMAL_FONT);
        return tenKhuyenMaiField;
    }

    private JFormattedTextField createSoTienKhuyenMaiField() {
        NumberFormat format = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        format.setGroupingUsed(true);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setAllowsInvalid(false);

        soTienKhuyenMaiField = new JFormattedTextField(formatter);
        soTienKhuyenMaiField.setFont(AppConstants.NORMAL_FONT);
        soTienKhuyenMaiField.setValue(0);

        return soTienKhuyenMaiField;
    }

    private JFormattedTextField createDieuKienHoaDonField() {
        NumberFormat format = NumberFormat.getIntegerInstance(new Locale("vi", "VN"));
        format.setGroupingUsed(true);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setAllowsInvalid(false);

        dieuKienHoaDonField = new JFormattedTextField(formatter);
        dieuKienHoaDonField.setFont(AppConstants.NORMAL_FONT);
        dieuKienHoaDonField.setValue(0);

        return dieuKienHoaDonField;
    }

    private JComponent createNgayBatDauField() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            ngayBatDauField = new JFormattedTextField(dateFormatter);
            ngayBatDauField.setFont(AppConstants.NORMAL_FONT);

            // Set current date as default
            ngayBatDauField.setText(dateFormat.format(new Date()));

            panel.add(ngayBatDauField, BorderLayout.CENTER);

            // Add button to set current date
            StyledButton currentDateButton = new StyledButton("Hôm nay", new Color(59, 130, 246), 80, 30);
            currentDateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ngayBatDauField.setText(dateFormat.format(new Date()));
                }
            });

            panel.add(currentDateButton, BorderLayout.EAST);

            return panel;
        } catch (ParseException e) {
            ngayBatDauField = new JFormattedTextField();
            ngayBatDauField.setFont(AppConstants.NORMAL_FONT);
            panel.add(ngayBatDauField, BorderLayout.CENTER);
            return panel;
        }
    }

    private JComponent createNgayKetThucField() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            ngayKetThucField = new JFormattedTextField(dateFormatter);
            ngayKetThucField.setFont(AppConstants.NORMAL_FONT);

            // Set default end date (current date + 30 days)
            Date endDate = new Date();
            endDate.setTime(endDate.getTime() + 30L * 24 * 60 * 60 * 1000);
            ngayKetThucField.setText(dateFormat.format(endDate));

            panel.add(ngayKetThucField, BorderLayout.CENTER);

            // Add button to set date to 30 days from now
            StyledButton defaultEndDateButton = new StyledButton("+30 ngày", new Color(59, 130, 246), 80, 30);
            defaultEndDateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Date endDate = new Date();
                    endDate.setTime(endDate.getTime() + 30L * 24 * 60 * 60 * 1000);
                    ngayKetThucField.setText(dateFormat.format(endDate));
                }
            });

            panel.add(defaultEndDateButton, BorderLayout.EAST);

            return panel;
        } catch (ParseException e) {
            ngayKetThucField = new JFormattedTextField();
            ngayKetThucField.setFont(AppConstants.NORMAL_FONT);
            panel.add(ngayKetThucField, BorderLayout.CENTER);
            return panel;
        }
    }

    private JCheckBox createTrangThaiCheckBox() {
        trangThaiCheckBox = new JCheckBox("Hoạt động");
        trangThaiCheckBox.setFont(AppConstants.NORMAL_FONT);
        trangThaiCheckBox.setSelected(true);
        return trangThaiCheckBox;
    }

    private void populateFields() {
        tenKhuyenMaiField.setText(khuyenMai.getTenKhuyenMai());
        soTienKhuyenMaiField.setValue(khuyenMai.getSoTienKhuyenMai());
        dieuKienHoaDonField.setValue(khuyenMai.getDieuKienHoaDon());
        ngayBatDauField.setText(dateFormat.format(khuyenMai.getNgayBatDau()));
        ngayKetThucField.setText(dateFormat.format(khuyenMai.getNgayKetThuc()));
        trangThaiCheckBox.setSelected(khuyenMai.getTrangThai());
    }

    private void saveKhuyenMai() {
        // Validate input fields
        if (tenKhuyenMaiField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khuyến mãi", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date ngayBatDau = DateUtils.parseDate(ngayBatDauField.getText());
        Date ngayKetThuc = DateUtils.parseDate(ngayKetThucField.getText());

        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu hoặc ngày kết thúc không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ngayBatDau.after(ngayKetThuc)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không thể sau ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int soTienKhuyenMai = ((Number) soTienKhuyenMaiField.getValue()).intValue();
        int dieuKienHoaDon = ((Number) dieuKienHoaDonField.getValue()).intValue();

        if (soTienKhuyenMai <= 0) {
            JOptionPane.showMessageDialog(this, "Số tiền khuyến mãi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (soTienKhuyenMai >= dieuKienHoaDon && dieuKienHoaDon > 0) {
            JOptionPane.showMessageDialog(this, "Số tiền khuyến mãi không thể lớn hơn hoặc bằng điều kiện hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create or update KhuyenMai object
        if (khuyenMai == null) {
            khuyenMai = new KhuyenMai();
        }

        khuyenMai.setTenKhuyenMai(tenKhuyenMaiField.getText().trim());
        khuyenMai.setSoTienKhuyenMai(soTienKhuyenMai);
        khuyenMai.setDieuKienHoaDon(dieuKienHoaDon);
        khuyenMai.setNgayBatDau(ngayBatDau);
        khuyenMai.setNgayKetThuc(ngayKetThuc);
        khuyenMai.setTrangThai(trangThaiCheckBox.isSelected());

        boolean success;
        if (khuyenMai.getMaKhuyenMai() == 0) {
            // Add new promotion
            KhuyenMai result = khuyenMaiBUS.themKhuyenMai(khuyenMai);
            success = (result != null);
        } else {
            // Update existing promotion
            success = khuyenMaiBUS.suaKhuyenMai(khuyenMai);
        }

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    khuyenMai.getMaKhuyenMai() == 0 ? "Thêm khuyến mãi thành công!" : "Cập nhật khuyến mãi thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
            isConfirmed = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    khuyenMai.getMaKhuyenMai() == 0 ? "Thêm khuyến mãi thất bại!" : "Cập nhật khuyến mãi thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
