package org.example.GUI.Panels.NhanSu;

import org.example.BUS.NguoiDungBUS;
import org.example.DTO.NguoiDung;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DoiMatKhauDialog extends JDialog {
    private JPasswordField matKhauCuField;
    private JPasswordField matKhauMoiField;
    private JPasswordField xacNhanMatKhauField;
    private JCheckBox hienThiMatKhauCheckBox;
    private boolean isConfirmed = false;
    private final NguoiDungBUS nguoiDungBUS;
    private final NguoiDung nguoiDungHienTai;

    public DoiMatKhauDialog(Window owner) {
        super(owner, "Đổi mật khẩu");
        nguoiDungBUS = new NguoiDungBUS();
        nguoiDungHienTai = NguoiDungBUS.getNguoiDungHienTai();

        initComponents();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
        setModal(true);
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

        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setOpaque(false);

        JLabel userIconLabel = new JLabel();
        try {
            ImageIcon userIcon = new ImageIcon(getClass().getResource("/icons/user.png"));
            Image img = userIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            userIconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            userIconLabel.setText("👤");
        }

        JLabel userNameLabel = new JLabel(nguoiDungHienTai.getHoTen());
        userNameLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));

        userInfoPanel.add(userIconLabel);
        userInfoPanel.add(userNameLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(userInfoPanel, gbc);
        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 10, 5);
        formPanel.add(separator, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Mật khẩu hiện tại:", createMatKhauCuField(), gbc, 2);

        addFormField(formPanel, "Mật khẩu mới:", createMatKhauMoiField(), gbc, 3);

        addFormField(formPanel, "Xác nhận mật khẩu mới:", createXacNhanMatKhauField(), gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(createShowPasswordCheckbox(), gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 120, 35);
        StyledButton saveButton = new StyledButton("Đổi mật khẩu", AppConstants.PRIMARY_COLOR, 150, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> changePassword());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(400, 500));
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 2;
        panel.add(field, gbc);
    }

    private JPasswordField createMatKhauCuField() {
        matKhauCuField = new JPasswordField();
        matKhauCuField.setFont(AppConstants.NORMAL_FONT);
        matKhauCuField.setPreferredSize(new Dimension(300, 30));
        return matKhauCuField;
    }

    private JPasswordField createMatKhauMoiField() {
        matKhauMoiField = new JPasswordField();
        matKhauMoiField.setFont(AppConstants.NORMAL_FONT);
        matKhauMoiField.setPreferredSize(new Dimension(300, 30));
        return matKhauMoiField;
    }

    private JPasswordField createXacNhanMatKhauField() {
        xacNhanMatKhauField = new JPasswordField();
        xacNhanMatKhauField.setFont(AppConstants.NORMAL_FONT);
        xacNhanMatKhauField.setPreferredSize(new Dimension(300, 30));
        return xacNhanMatKhauField;
    }

    private JCheckBox createShowPasswordCheckbox() {
        hienThiMatKhauCheckBox = new JCheckBox("Hiển thị mật khẩu");
        hienThiMatKhauCheckBox.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 12));
        hienThiMatKhauCheckBox.addActionListener(e -> {
            boolean show = hienThiMatKhauCheckBox.isSelected();
            matKhauCuField.setEchoChar(show ? (char) 0 : '•');
            matKhauMoiField.setEchoChar(show ? (char) 0 : '•');
            xacNhanMatKhauField.setEchoChar(show ? (char) 0 : '•');
        });
        return hienThiMatKhauCheckBox;
    }

    private void changePassword() {
        String matKhauCu = new String(matKhauCuField.getPassword());
        String matKhauMoi = new String(matKhauMoiField.getPassword());
        String xacNhanMatKhau = new String(xacNhanMatKhauField.getPassword());

        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || xacNhanMatKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng điền đầy đủ thông tin",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!matKhauMoi.equals(xacNhanMatKhau)) {
            JOptionPane.showMessageDialog(this,
                    "Mật khẩu mới và xác nhận mật khẩu không khớp",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ketQua = nguoiDungBUS.datLaiMatKhau(matKhauCu, matKhauMoi, xacNhanMatKhau);

        if (ketQua.equals("Đổi mật khẩu thành công!")) {
            JOptionPane.showMessageDialog(this,
                    ketQua,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            isConfirmed = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    ketQua,
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
