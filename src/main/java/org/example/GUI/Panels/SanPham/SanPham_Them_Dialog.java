package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class SanPham_Them_Dialog extends JDialog {

    private JTextField maSanPhamField;
    private JComboBox<String> loaiSanPhamComboBox;
    private JLabel anhSanPhamLabel;
    private JTextField tenSanPhamField;
    private JTextField nhaSanXuatField;
    private JTextField soLuongField;
    private JTextField giaVonField;
    private JTextField giaLoiField;
    private SanPhamBUS sanPhamBUS;
    private JTable tbSanPham;
    private SanPhamDAO sanPhamDAO;
    private String duongDanAnh;

    public SanPham_Them_Dialog(Window owner, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham) {
        super(owner, "Thêm sản phẩm mới");
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
        this.sanPhamDAO = new SanPhamDAO();
        initComponents();
        taiDuLieuLoaiSanPham();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel anhPanel = new JPanel();
        anhPanel.setLayout(new BoxLayout(anhPanel, BoxLayout.Y_AXIS));
        anhPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        anhPanel.setPreferredSize(new Dimension(240, Integer.MAX_VALUE));

        anhSanPhamLabel = new JLabel("Chưa chọn ảnh", SwingConstants.CENTER);
        anhSanPhamLabel.setFont(AppConstants.NORMAL_FONT);
        anhSanPhamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        anhSanPhamLabel.setVerticalAlignment(SwingConstants.CENTER);
        anhSanPhamLabel.setPreferredSize(new Dimension(240, 240));
        anhSanPhamLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        anhSanPhamLabel.setMinimumSize(new Dimension(240, 240));
        anhSanPhamLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        anhSanPhamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        StyledButton chonAnhButton = new StyledButton("Chọn", AppConstants.BLUE, 0, 30);
        chonAnhButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        chonAnhButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        chonAnhButton.addActionListener(e -> chonAnh());

        anhPanel.add(anhSanPhamLabel);
        anhPanel.add(Box.createVerticalStrut(5));
        anhPanel.add(chonAnhButton);
        anhPanel.add(Box.createVerticalGlue());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Mã sản phẩm:", taoMaSanPhamField(), gbc, 0);
        addLoaiSanPhamField(formPanel, gbc, 1);
        addFormField(formPanel, "Tên sản phẩm:", taoTenSanPhamField(), gbc, 2);
        addFormField(formPanel, "Nhà sản xuất:", taoNhaSanXuatField(), gbc, 3);
        addFormField(formPanel, "Số lượng:", taoSoLuongField(), gbc, 4);
        addFormField(formPanel, "Giá vốn:", taoGiaVonField(), gbc, 5);
        addFormField(formPanel, "Giá lời:", taoGiaLoiField(), gbc, 6);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);
        StyledButton saveButton = new StyledButton("Lưu", AppConstants.PRIMARY_COLOR, 100, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> luuSanPham());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(anhPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(600, 400));
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

    private JTextField taoMaSanPhamField() {
        maSanPhamField = new JTextField();
        maSanPhamField.setFont(AppConstants.NORMAL_FONT);
        return maSanPhamField;
    }

    private void addLoaiSanPhamField(JPanel panel, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel("Loại sản phẩm:");
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        JPanel loaiPanel = new JPanel(new BorderLayout());
        loaiSanPhamComboBox = new JComboBox<>();
        loaiSanPhamComboBox.setFont(AppConstants.NORMAL_FONT);

        StyledButton themLoaiButton = new StyledButton("+", AppConstants.BLUE, 30, 30);
        themLoaiButton.setFont(new Font("Arial",Font.BOLD,24));
        themLoaiButton.setHorizontalAlignment(SwingConstants.CENTER);
        themLoaiButton.setVerticalAlignment(SwingConstants.CENTER);
        themLoaiButton.addActionListener(e -> moLoaiSanPhamDialog());

        loaiPanel.add(loaiSanPhamComboBox, BorderLayout.CENTER);
        loaiPanel.add(themLoaiButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(loaiPanel, gbc);
    }

    private JTextField taoTenSanPhamField() {
        tenSanPhamField = new JTextField();
        tenSanPhamField.setFont(AppConstants.NORMAL_FONT);
        return tenSanPhamField;
    }

    private JTextField taoNhaSanXuatField() {
        nhaSanXuatField = new JTextField();
        nhaSanXuatField.setFont(AppConstants.NORMAL_FONT);
        return nhaSanXuatField;
    }

    private JTextField taoSoLuongField() {
        soLuongField = new JTextField();
        soLuongField.setFont(AppConstants.NORMAL_FONT);
        soLuongField.setText("0");
        soLuongField.setEnabled(false);
        soLuongField.setEditable(false);
        return soLuongField;
    }

    private JTextField taoGiaVonField() {
        giaVonField = new JTextField();
        giaVonField.setFont(AppConstants.NORMAL_FONT);
        return giaVonField;
    }

    private JTextField taoGiaLoiField() {
        giaLoiField = new JTextField();
        giaLoiField.setFont(AppConstants.NORMAL_FONT);
        return giaLoiField;
    }

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            duongDanAnh = selectedFile.getAbsolutePath();

            ImageIcon originalIcon = new ImageIcon(duongDanAnh);
            Image img = originalIcon.getImage();

            int maxWidth = 240;
            int maxHeight = 240;
            int originalWidth = originalIcon.getIconWidth();
            int originalHeight = originalIcon.getIconHeight();

            int newWidth, newHeight;
            if ((float) originalWidth / originalHeight > 1f) {
                newWidth = maxWidth;
                newHeight = (int) (maxWidth * originalHeight / originalWidth);
            } else {
                newHeight = maxHeight;
                newWidth = (int) (maxHeight * originalWidth / originalHeight);
            }

            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            anhSanPhamLabel.setText("");
            anhSanPhamLabel.setIcon(new ImageIcon(scaledImg));
        }
    }

    private void moLoaiSanPhamDialog() {
        SanPham_Loai_Dialog loaiDialog = new SanPham_Loai_Dialog(this, true, this);
        loaiDialog.setVisible(true);
    }

    private void taiDuLieuLoaiSanPham() {
        List<SanPhamDTO> danhSachLoaiSanPham = sanPhamDAO.layDanhSachLoaiSanPham();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (SanPhamDTO loai : danhSachLoaiSanPham) {
            String tenLoai = loai.getTenLoaiSanPham();
            if (tenLoai != null) {
                model.addElement(tenLoai);
            }
        }
        loaiSanPhamComboBox.setModel(model);
    }

    private int layMaLoaiSanPhamTuTen(String tenLoaiSanPham) {
        List<SanPhamDTO> danhSachLoai = sanPhamDAO.layDanhSachLoaiSanPham();
        for (SanPhamDTO loai : danhSachLoai) {
            if (loai.getTenLoaiSanPham().equals(tenLoaiSanPham)) {
                return loai.getMaLoaiSanPham();
            }
        }
        return -1;
    }

    private void luuSanPham() {
        try {
            if (maSanPhamField.getText().trim().isEmpty()
                    || soLuongField.getText().trim().isEmpty()
                    || giaVonField.getText().trim().isEmpty()
                    || giaLoiField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SanPhamDTO sanPham = new SanPhamDTO();
            sanPham.setMaSanPham(Integer.parseInt(maSanPhamField.getText().trim()));
            int maLoaiSanPham = layMaLoaiSanPhamTuTen(loaiSanPhamComboBox.getSelectedItem().toString());
            if (maLoaiSanPham == -1) {
                JOptionPane.showMessageDialog(this, "Loại sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sanPham.setMaLoaiSanPham(maLoaiSanPham);
            sanPham.setAnhSanPhamURL(duongDanAnh != null ? duongDanAnh : "");
            sanPham.setTenSanPham(tenSanPhamField.getText().trim());
            sanPham.setNhaSanXuat(nhaSanXuatField.getText().trim());
            sanPham.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
            sanPham.setGiaVon(Integer.parseInt(giaVonField.getText().trim()));
            sanPham.setGiaLoi(Integer.parseInt(giaLoiField.getText().trim().toString()));

            if (sanPhamBUS.themSanPham(sanPham)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                sanPhamBUS.hienThiSanPhamLenTable(tbSanPham);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho các trường số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void capNhatComboBox() {
        taiDuLieuLoaiSanPham();
    }
}
