package org.example.GUI.Panels.KhuyenMai;

import org.example.BUS.KhuyenMaiBUS;
import org.example.DTO.KhuyenMai;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import org.example.Utils.ExcelUtils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KhuyenMaiPanel extends JPanel {
    private JTable khuyenMaiTable;
    private JTextField searchField;
    private KhuyenMaiBUS khuyenMaiBUS;
    private List<KhuyenMai> danhSachKhuyenMai;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ExcelUtils excelUtils;

    public KhuyenMaiPanel() {
        khuyenMaiBUS = new KhuyenMaiBUS();
        excelUtils = new ExcelUtils();
        setLayout(new BorderLayout(0, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add header panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Add main content panel
        add(createContentPanel(), BorderLayout.CENTER);

        // Load data
        loadKhuyenMaiData();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Quản lý khuyến mãi");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm khuyến mãi...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addActionListener(e -> searchKhuyenMai());

        StyledButton searchButton = new StyledButton("Tìm kiếm", new Color(107, 114, 128), 120, 35);
        searchButton.addActionListener(e -> searchKhuyenMai());

        StyledButton refreshButton = new StyledButton("Làm mới", new Color(107, 114, 128), 120, 35);
        refreshButton.addActionListener(e -> loadKhuyenMaiData());

        StyledButton excelButton = new StyledButton("Excel", new Color(16, 185, 129), 120, 35);
        excelButton.addActionListener(e -> showExcelMenu(excelButton));

        StyledButton addButton = new StyledButton("Thêm khuyến mãi", AppConstants.PRIMARY_COLOR, 150, 35);
        addButton.addActionListener(e -> showAddKhuyenMaiDialog());

        actionPanel.add(searchField);
        actionPanel.add(searchButton);
        actionPanel.add(refreshButton);
        actionPanel.add(excelButton);
        actionPanel.add(addButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private void showExcelMenu(JButton source) {
        JPopupMenu excelMenu = new JPopupMenu();
        
        JMenuItem exportItem = new JMenuItem("Xuất Excel");
        exportItem.addActionListener(e -> exportToExcel());
        
        JMenuItem importItem = new JMenuItem("Import Excel");
        importItem.addActionListener(e -> importFromExcel());
        
        excelMenu.add(exportItem);
        excelMenu.add(importItem);
        
        excelMenu.show(source, 0, source.getHeight());
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Create table
        String[] columns = {"Mã KM", "Tên khuyến mãi", "Số tiền KM", "Điều kiện HĐ", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? Boolean.class : String.class;
            }
        };

        khuyenMaiTable = new JTable(model);
        khuyenMaiTable.setRowHeight(40);
        khuyenMaiTable.setShowVerticalLines(false);
        khuyenMaiTable.setGridColor(new Color(229, 231, 235));
        khuyenMaiTable.setSelectionBackground(new Color(243, 244, 246));
        khuyenMaiTable.setSelectionForeground(new Color(17, 24, 39));
        khuyenMaiTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Style the table header
        JTableHeader header = khuyenMaiTable.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        khuyenMaiTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        khuyenMaiTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        khuyenMaiTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        khuyenMaiTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        khuyenMaiTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        // Set column widths
        khuyenMaiTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        khuyenMaiTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        khuyenMaiTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        khuyenMaiTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        khuyenMaiTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        khuyenMaiTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        khuyenMaiTable.getColumnModel().getColumn(6).setPreferredWidth(80);

        // Add double-click listener to open promotion details
        khuyenMaiTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = khuyenMaiTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        showEditKhuyenMaiDialog(selectedRow);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(khuyenMaiTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);

        StyledButton editButton = new StyledButton("Sửa", new Color(59, 130, 246), 100, 35);
        StyledButton deleteButton = new StyledButton("Xóa", new Color(239, 68, 68), 100, 35);

        editButton.addActionListener(e -> {
            int selectedRow = khuyenMaiTable.getSelectedRow();
            if (selectedRow >= 0) {
                showEditKhuyenMaiDialog(selectedRow);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn một khuyến mãi để sửa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = khuyenMaiTable.getSelectedRow();
            if (selectedRow >= 0) {
                deleteKhuyenMai(selectedRow);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn một khuyến mãi để xóa",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private void loadKhuyenMaiData() {
        danhSachKhuyenMai = khuyenMaiBUS.layDanhSachKhuyenMai();
        updateTableData(danhSachKhuyenMai);
    }

    private void updateTableData(List<KhuyenMai> danhSach) {
        DefaultTableModel model = (DefaultTableModel) khuyenMaiTable.getModel();
        model.setRowCount(0);

        for (KhuyenMai khuyenMai : danhSach) {
            model.addRow(new Object[]{
                    khuyenMai.getMaKhuyenMai(),
                    khuyenMai.getTenKhuyenMai(),
                    formatCurrency(khuyenMai.getSoTienKhuyenMai()),
                    formatCurrency(khuyenMai.getDieuKienHoaDon()),
                    dateFormat.format(khuyenMai.getNgayBatDau()),
                    dateFormat.format(khuyenMai.getNgayKetThuc()),
                    khuyenMai.getTrangThai()
            });
        }
    }

    private String formatCurrency(int amount) {
        return String.format("%,d VNĐ", amount).replace(",", ".");
    }

    private void searchKhuyenMai() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadKhuyenMaiData();
        } else {
            List<KhuyenMai> ketQua = khuyenMaiBUS.timKiemKhuyenMai(searchText);
            updateTableData(ketQua);
        }
    }

    private void showAddKhuyenMaiDialog() {
        KhuyenMaiDialog dialog = new KhuyenMaiDialog(SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            loadKhuyenMaiData();
        }
    }

    private void showEditKhuyenMaiDialog(int selectedRow) {
        int maKhuyenMai = (int) khuyenMaiTable.getValueAt(selectedRow, 0);
        KhuyenMai khuyenMai = khuyenMaiBUS.layKhuyenMaiTheoMa(maKhuyenMai);

        KhuyenMaiDialog dialog = new KhuyenMaiDialog(SwingUtilities.getWindowAncestor(this), khuyenMai);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            loadKhuyenMaiData();
        }
    }

    private void deleteKhuyenMai(int selectedRow) {
        int maKhuyenMai = (int) khuyenMaiTable.getValueAt(selectedRow, 0);
        String tenKhuyenMai = (String) khuyenMaiTable.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa khuyến mãi \"" + tenKhuyenMai + "\"?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = khuyenMaiBUS.xoaKhuyenMai(maKhuyenMai);
            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa khuyến mãi thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadKhuyenMaiData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Xóa khuyến mãi thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void exportToExcel() {
        excelUtils.xuatDanhSachKhuyenMaiRaExcel(danhSachKhuyenMai, khuyenMaiTable);
    }

    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn tệp Excel để import");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        
        try {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        } catch (Exception e) {
            System.err.println("Lỗi khi thiết lập thư mục mặc định: " + e.getMessage());
            fileChooser.setCurrentDirectory(null);
        }

        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try {
                List<KhuyenMai> importedKhuyenMai = excelUtils.nhapDanhSachKhuyenMaiTuExcel(fileToImport);
                int successCount = 0;
                int duplicateCount = 0;
                
                // Save imported promotions to database
                for (KhuyenMai km : importedKhuyenMai) {
                    if (khuyenMaiBUS.kiemTraTenKhuyenMaiTonTai(km.getTenKhuyenMai())) {
                        duplicateCount++;
                        continue;
                    }
                    KhuyenMai result = khuyenMaiBUS.themKhuyenMai(km);
                    if (result != null) {
                        successCount++;
                    }
                }
                
                String message = String.format(
                    "Import Excel hoàn tất!\n- Thành công: %d khuyến mãi\n- Bỏ qua: %d khuyến mãi do trùng tên",
                    successCount, duplicateCount
                );
                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadKhuyenMaiData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi import Excel: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        }
    }
}