package org.example.GUI.Panels.SanPham;

import org.example.BUS.LoaiSanPhamBUS;
import org.example.DAO.LoaiSanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SanPham_Loai_Dialog extends JDialog {

    private JTextField maLoaiSanPhamField;
    private JTextField tenLoaiSanPhamField;
    private JTextField moTaField;
    private JCheckBox trangThaiCheckBox;
    private JTable tbLoaiSanPham;
    private LoaiSanPhamBUS loaiSanPhamBUS;
    private LoaiSanPhamDAO loaiSanPhamDAO;
    private StyledButton themButton;
    private StyledButton suaButton;
    private StyledButton xoaButton;
    private StyledButton dongButton;
    private boolean isEditMode = false;
    private LoaiSanPhamDTO selectedLoai;
    private SanPham_Them_Dialog parentDialog;

    public SanPham_Loai_Dialog (Window owner, boolean modal,SanPham_Them_Dialog parent) {
        super(owner, "Quản lý loại sản phẩm");
        this.loaiSanPhamBUS = new LoaiSanPhamBUS();
        this.loaiSanPhamDAO = new LoaiSanPhamDAO();
        this.parentDialog = parent;
        initComponents();
        hienThiDanhSachLoaiSanPham();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        tbLoaiSanPham = new JTable();
        tbLoaiSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã loại sản phẩm", "Tên loại sản phẩm", "Mô tả", "Trạng thái"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tbLoaiSanPham.setRowHeight(25);
        tbLoaiSanPham.setFont(AppConstants.NORMAL_FONT);
        tbLoaiSanPham.getTableHeader().setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        tbLoaiSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    chonLoaiSanPham();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tbLoaiSanPham);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(formPanel, "Mã loại sản phẩm:", taoMaLoaiSanPhamField(), gbc, 0);
        addFormField(formPanel, "Tên loại sản phẩm:", taoTenLoaiSanPhamField(), gbc, 1);
        addFormField(formPanel, "Mô tả:", taoMoTaField(), gbc, 2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        themButton = new StyledButton("Thêm", AppConstants.BLUE, 100, 35);
        suaButton = new StyledButton("Sửa", new Color(59, 130, 246), 100, 35);
        xoaButton = new StyledButton("Xóa", new Color(239, 68, 68), 100, 35);
        dongButton = new StyledButton("Đóng", new Color(107, 114, 128), 100, 35);

        themButton.addActionListener(e -> themLoaiSanPham());
        suaButton.addActionListener(e -> suaLoaiSanPham());
        xoaButton.addActionListener(e -> xoaLoaiSanPham());
        dongButton.addActionListener(e -> dongSanPham());

        suaButton.setEnabled(false);
        xoaButton.setEnabled(false);

        buttonPanel.add(themButton);
        buttonPanel.add(suaButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(dongButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(tablePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(600, 500));
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

    private JTextField taoMaLoaiSanPhamField() {
        maLoaiSanPhamField = new JTextField();
        maLoaiSanPhamField.setFont(AppConstants.NORMAL_FONT);
        return maLoaiSanPhamField;
    }

    private JTextField taoTenLoaiSanPhamField() {
        tenLoaiSanPhamField = new JTextField();
        tenLoaiSanPhamField.setFont(AppConstants.NORMAL_FONT);
        return tenLoaiSanPhamField;
    }

    private JTextField taoMoTaField() {
        moTaField = new JTextField();
        moTaField.setFont(AppConstants.NORMAL_FONT);
        return moTaField;
    }

    private void hienThiDanhSachLoaiSanPham() {
        loaiSanPhamBUS.hienThiLoaiSanPhamLenTable(tbLoaiSanPham);
    }

    private void chonLoaiSanPham() {
        int selectedRow = tbLoaiSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            int maLoaiSanPham = (int) tbLoaiSanPham.getValueAt(selectedRow, 0);
            String tenLoaiSanPham = (String) tbLoaiSanPham.getValueAt(selectedRow, 1);
            String moTa = (String) tbLoaiSanPham.getValueAt(selectedRow, 2);
            boolean trangThai = tbLoaiSanPham.getValueAt(selectedRow, 3).equals("Hoạt động");
            selectedLoai = new LoaiSanPhamDTO(maLoaiSanPham, tenLoaiSanPham, moTa, trangThai);
            maLoaiSanPhamField.setText(String.valueOf(maLoaiSanPham));
            tenLoaiSanPhamField.setText(tenLoaiSanPham);
            moTaField.setText(moTa);
            suaButton.setEnabled(true);
            xoaButton.setEnabled(true);
        } else {
            selectedLoai = null;
            maLoaiSanPhamField.setText("");
            tenLoaiSanPhamField.setText("");
            moTaField.setText("");
            suaButton.setEnabled(false);
            xoaButton.setEnabled(false);
        }
    }

    private void themLoaiSanPham() {
        try {
            if (maLoaiSanPhamField.getText().trim().isEmpty() || tenLoaiSanPhamField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã và tên loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LoaiSanPhamDTO loai = new LoaiSanPhamDTO();
            loai.setMaLoaiSanPham(Integer.parseInt(maLoaiSanPhamField.getText().trim()));
            loai.setTenLoaiSanPham(tenLoaiSanPhamField.getText().trim());
            loai.setMoTa(moTaField.getText().trim());

            if (loaiSanPhamBUS.themLoaiSanPham(loai)) {
                JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                hienThiDanhSachLoaiSanPham();
                maLoaiSanPhamField.setText("");
                tenLoaiSanPhamField.setText("");
                moTaField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã loại sản phẩm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaLoaiSanPham() {
        if (selectedLoai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại sản phẩm để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (tenLoaiSanPhamField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên loại sản phẩm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedLoai.setTenLoaiSanPham(tenLoaiSanPhamField.getText().trim());
            selectedLoai.setMoTa(moTaField.getText().trim());

            if (loaiSanPhamBUS.suaLoaiSanPham(selectedLoai)) {
                JOptionPane.showMessageDialog(this, "Sửa loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                hienThiDanhSachLoaiSanPham();
                maLoaiSanPhamField.setText("");
                tenLoaiSanPhamField.setText("");
                moTaField.setText("");
                selectedLoai = null;
                suaButton.setEnabled(false);
                xoaButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa loại sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaLoaiSanPham() {
        if (selectedLoai == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại sản phẩm để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa loại sản phẩm với mã " + selectedLoai.getMaLoaiSanPham() + " không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (loaiSanPhamBUS.xoaLoaiSanPham(selectedLoai.getMaLoaiSanPham())) {
                JOptionPane.showMessageDialog(this, "Xóa loại sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                hienThiDanhSachLoaiSanPham();
                maLoaiSanPhamField.setText("");
                tenLoaiSanPhamField.setText("");
                moTaField.setText("");
                selectedLoai = null;
                suaButton.setEnabled(false);
                xoaButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa loại sản phẩm thất bại! Có thể loại này đang được sử dụng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void dongSanPham(){
        parentDialog.capNhatComboBox();
        dispose();
    }
}
