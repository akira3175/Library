package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SanPham_Thongtinsanpham_Dialog extends JDialog {

    private JTextField maSanPhamField;
    private JComboBox<String> loaiSanPhamComboBox;
    private JLabel anhSanPhamLabel;
    private JTextField tenSanPhamField;
    private JTextField nhaSanXuatField;
    private JTextField soLuongField;
    private JTextField giaVonField;
    private JTextField giaLoiField;
    private JCheckBox trangThaiCheckBox;

    private SanPhamBUS sanPhamBUS;
    private SanPhamDTO sanPham;
    private JTable tbSanPham;
    private SanPhamDAO sanPhamDAO;
    private String duongDanAnh;

    private StyledButton suaButton;
    private StyledButton xoaButton;
    private StyledButton xacnhanButton;
    private StyledButton huyButton;
    private StyledButton thoatButton;
    private JPanel thanhCongCuPanel;

    private boolean isEdit = false;
    private SanPhamDTO originalSanPham;

    public SanPham_Thongtinsanpham_Dialog(Window owner, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham, SanPhamDTO sanPham) {
        super(owner, "Thông tin sản phẩm");
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
        this.sanPham = sanPham;
        this.sanPhamDAO = new SanPhamDAO();
        this.duongDanAnh = sanPham.getAnhSanPhamURL();
        this.originalSanPham = new SanPhamDTO(sanPham);
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
        addFormField(formPanel, "Loại sản phẩm:", taoLoaiSanPhamComboBox(), gbc, 1);
        addFormField(formPanel, "Tên sản phẩm:", taoTenSanPhamField(), gbc, 2);
        addFormField(formPanel, "Nhà sản xuất:", taoNhaSanXuatField(), gbc, 3);
        addFormField(formPanel, "Số lượng:", taoSoLuongField(), gbc, 4);
        addFormField(formPanel, "Giá vốn:", taoGiaVonField(), gbc, 5);
        addFormField(formPanel, "Giá lời:", taoGiaLoiField(), gbc, 6);
        addFormField(formPanel, "Trạng thái:", taoTrangThaiCheckBox(), gbc, 7);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        thanhCongCuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        suaButton = new StyledButton("Sửa", new Color(59, 130, 246), 100, 35);
        xoaButton = new StyledButton("Xóa", new Color(239, 68, 68), 100, 35);
        xacnhanButton = new StyledButton("Xác nhận", new Color(34, 197, 94), 100, 35);
        huyButton = new StyledButton("Hủy", new Color(107, 114, 128), 100, 35);

        suaButton.addActionListener(e -> enterEditMode());
        xoaButton.addActionListener(e -> xoaSanPham());
        xacnhanButton.addActionListener(e -> xacNhanSua());
        huyButton.addActionListener(e -> huySua());

        thanhCongCuPanel.add(suaButton);
        thanhCongCuPanel.add(xoaButton);
        thanhCongCuPanel.add(xacnhanButton);
        thanhCongCuPanel.add(huyButton);

        xacnhanButton.setVisible(false);
        huyButton.setVisible(false);

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        thoatButton = new StyledButton("Đóng", new Color(107, 114, 128), 100, 35);
        thoatButton.addActionListener(e -> dispose());
        closePanel.add(thoatButton);

        buttonPanel.add(thanhCongCuPanel, BorderLayout.WEST);
        buttonPanel.add(closePanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(anhPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(600, 420));
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

    private JCheckBox taoTrangThaiCheckBox() {
        trangThaiCheckBox = new JCheckBox("Hoạt động");
        trangThaiCheckBox.setFont(AppConstants.NORMAL_FONT);
        trangThaiCheckBox.setEnabled(false);
        return trangThaiCheckBox;
    }

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile != null && selectedFile.exists() && selectedFile.isFile()) {
                try {
                    String fileName = selectedFile.getName().toLowerCase();
                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
                        JOptionPane.showMessageDialog(this,
                                "Vui lòng chọn file ảnh định dạng JPG, JPEG hoặc PNG!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    duongDanAnh = selectedFile.getAbsolutePath();
                    ImageIcon originalIcon = new ImageIcon(duongDanAnh);

                    if (originalIcon.getImage() == null) {
                        JOptionPane.showMessageDialog(this,
                                "Không thể tải ảnh. File có thể bị hỏng!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

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
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi tải ảnh: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "File không tồn tại hoặc không hợp lệ!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiDuLieuLoaiSanPham() {
        List<SanPhamDTO> danhSachLoaiSanPham = sanPhamDAO.layDanhSachLoaiSanPham();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (SanPhamDTO loai : danhSachLoaiSanPham) {
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
        trangThaiCheckBox.setSelected(sanPham.getTrangThai());

        if (sanPham.getAnhSanPhamURL() != null && !sanPham.getAnhSanPhamURL().isEmpty()) {
            try {
                URL imageUrl = getClass().getResource(sanPham.getAnhSanPhamURL());
                ImageIcon originalIcon;
                if (imageUrl != null) {
                    originalIcon = new ImageIcon(imageUrl);
                } else {
                    String filePath = "src/main/resources" + sanPham.getAnhSanPhamURL();
                    File imageFile = new File(filePath);
                    if (imageFile.exists()) {
                        originalIcon = new ImageIcon(filePath);
                    } else {
                        anhSanPhamLabel.setText("Ảnh không tồn tại");
                        anhSanPhamLabel.setIcon(null);
                        return;
                    }
                }

                if (originalIcon.getImage() == null) {
                    anhSanPhamLabel.setText("Lỗi tải ảnh");
                    anhSanPhamLabel.setIcon(null);
                    return;
                }

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

    private void enterEditMode() {
        isEdit = true;

        loaiSanPhamComboBox.setEnabled(true);
        tenSanPhamField.setEditable(true);
        nhaSanXuatField.setEditable(true);
        soLuongField.setEditable(true);
        giaVonField.setEditable(true);
        giaLoiField.setEditable(true);
        trangThaiCheckBox.setEnabled(true);

        suaButton.setVisible(false);
        xoaButton.setVisible(false);

        xacnhanButton.setVisible(true);
        huyButton.setVisible(true);

        thanhCongCuPanel.revalidate();
        thanhCongCuPanel.repaint();
    }

    private String luuAnhVaoThuMucTaiNguyen(File selectedFile, int maSanPham) {
        try {
            String resourceDir = "src/main/resources/images/Sanpham_img";
            Path targetDir = Paths.get(resourceDir);

            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
            String newFileName = "sp_" + maSanPham + "_" + System.currentTimeMillis() + fileExtension;
            Path targetPath = targetDir.resolve(newFileName);

            Files.copy(selectedFile.toPath(), targetPath);

            return "/images/Sanpham_img/" + newFileName;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void xacNhanSua() {
        try {
            StringBuilder errorMessage = new StringBuilder();

            if (tenSanPhamField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập tên sản phẩm!\n");
            }
            if (nhaSanXuatField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập nhà sản xuất!\n");
            }
            if (duongDanAnh == null || duongDanAnh.trim().isEmpty()) {
                errorMessage.append("- Vui lòng chọn ảnh sản phẩm!\n");
            }
            if (soLuongField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập số lượng!\n");
            }
            if (giaVonField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập giá vốn!\n");
            }
            if (giaLoiField.getText().trim().isEmpty()) {
                errorMessage.append("- Vui lòng nhập giá lời!\n");
            }

            if (errorMessage.length() > 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng hoàn thành các thông tin sau:\n" + errorMessage.toString(),
                        "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int soLuong, giaVon, giaLoi;
            try {
                soLuong = Integer.parseInt(soLuongField.getText().trim());
                giaVon = Integer.parseInt(giaVonField.getText().trim());
                giaLoi = Integer.parseInt(giaLoiField.getText().trim());

                if (soLuong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số không âm!",
                            "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (giaVon < 0 || giaLoi < 0) {
                    JOptionPane.showMessageDialog(this, "Giá vốn và giá lời phải là số không âm!",
                            "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (giaLoi <= giaVon) {
                    JOptionPane.showMessageDialog(this, "Giá lời phải lớn hơn giá vốn!",
                            "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng, giá vốn và giá lời phải là số nguyên hợp lệ!",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String relativePath = sanPham.getAnhSanPhamURL();
            if (duongDanAnh != null && !duongDanAnh.equals(sanPham.getAnhSanPhamURL())) {
                File newImageFile = new File(duongDanAnh);
                if (newImageFile.exists()) {
                    relativePath = luuAnhVaoThuMucTaiNguyen(newImageFile, sanPham.getMaSanPham());
                    if (relativePath == null) {
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File ảnh không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            sanPham.setMaLoaiSanPham(layMaLoaiSanPhamTuTen((String) loaiSanPhamComboBox.getSelectedItem()));
            sanPham.setTenSanPham(tenSanPhamField.getText().trim());
            sanPham.setNhaSanXuat(nhaSanXuatField.getText().trim());
//            sanPham.setSoLuong(soLuong);
//            sanPham.setGiaVon(giaVon);
//            sanPham.setGiaLoi(giaLoi);
            sanPham.setAnhSanPhamURL(relativePath);
            sanPham.setTrangThai(trangThaiCheckBox.isSelected());

            if (sanPhamBUS.suaSanPham(sanPham)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                originalSanPham = new SanPhamDTO(sanPham);
                updateTable();
                tbSanPham.repaint();
                hienThiDuLieuSanPham();
                exitEditMode();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sản phẩm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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

    private void huySua() {
        sanPham = new SanPhamDTO(originalSanPham);
        hienThiDuLieuSanPham();
        exitEditMode();
    }

    private void xoaSanPham() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm với mã " + sanPham.getMaSanPham() + " không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (sanPhamBUS.xoaSanPham(sanPham.getMaSanPham())) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exitEditMode() {
        isEdit = false;

        loaiSanPhamComboBox.setEnabled(false);
        tenSanPhamField.setEditable(false);
        nhaSanXuatField.setEditable(false);
        soLuongField.setEditable(false);
        giaVonField.setEditable(false);
        giaLoiField.setEditable(false);
        trangThaiCheckBox.setEnabled(false);

        suaButton.setVisible(true);
        xoaButton.setVisible(true);

        xacnhanButton.setVisible(false);
        huyButton.setVisible(false);

        thanhCongCuPanel.revalidate();
        thanhCongCuPanel.repaint();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        List<SanPhamDTO> danhSachSanPham = sanPhamBUS.layDanhSachTatCaSanPham();
        for (SanPhamDTO sp : danhSachSanPham) {
            model.addRow(new Object[]{
                    sp.getMaSanPham(),
                    sp.getTenLoaiSanPham(),
                    sp.getTenSanPham(),
                    sp.getAnhSanPhamURL(),
                    sp.getSoLuong(),
                    sp.getGiaVon(),
                    sp.getGiaLoi(),
                    sp.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }
}