package org.example.GUI.Panels.NhanSu;

import org.example.BUS.NguoiDungBUS;
import org.example.BUS.VaiTroBUS;
import org.example.DTO.NguoiDung;
import org.example.DTO.VaiTro;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.example.GUI.Utils.DateUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChiTietNguoiDungDialog extends JDialog {
    private JTextField maNguoiDungField;
    private JTextField tenDangNhapField;
    private JTextField hoTenField;
    private JFormattedTextField ngaySinhField;
    private JComboBox<String> gioiTinhComboBox;
    private JComboBox<VaiTro> vaiTroJComboBox;
    private JTextField emailField;
    private JTextField soDienThoaiField;
    private JTextArea diaChiArea;
    private JFormattedTextField ngayVaoLamField;
    private boolean isActive;
    private boolean isConfirmed = false;
    private String newPassword = null;
    private StyledButton toggleActiveButton;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private NguoiDung nguoiDung;
    private NguoiDungBUS nguoiDungBUS;
    private VaiTroBUS vaiTroBUS;

    public ChiTietNguoiDungDialog(Window owner, NguoiDung nguoiDung) {
        super(owner, "Chi tiết người dùng");
        this.isActive = nguoiDung.isConHoatDong();
        nguoiDungBUS = new NguoiDungBUS();
        vaiTroBUS = new VaiTroBUS();
        initComponents();

        maNguoiDungField.setText(nguoiDung.getMaNguoiDung() + "");
        tenDangNhapField.setText(nguoiDung.getTenDangNhap());
        hoTenField.setText(nguoiDung.getHoTen());
        emailField.setText(nguoiDung.getEmail());
        soDienThoaiField.setText(nguoiDung.getSoDienThoai());
        ngaySinhField.setText(sdf.format(nguoiDung.getNgaySinh()));
        gioiTinhComboBox.setSelectedItem(nguoiDung.getGioiTinh());
        diaChiArea.setText(nguoiDung.getDiaChi());
        ngayVaoLamField.setText(sdf.format(nguoiDung.getNgayVaoLam()));

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

        // Mã người dùng
        addFormField(formPanel, "Mã người dùng:", createMaNguoiDungField(), gbc, 0);

        // Tên đăng nhập
        addFormField(formPanel, "Tên đăng nhập:", createTenDangNhapField(), gbc, 1);

        // Họ tên
        addFormField(formPanel, "Họ tên:", createHoTenField(), gbc, 2);

        // Ngày sinh
        addFormField(formPanel, "Ngày sinh:", createNgaySinhField(), gbc, 3);

        // Giới tính
        addFormField(formPanel, "Giới tính:", createGioiTinhComboBox(), gbc, 4);

        // Email
        addFormField(formPanel, "Email:", createEmailField(), gbc, 5);

        // Số điện thoại
        addFormField(formPanel, "Số điện thoại:", createSoDienThoaiField(), gbc, 6);

        // Địa chỉ
        addFormField(formPanel, "Địa chỉ:", createDiaChiField(), gbc, 7);

        // Ngày vào làm
        addFormField(formPanel, "Ngày vào làm:", createNgayVaoLamField(), gbc, 8);

        addFormField(formPanel, "Vai Trò: ", createVaiTroComboBox(), gbc, 9);

        // Additional action buttons
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionButtonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Reset password button
        StyledButton resetPasswordButton = new StyledButton("Đặt lại mật khẩu", new Color(59, 130, 246), 200, 35);
        resetPasswordButton.addActionListener(e -> resetPassword());

        // Toggle active status button
        toggleActiveButton = new StyledButton(
                isActive ? "Thôi việc" : "Kích hoạt lại",
                isActive ? new Color(239, 68, 68) : new Color(34, 197, 94),
                200, 35
        );
        toggleActiveButton.addActionListener(e -> toggleActiveStatus());

        actionButtonsPanel.add(resetPasswordButton);
        actionButtonsPanel.add(Box.createHorizontalStrut(10));
        actionButtonsPanel.add(toggleActiveButton);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(actionButtonsPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);
        StyledButton saveButton = new StyledButton("Lưu", AppConstants.PRIMARY_COLOR, 100, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveUser());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(500, 650));
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private JTextField createMaNguoiDungField() {
        maNguoiDungField = new JTextField();
        maNguoiDungField.setEditable(false);
        maNguoiDungField.setFont(AppConstants.NORMAL_FONT);
        return maNguoiDungField;
    }

    private JTextField createTenDangNhapField() {
        tenDangNhapField = new JTextField();
        tenDangNhapField.setEditable(false); // Username cannot be changed
        tenDangNhapField.setFont(AppConstants.NORMAL_FONT);
        return tenDangNhapField;
    }

    private JTextField createHoTenField() {
        hoTenField = new JTextField();
        hoTenField.setFont(AppConstants.NORMAL_FONT);
        return hoTenField;
    }

    private JComponent createNgaySinhField() {
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            ngaySinhField = new JFormattedTextField(dateFormatter);
            ngaySinhField.setFont(AppConstants.NORMAL_FONT);
            return ngaySinhField;
        } catch (ParseException e) {
            ngaySinhField = new JFormattedTextField();
            ngaySinhField.setFont(AppConstants.NORMAL_FONT);
            return ngaySinhField;
        }
    }

    private JComponent createGioiTinhComboBox() {
        gioiTinhComboBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        gioiTinhComboBox.setFont(AppConstants.NORMAL_FONT);
        return gioiTinhComboBox;
    }

    private JComponent createVaiTroComboBox() {
        vaiTroJComboBox = new JComboBox<>();
        List<VaiTro> danhSachVaiTro = vaiTroBUS.danhSachVaitro();
        for (VaiTro vaiTro: danhSachVaiTro) {
            vaiTroJComboBox.addItem(vaiTro);
        }
        vaiTroJComboBox.setFont(AppConstants.NORMAL_FONT);

        return vaiTroJComboBox;
    }

    private JTextField createEmailField() {
        emailField = new JTextField();
        emailField.setFont(AppConstants.NORMAL_FONT);
        return emailField;
    }

    private JTextField createSoDienThoaiField() {
        soDienThoaiField = new JTextField();
        soDienThoaiField.setFont(AppConstants.NORMAL_FONT);
        return soDienThoaiField;
    }

    private JComponent createDiaChiField() {
        diaChiArea = new JTextArea(3, 20);
        diaChiArea.setFont(AppConstants.NORMAL_FONT);
        diaChiArea.setLineWrap(true);
        diaChiArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(diaChiArea);
        return scrollPane;
    }

    private JComponent createNgayVaoLamField() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            ngayVaoLamField = new JFormattedTextField(dateFormatter);
            ngayVaoLamField.setFont(AppConstants.NORMAL_FONT);

            panel.add(ngayVaoLamField, BorderLayout.CENTER);

            StyledButton currentDateButton = new StyledButton("Hôm nay", new Color(59, 130, 246), 120, 30);
            currentDateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    ngayVaoLamField.setText(sdf.format(new Date()));
                }
            });

            panel.add(currentDateButton, BorderLayout.EAST);

            return panel;
        } catch (ParseException e) {
            ngayVaoLamField = new JFormattedTextField();
            ngayVaoLamField.setFont(AppConstants.NORMAL_FONT);
            panel.add(ngayVaoLamField, BorderLayout.CENTER);
            return panel;
        }
    }

    private String generateRandomPassword() {
        return "0000";
    }

    private void resetPassword() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đặt lại mật khẩu cho người dùng này?",
                "Xác nhận đặt lại mật khẩu",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            newPassword = generateRandomPassword();

            JOptionPane.showMessageDialog(
                    this,
                    "Mật khẩu mới cho người dùng " + tenDangNhapField.getText() + " là: " + newPassword,
                    "Đặt lại mật khẩu thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void toggleActiveStatus() {
        isActive = !isActive;

        String message = isActive
                ? "Bạn có chắc chắn muốn kích hoạt lại người dùng này?"
                : "Bạn có chắc chắn muốn đặt trạng thái thôi việc cho người dùng này?";

        String title = isActive ? "Xác nhận kích hoạt lại" : "Xác nhận thôi việc";

        int confirm = JOptionPane.showConfirmDialog(
                this,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.NO_OPTION) {
            isActive = !isActive;
        } else {
            toggleActiveButton.setText(isActive ? "Thôi việc" : "Kích hoạt lại");
            toggleActiveButton.setButtonBackgroundColor(isActive ? new Color(239, 68, 68) : new Color(34, 197, 94));
        }
    }

    private void saveUser() {
        nguoiDung = nguoiDungBUS.suaNguoiDung(capNhatNguoiDung());

        if (nguoiDung == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cập nhật thông tin người dùng thất bại!",
                    "Thất bại",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        JOptionPane.showMessageDialog(
                this,
                "Cập nhật thông tin người dùng thành công!",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE
        );

        isConfirmed = true;
        dispose();
    }

    private NguoiDung capNhatNguoiDung() {
        // Validate input fields
        if (hoTenField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Date ngaySinh = DateUtils.parseDate(ngaySinhField.getText());
        Date ngayVaoLam = DateUtils.parseDate(ngayVaoLamField.getText());

        if (ngaySinh == null || ngayVaoLam == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh hoặc ngày vào làm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        VaiTro selectedVaiTro = (VaiTro) vaiTroJComboBox.getSelectedItem();

        nguoiDung = nguoiDungBUS.layNguoiDungTheoID(Integer.parseInt(maNguoiDungField.getText()));
        nguoiDung.setHoTen(hoTenField.getText());
        nguoiDung.setMatKhau(nguoiDung.getMatKhau());
        nguoiDung.setSoDienThoai(soDienThoaiField.getText());
        nguoiDung.setNgaySinh(ngaySinh);
        nguoiDung.setGioiTinh((String) gioiTinhComboBox.getSelectedItem());
        nguoiDung.setEmail(emailField.getText());
        nguoiDung.setDiaChi(diaChiArea.getText());
        nguoiDung.setNgayVaoLam(ngayVaoLam);
        nguoiDung.setMaVaiTro(selectedVaiTro.getMaVaiTro());

        return nguoiDung;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getMaNguoiDung() {
        return maNguoiDungField.getText();
    }

    public String getTenDangNhap() {
        return tenDangNhapField.getText();
    }

    public String getHoTen() {
        return hoTenField.getText();
    }

    public String getNgaySinh() {
        return ngaySinhField.getText();
    }

    public String getGioiTinh() {
        return (String) gioiTinhComboBox.getSelectedItem();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getSoDienThoai() {
        return soDienThoaiField.getText();
    }

    public String getDiaChi() {
        return diaChiArea.getText();
    }

    public String getNgayVaoLam() {
        return ngayVaoLamField.getText();
    }

    public boolean isActive() {
        return isActive;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

