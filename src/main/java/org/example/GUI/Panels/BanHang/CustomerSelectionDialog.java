package org.example.GUI.Panels.BanHang;

import org.example.BUS.KhachHangBUS;
import org.example.DTO.KhachHangDTO;
import org.example.GUI.Constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerSelectionDialog extends JDialog {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSelectionDialog.class);
    private JTable customerTable;
    private JTextField searchField;
    private KhachHangBUS khachHangBUS;
    private List<KhachHangDTO> selectedCustomers;
    private boolean confirmed;

    public CustomerSelectionDialog(Frame parent) {
        super(parent, "Chọn Khách Hàng", true);
        khachHangBUS = new KhachHangBUS();
        selectedCustomers = new ArrayList<>();
        confirmed = false;

        setLayout(new BorderLayout(10, 10));
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setBackground(AppConstants.BACKGROUND_COLOR);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadCustomers();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm khách hàng...");
        searchField.setPreferredSize(new Dimension(200, 35));

        JButton searchButton = new JButton("Tìm");
        searchButton.setPreferredSize(new Dimension(80, 35));
        searchButton.addActionListener(e -> searchCustomers());

        headerPanel.add(searchField, BorderLayout.CENTER);
        headerPanel.add(searchButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] columns = {"Mã KH", "Họ Tên", "Số Điện Thoại", "Địa Chỉ", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable = new JTable(model);
        customerTable.setRowHeight(40);
        customerTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        customerTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        customerTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Set column widths
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã KH
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Họ Tên
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Số Điện Thoại
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Địa Chỉ
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Trạng Thái

        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setPreferredSize(new Dimension(100, 35));
        confirmButton.setBackground(AppConstants.PRIMARY_COLOR);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmSelection();
            }
        });

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void loadCustomers() {
        try {
            khachHangBUS.hienThiKhachHangLenTable(customerTable, "Đang hoạt động");
            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            logger.info("Tải {} khách hàng đang hoạt động.", model.getRowCount());
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng đang hoạt động!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tải danh sách khách hàng: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCustomers() {
        String tuKhoa = searchField.getText().trim();
        try {
            khachHangBUS.hienThiKhachHangTimKiem(customerTable, tuKhoa);
            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            logger.info("Tìm kiếm khách hàng với từ khóa '{}', tìm thấy {} kết quả.", tuKhoa, model.getRowCount());
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm khách hàng: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmSelection() {
        int[] selectedRows = customerTable.getSelectedRows();
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        selectedCustomers.clear();

        for (int row : selectedRows) {
            int maKhachHang = (Integer) model.getValueAt(row, 0);
            KhachHangDTO khachHang = khachHangBUS.layKhachHangTheoMa(maKhachHang);
            if (khachHang != null) {
                selectedCustomers.add(khachHang);
            }
        }

        if (selectedCustomers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        confirmed = true;
        dispose();
    }

    public List<KhachHangDTO> getSelectedCustomers() {
        return confirmed ? selectedCustomers : new ArrayList<>();
    }
}