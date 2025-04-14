package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Thongtinsanpham_Dialog extends JDialog {

    private JTextField maSanPhamField;
    private JComboBox<String> loaiSanPhamComboBox;
    private JLabel anhSanPhamLabel;
    private JTextField tenSanPhamField;
    private JTextField nhaSanXuatField;
    private JTextField soLuongField;
    private JTextField giaVonField;
    private JTextField giaLoiField;

    private SanPhamBUS sanPhamBUS;
    private SanPhamDTO sanPham;
    private JTable tbSanPham;
    private SanPhamDAO sanPhamDAO;
    private String duongDanAnh;

    public Thongtinsanpham_Dialog(Window owner, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham, SanPhamDTO sanPham) {
        super(owner, "Thông tin sản phẩm");
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
        this.sanPham = sanPham;
        this.sanPhamDAO = new SanPhamDAO();
        this.duongDanAnh = sanPham.getAnhSanPhamURL();
        initComponents();
        hienThiDuLieuSanPham();
        hienThiDuLieuLoaiSanPham();
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
        anhSanPhamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        anhSanPhamLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        anhPanel.add(anhSanPhamLabel);
        anhPanel.add(Box.createVerticalGlue());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Mã sản phẩm:", taoMaSanPhamField(), gbc, 0);
        addFormField(formPanel, "Loại sản phẩm:", taoLoaiSanPhamComboBox(), gbc, 1);
        addFormField(formPanel, "Tên sản phẩm:", taoTenSanPhamField(), gbc, 2);
        addFormField(formPanel, "Nhà sản xuất:", taoNhaSanXuatField(), gbc, 3);
        addFormField(formPanel, "Số lượng:", taoSoLuongField(), gbc, 4);
        addFormField(formPanel, "Giá vốn:", taoGiaVonField(), gbc, 5);
        addFormField(formPanel, "Giá lời:", taoGiaLoiField(), gbc, 6);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton closeButton = new StyledButton("Đóng", new Color(107, 114, 128), 100, 35);
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);
        

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
        maSanPhamField.setEditable(false);
        return maSanPhamField;
    }

    private JComboBox<String> taoLoaiSanPhamComboBox() {
        loaiSanPhamComboBox = new JComboBox<>();
        loaiSanPhamComboBox.setFont(AppConstants.NORMAL_FONT);
        loaiSanPhamComboBox.setEnabled(false);
        return loaiSanPhamComboBox;
    }

    private JTextField taoTenSanPhamField() {
        tenSanPhamField = new JTextField();
        tenSanPhamField.setFont(AppConstants.NORMAL_FONT);
        tenSanPhamField.setEditable(false);
        return tenSanPhamField;
    }

    private JTextField taoNhaSanXuatField() {
        nhaSanXuatField = new JTextField();
        nhaSanXuatField.setFont(AppConstants.NORMAL_FONT);
        nhaSanXuatField.setEditable(false);
        return nhaSanXuatField;
    }

    private JTextField taoSoLuongField() {
        soLuongField = new JTextField();
        soLuongField.setFont(AppConstants.NORMAL_FONT);
        soLuongField.setEditable(false);
        return soLuongField;
    }

    private JTextField taoGiaVonField() {
        giaVonField = new JTextField();
        giaVonField.setFont(AppConstants.NORMAL_FONT);
        giaVonField.setEditable(false);
        return giaVonField;
    }

    private JTextField taoGiaLoiField() {
        giaLoiField = new JTextField();
        giaLoiField.setFont(AppConstants.NORMAL_FONT);
        giaLoiField.setEditable(false);
        return giaLoiField;
    }
    
    private void hienThiDuLieuLoaiSanPham(){
        List<SanPhamDTO> danhSachLoaiSanPham = sanPhamDAO.layDanhSachLoaiSanPham();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for(SanPhamDTO loai : danhSachLoaiSanPham){
            model.addElement(loai.getTenLoaiSanPham());
        }
        loaiSanPhamComboBox.setModel(model);
    }

    private void hienThiDuLieuSanPham() {
        if (sanPham == null) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu sản phẩm để hiển thị!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        maSanPhamField.setText(String.valueOf(sanPham.getMaSanPham()));
        loaiSanPhamComboBox.setSelectedItem(sanPham.getTenLoaiSanPham());
        tenSanPhamField.setText(sanPham.getTenSanPham());
        nhaSanXuatField.setText(sanPham.getNhaSanXuat());
        soLuongField.setText(String.valueOf(sanPham.getSoLuong()));
        giaVonField.setText(String.valueOf(sanPham.getGiaVon()));
        giaLoiField.setText(String.valueOf(sanPham.getGiaLoi()));

        if (sanPham.getAnhSanPhamURL() != null && !sanPham.getAnhSanPhamURL().isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(sanPham.getAnhSanPhamURL());
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
            } catch (Exception e) {
                anhSanPhamLabel.setText("Lỗi tải ảnh");
                anhSanPhamLabel.setIcon(null);
            }
        } else {
            anhSanPhamLabel.setText("Chưa chọn ảnh");
            anhSanPhamLabel.setIcon(null);
        }
    }
}
