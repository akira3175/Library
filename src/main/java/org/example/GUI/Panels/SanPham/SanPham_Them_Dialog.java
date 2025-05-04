package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SanPham_Them_Dialog extends JDialog {

    private JComboBox<String> loaiSanPhamComboBox;
    private JLabel anhSanPhamLabel;
    private JTextField tenSanPhamField;
    private JTextField nhaSanXuatField;
    private JTextField soLuongField;
    private JTextField giaVonField;
    private JTextField giaLoiField;
    private SanPhamBUS sanPhamBUS;
    private JTable tbSanPham;
    private String duongDanAnh;

    public SanPham_Them_Dialog(Window owner, boolean modal, SanPhamBUS sanPhamBUS, JTable tbSanPham) {
        super(owner, "Thêm sản phẩm mới");
        this.sanPhamBUS = sanPhamBUS;
        this.tbSanPham = tbSanPham;
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

        addLoaiSanPhamField(formPanel, gbc, 0);
        addFormField(formPanel, "Tên sản phẩm:", taoTenSanPhamField(), gbc, 1);
        addFormField(formPanel, "Nhà sản xuất:", taoNhaSanXuatField(), gbc, 2);
        addFormField(formPanel, "Số lượng:", taoSoLuongField(), gbc, 3);
        addFormField(formPanel, "Giá vốn:", taoGiaVonField(), gbc, 4);
        addFormField(formPanel, "Giá lời:", taoGiaLoiField(), gbc, 5);

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

    private void addLoaiSanPhamField(JPanel panel, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel("Loại sản phẩm:");
        label.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        JPanel loaiPanel = new JPanel(new BorderLayout());
        loaiSanPhamComboBox = new JComboBox<>();
        loaiSanPhamComboBox.setFont(AppConstants.NORMAL_FONT);

        StyledButton themLoaiButton = new StyledButton("+", AppConstants.BLUE, 30, 30);
        themLoaiButton.setFont(new Font("Arial", Font.BOLD, 24));
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
        
        // Thêm bộ lọc file để chỉ cho phép ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Kiểm tra file tồn tại và hợp lệ
            if (selectedFile != null && selectedFile.exists() && selectedFile.isFile()) {
                try {
                    // Kiểm tra định dạng file
                    String fileName = selectedFile.getName().toLowerCase();
                    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
                        JOptionPane.showMessageDialog(this, 
                                "Vui lòng chọn file ảnh định dạng JPG, JPEG hoặc PNG!", 
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    duongDanAnh = selectedFile.getAbsolutePath();
                    ImageIcon originalIcon = new ImageIcon(duongDanAnh);
                    
                    // Kiểm tra nếu ImageIcon không tải được ảnh
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

    private void moLoaiSanPhamDialog() {
        SanPham_Loai_Dialog loaiDialog = new SanPham_Loai_Dialog(this, true, this);
        loaiDialog.setVisible(true);
    }

    private void taiDuLieuLoaiSanPham() {
        List<SanPhamDTO> danhSachLoaiSanPham = sanPhamBUS.layDanhSachLoaiSanPham();
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
        List<SanPhamDTO> danhSachLoai = sanPhamBUS.layDanhSachLoaiSanPham();
        for (SanPhamDTO loai : danhSachLoai) {
            if (loai.getTenLoaiSanPham().equals(tenLoaiSanPham)) {
                return loai.getMaLoaiSanPham();
            }
        }
        return -1;
    }

    private String luuAnhVaoThuMucTaiNguyen(File selectedFile, int maSanPham) {
        try {
            // Đường dẫn thư mục tài nguyên trong dự án
            String resourceDir = "src/main/resources/images/Sanpham_img";
            Path targetDir = Paths.get(resourceDir);
            
            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            // Tạo tên file duy nhất dựa trên mã sản phẩm và timestamp
            String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
            String newFileName = "sp_" + maSanPham + "_" + System.currentTimeMillis() + fileExtension;
            Path targetPath = targetDir.resolve(newFileName);

            // Sao chép file vào thư mục tài nguyên
            Files.copy(selectedFile.toPath(), targetPath);

            // Trả về đường dẫn tương đối để lưu vào cơ sở dữ liệu
            return "/images/Sanpham_img/" + newFileName;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void luuSanPham() {
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

            double giaVon, giaLoi;
            try {
                giaVon = Double.parseDouble(giaVonField.getText().trim());
                giaLoi = Double.parseDouble(giaLoiField.getText().trim());
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
                JOptionPane.showMessageDialog(this, "Giá vốn và giá lời phải là số hợp lệ!",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SanPhamDTO sanPham = new SanPhamDTO();
            int maSanPham = sanPhamBUS.laySanPhamTheoMaMax();
            if (maSanPham == -1) {
                JOptionPane.showMessageDialog(this, "Không thể tạo mã sản phẩm mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sanPham.setMaSanPham(maSanPham);
            int maLoaiSanPham = layMaLoaiSanPhamTuTen(loaiSanPhamComboBox.getSelectedItem().toString());
            if (maLoaiSanPham == -1) {
                JOptionPane.showMessageDialog(this, "Loại sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sanPham.setMaLoaiSanPham(maLoaiSanPham);

            // Lưu ảnh vào thư mục tài nguyên và lấy đường dẫn tương đối
            String relativePath = luuAnhVaoThuMucTaiNguyen(new File(duongDanAnh), maSanPham);
            if (relativePath == null) {
                return; // Lỗi đã được hiển thị trong luuAnhVaoThuMucTaiNguyen
            }
            sanPham.setAnhSanPhamURL(relativePath);

            sanPham.setTenSanPham(tenSanPhamField.getText().trim());
            sanPham.setNhaSanXuat(nhaSanXuatField.getText().trim());
            sanPham.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
            sanPham.setGiaVon(Integer.parseInt(giaVonField.getText().trim()));
            sanPham.setGiaLoi(Integer.parseInt(giaLoiField.getText().trim().toString()));

            if (sanPhamBUS.themSanPham(sanPham)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                sanPhamBUS.hienThiSanPhamLenTable(tbSanPham); // Làm mới bảng
                tbSanPham.repaint(); // Đảm bảo bảng được vẽ lại
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void capNhatComboBox() {
        taiDuLieuLoaiSanPham();
    }
}