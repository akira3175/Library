package org.example.GUI.Panels.KhachHang;

import org.example.BUS.KhachHangBUS;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class KhachHangPanel extends JPanel {

    private KhachHangBUS khachHangBUS;
    private JTable tbKhachHang;
    private JTextField searchField;
    private JPanel danhSachPanel;
    private JComboBox<String> statusFilterComboBox;

    public KhachHangPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        khachHangBUS = new KhachHangBUS();

        add(createHeaderPanel(), BorderLayout.NORTH);

        danhSachPanel = createDanhSachPanel();
        add(danhSachPanel, BorderLayout.CENTER);

        xuatKhachHangTable();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Quản lý khách hàng");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        JPanel topRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topRowPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm khách hàng...");
        searchField.setPreferredSize(new Dimension(200, 35));

        JButton searchButton = new JButton("Tìm");
        searchButton.setPreferredSize(new Dimension(80, 35));
        searchButton.addActionListener(e -> {
            String tuKhoa = searchField.getText().trim();
            khachHangBUS.hienThiKhachHangTimKiem(tbKhachHang, tuKhoa);
        });

        JButton addButton = new JButton("Thêm khách hàng");
        addButton.setBackground(AppConstants.PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(150, 35));
        addButton.addActionListener(e -> {
            KhachHang_Them_Dialog dialog = new KhachHang_Them_Dialog(null, true, khachHangBUS, tbKhachHang);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        topRowPanel.add(searchField);
        topRowPanel.add(searchButton);
        topRowPanel.add(addButton);

        JPanel bottomRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomRowPanel.setOpaque(false);

        String[] filterOptions = {"Tất cả", "Không hoạt động", "Đang hoạt động"};
        statusFilterComboBox = new JComboBox<>(filterOptions);
        statusFilterComboBox.setPreferredSize(new Dimension(150, 35));
        statusFilterComboBox.addActionListener(e -> xuatKhachHangTable());

        bottomRowPanel.add(statusFilterComboBox);

        actionPanel.add(topRowPanel);
        actionPanel.add(Box.createVerticalStrut(5));
        actionPanel.add(bottomRowPanel);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createDanhSachPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        String[] columns = {"Mã khách hàng", "Họ tên", "Số điện thoại", "Địa chỉ", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        tbKhachHang = new JTable(model);
        tbKhachHang.setRowHeight(40);
        tbKhachHang.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        tbKhachHang.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        JPopupMenu popup = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Sửa");
        editItem.addActionListener(e -> suaKhachHang());
        JMenuItem deleteItem = new JMenuItem("Xóa");
        deleteItem.addActionListener(e -> xoaKhachHang());
        popup.add(editItem);
        popup.add(deleteItem);
        tbKhachHang.setComponentPopupMenu(popup);

        JScrollPane scrollPane = new JScrollPane(tbKhachHang);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private void xuatKhachHangTable() {
        String selectedFilter = (String) statusFilterComboBox.getSelectedItem();
        khachHangBUS.hienThiKhachHangLenTable(tbKhachHang, selectedFilter);
    }

    private void suaKhachHang() {
        int selectedRow = tbKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xem thông tin!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int maKhachHang = (Integer) tbKhachHang.getValueAt(selectedRow, 0);
            KhachHangDTO khachHang = khachHangBUS.layKhachHangTheoMa(maKhachHang);
            if (khachHang == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã " + maKhachHang,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            KhachHang_Sua_Dialog dialog = new KhachHang_Sua_Dialog(null, true, khachHangBUS, tbKhachHang, khachHang);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaKhachHang() {
        int selectedRow = tbKhachHang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tbKhachHang.getModel();
        Object maKhachHangObj = model.getValueAt(selectedRow, 0);
        int maKhachHang;
        try {
            maKhachHang = Integer.parseInt(maKhachHangObj.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng với mã " + maKhachHang + " không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (khachHangBUS.xoaKhachHang(maKhachHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    xuatKhachHangTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa khách hàng thất bại! Khách hàng không tồn tại hoặc đã bị xóa.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi hệ thống khi xóa khách hàng: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}