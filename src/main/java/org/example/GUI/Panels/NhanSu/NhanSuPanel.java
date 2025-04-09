package org.example.GUI.Panels.NhanSu;

import org.example.BUS.NguoiDungBUS;
import org.example.DTO.NguoiDung;
import org.example.GUI.Components.BooleanRenderer;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NhanSuPanel extends JPanel {
    private JTable table;
    private JTextField searchField;
    private NguoiDungBUS nguoiDungBUS;
    private JTabbedPane tabbedPane;
    private JPanel danhSachPanel;
    private NhomQuyenPanel nhomQuyenPanel;

    public NhanSuPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nguoiDungBUS = new NguoiDungBUS();

        add(createHeaderPanel(), BorderLayout.NORTH);

        // Create tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(AppConstants.NORMAL_FONT);

        // Create panels for each tab
        danhSachPanel = createDanhSachPanel();
        nhomQuyenPanel = new NhomQuyenPanel();

        // Add tabs
        tabbedPane.addTab("Danh sách nhân sự", danhSachPanel);
        tabbedPane.addTab("Nhóm quyền", nhomQuyenPanel);

        // Add tabbed pane to main panel
        add(tabbedPane, BorderLayout.CENTER);

        loadDataNhanSuTable();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Nhân sự");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm người dùng...");
        searchField.setPreferredSize(new Dimension(200, 35));

        StyledButton addButton = new StyledButton("Thêm người dùng", AppConstants.PRIMARY_COLOR, 150, 35);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddUserDialog();
            }
        });

        actionPanel.add(searchField);
        actionPanel.add(addButton);

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

        String[] columns = {"Mã nhân sự", "Tên đăng nhập", "Họ tên", "Email", "Số điện thoại", "Còn hoạt động"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa bất kỳ ô nào trong bảng
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? Boolean.class : String.class;
            }
        };
        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setFont(AppConstants.NORMAL_FONT);
        table.getColumnModel().getColumn(5).setCellRenderer(new BooleanRenderer());
        table.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Add double-click listener to open user details
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        showUserDetailsDialog(row);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private void loadDataNhanSuTable() {
        List<NguoiDung> danhSachNguoiDung = nguoiDungBUS.danhSachNguoiDung();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (NguoiDung nguoiDung: danhSachNguoiDung) {
            model.addRow(new Object[]{
                    nguoiDung.getMaNguoiDung(),
                    nguoiDung.getTenDangNhap(),
                    nguoiDung.getHoTen(),
                    nguoiDung.getEmail(),
                    nguoiDung.getSoDienThoai(),
                    nguoiDung.isConHoatDong()
            });
        }
    }

    private void showAddUserDialog() {
        ThemNguoiDungDialog dialog = new ThemNguoiDungDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{
                    dialog.getMaNguoiDung(),
                    dialog.getTenDangNhap(),
                    dialog.getHoTen(),
                    dialog.getEmail(),
                    dialog.getSoDienThoai(),
                    true // New users are active by default
            });
        }
    }

    private void showUserDetailsDialog(int row) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        String maNguoiDung = model.getValueAt(row, 0).toString();
        NguoiDung nguoiDung = nguoiDungBUS.layNguoiDungTheoID(Integer.parseInt(maNguoiDung));

        ChiTietNguoiDungDialog dialog = new ChiTietNguoiDungDialog(
                SwingUtilities.getWindowAncestor(this),
                nguoiDung
        );

        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            model.setValueAt(dialog.getHoTen(), row, 2);
            model.setValueAt(dialog.getEmail(), row, 3);
            model.setValueAt(dialog.getSoDienThoai(), row, 4);
            model.setValueAt(dialog.isActive(), row, 5);

            if (dialog.getNewPassword() != null) {
                System.out.println("New password for " + nguoiDung.getTenDangNhap() + ": " + dialog.getNewPassword());
            }
        }
    }
}

