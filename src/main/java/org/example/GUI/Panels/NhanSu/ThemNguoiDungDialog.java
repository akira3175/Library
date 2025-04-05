package org.example.GUI.Panels.NhanSu;

import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ThemNguoiDungDialog extends JDialog {
    private JTextField maNguoiDungField;
    private JTextField tenDangNhapField;
    private JTextField hoTenField;
    private JFormattedTextField ngaySinhField;
    private JComboBox<String> gioiTinhComboBox;
    private JTextField emailField;
    private JTextField soDienThoaiField;
    private JTextArea diaChiArea;
    private JFormattedTextField ngayVaoLamField;
    private String generatedPassword;
    private boolean isConfirmed = false;

    public ThemNguoiDungDialog(Window owner) {
        super(owner, "Thêm người dùng mới");
        initComponents();
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

        // Button panel
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
        setMinimumSize(new Dimension(500, 600));
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

    private JTextField createMaNguoiDungField() {
        maNguoiDungField = new JTextField();
        maNguoiDungField.setEditable(false);
        maNguoiDungField.setText(generateNextUserId());
        maNguoiDungField.setFont(AppConstants.NORMAL_FONT);
        return maNguoiDungField;
    }

    private JTextField createTenDangNhapField() {
        tenDangNhapField = new JTextField();
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

            // Set current date as default
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            ngayVaoLamField.setText(sdf.format(new Date()));

            panel.add(ngayVaoLamField, BorderLayout.CENTER);

            // Add button to set current date
            StyledButton currentDateButton = new StyledButton("Hôm nay", AppConstants.BLUE, 120, 30);
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

    private String generateNextUserId() {
        // In a real application, this would query the database for the last ID
        // and generate the next one. For this example, we'll use a placeholder.
        return "NV" + String.format("%03d", 11); // Assuming we have 10 users already
    }

    private String generateRandomPassword() {
        // Generate a random password with 8 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private void saveUser() {
        // Validate input fields
        if (tenDangNhapField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hoTenField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // In a real application, you would validate all fields and save to database

        // Generate a random password
        generatedPassword = generateRandomPassword();

        // Show success message with username and password
        JOptionPane.showMessageDialog(
                this,
                "Thêm thành công!\nTên đăng nhập: " + tenDangNhapField.getText() + "\nMật khẩu: " + generatedPassword,
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE
        );

        isConfirmed = true;
        dispose();
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

    public String getGeneratedPassword() {
        return generatedPassword;
    }
}
