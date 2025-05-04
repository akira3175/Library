package org.example.GUI.Panels.SanPham;

import org.example.BUS.SanPhamBUS;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.example.Utils.ExcelUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.File;
import java.util.List;
import java.io.FileNotFoundException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SanPhamPanel extends JPanel {

    private SanPhamBUS sanPhamBUS;
    private JTable tbSanPham;
    private JTextField searchField;
    private JPanel danhSachPanel;
    private JComboBox<String> statusFilterComboBox;
    private JButton excelButton;

    public SanPhamPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sanPhamBUS = new SanPhamBUS();
        add(createHeaderPanel(), BorderLayout.NORTH);
        danhSachPanel = createDanhSachPanel();
        add(danhSachPanel, BorderLayout.CENTER);
        XuatSanPhamTable();
        System.out.println("SanPhamPanel instance created: " + this);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Quản lý sản phẩm");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        JPanel topRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topRowPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setFont(AppConstants.NORMAL_FONT);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem();
            }

            private void timKiem() {
                String tuKhoa = searchField.getText().trim();
                sanPhamBUS.HienThiSanPhamTimKiem(tbSanPham, tuKhoa);
            }
        });

        StyledButton addButton = new StyledButton("Thêm sản phẩm", AppConstants.PRIMARY_COLOR, 130, 35);
        addButton.addActionListener(e -> {
            SanPham_Them_Dialog dialog = new SanPham_Them_Dialog(null, true, sanPhamBUS, tbSanPham);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        topRowPanel.add(searchField);
        topRowPanel.add(addButton);

        JPanel bottomRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomRowPanel.setOpaque(false);

        String[] filterOptions = {"Tất cả", "Không hoạt động", "Đang hoạt động"};
        statusFilterComboBox = new JComboBox<>(filterOptions);
        statusFilterComboBox.setPreferredSize(new Dimension(150, 35));
        statusFilterComboBox.setFont(AppConstants.NORMAL_FONT);
        statusFilterComboBox.setSelectedItem("Tất cả");
        statusFilterComboBox.addActionListener(e -> XuatSanPhamTable());

        StyledButton filterButton = new StyledButton("Lọc nâng cao", new Color(100, 149, 237), 130, 35);
        filterButton.addActionListener(e -> {
            SanPham_Loc_Dialog dialog = new SanPham_Loc_Dialog(null, true, sanPhamBUS, tbSanPham);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        excelButton = new StyledButton("Excel", new Color(0, 238, 0), 100, 35);
        JPopupMenu excelMenu = new JPopupMenu();
        JMenuItem exportItem = new JMenuItem("Xuất Excel");
        JMenuItem importItem = new JMenuItem("Nhập Excel");
        excelMenu.add(exportItem);
        excelMenu.add(importItem);

        exportItem.addActionListener(e -> {
            String selectedFilter = (String) statusFilterComboBox.getSelectedItem();
            sanPhamBUS.xuatDanhSachSanPhamRaExcel(tbSanPham, selectedFilter);
        });

        importItem.addActionListener(e -> importExcel());

        excelButton.addActionListener(e -> excelMenu.show(excelButton, excelButton.getWidth() / 2, excelButton.getHeight()));

        bottomRowPanel.add(statusFilterComboBox);
        bottomRowPanel.add(excelButton);
        bottomRowPanel.add(filterButton);

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

        String[] columns = {"Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Ảnh sản phẩm URL", "Số lượng", "Giá vốn", "Giá lời", "Trạng Thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 4:
                        return Integer.class;
                    case 5:
                    case 6:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        tbSanPham = new JTable(model);
        tbSanPham.setRowHeight(40);
        tbSanPham.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        tbSanPham.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        JPopupMenu popup = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Thông tin sản phẩm");
        editItem.addActionListener(e -> SuaSanPham());
        JMenuItem deleteItem = new JMenuItem("Xóa");
        deleteItem.addActionListener(e -> XoaSanPham());
        popup.add(editItem);
        popup.add(deleteItem);
        tbSanPham.setComponentPopupMenu(popup);

        JScrollPane scrollPane = new JScrollPane(tbSanPham);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    public void XuatSanPhamTable() {
        System.out.println("XuatSanPhamTable() called with filter: " + statusFilterComboBox.getSelectedItem());
        String selectedFilter = (String) statusFilterComboBox.getSelectedItem();
        sanPhamBUS.hienThiSanPhamLenTable(tbSanPham, selectedFilter);
        tbSanPham.revalidate();
        tbSanPham.repaint();
        System.out.println("Table rows after XuatSanPhamTable: " + tbSanPham.getRowCount());
    }

    public void XuatTatCaSanPhamTable() {
        System.out.println("XuatTatCaSanPhamTable() called");
        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        model.setRowCount(0);
        sanPhamBUS.hienThiSanPhamLenTable(tbSanPham);
        tbSanPham.revalidate();
        tbSanPham.repaint();
        danhSachPanel.revalidate();
        danhSachPanel.repaint();
    }

    private void SuaSanPham() {
        int selectedRow = tbSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xem thông tin!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int maSanPham = (Integer) tbSanPham.getValueAt(selectedRow, 0);
            SanPhamDTO sanPham = sanPhamBUS.laySanPhamTheoMa(maSanPham);
            if (sanPham == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với mã " + maSanPham,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SanPham_Thongtinsanpham_Dialog dialog = new SanPham_Thongtinsanpham_Dialog(null, true, sanPhamBUS, tbSanPham, sanPham);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu sản phẩm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaSanPham() {
        int selectedRow = tbSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tbSanPham.getModel();
        Object maSanPhamObj = model.getValueAt(selectedRow, 0);
        int maSanPham;
        try {
            maSanPham = Integer.parseInt(maSanPhamObj.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm với mã " + maSanPham + " không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (sanPhamBUS.xoaSanPham(maSanPham)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    XuatSanPhamTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa sản phẩm thất bại! Sản phẩm không tồn tại hoặc đã bị xóa.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi hệ thống khai xóa sản phẩm: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn tệp Excel để nhập");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try {
                List<SanPhamDTO> danhSachSanPham = new ExcelUtils().nhapDanhSachSanPhamTuExcel(fileToImport);
                if (danhSachSanPham == null) {
                    throw new Exception("Danh sách sản phẩm trả về là null");
                }
                System.out.println("Số lượng sản phẩm nhập: " + danhSachSanPham.size());
                if (sanPhamBUS.nhapSanPham(danhSachSanPham)) {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ Excel thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    XuatSanPhamTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Không thể truy cập file: " + fileToImport.getName() + ". File đang được sử dụng bởi tiến trình khác. Vui lòng đóng file và thử lại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập tệp Excel: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}