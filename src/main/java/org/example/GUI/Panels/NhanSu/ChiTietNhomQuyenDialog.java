package org.example.GUI.Panels.NhanSu;

import org.example.BUS.VaiTroBUS;
import org.example.DAO.VaiTroDAO;
import org.example.DTO.VaiTro;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChiTietNhomQuyenDialog extends JDialog {
    private JTextField maNhomQuyenField;
    private JTextField tenNhomQuyenField;
    private JTextArea moTaNhomQuyenArea;
    private JTable quyenChiTietTable;
    private boolean isConfirmed = false;
    private String maNhomQuyen;
    private VaiTro vaiTro;
    private VaiTroBUS vaiTroBUS;

    public ChiTietNhomQuyenDialog(Window owner, String maNhomQuyen, String tenNhomQuyen, String moTa) {
        super(owner, "Chi tiết nhóm quyền");
        vaiTroBUS = new VaiTroBUS();
        this.maNhomQuyen = maNhomQuyen;

        initComponents();

        // Populate fields with group data
        maNhomQuyenField.setText(maNhomQuyen);
        tenNhomQuyenField.setText(tenNhomQuyen);
        moTaNhomQuyenArea.setText(moTa);

        // Load permissions for this group
        loadPermissionsForGroup();

        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Group ID field
        JPanel idPanel = new JPanel(new BorderLayout(0, 5));
        idPanel.setOpaque(false);
        idPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel idLabel = new JLabel("Mã nhóm quyền:");
        idLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        maNhomQuyenField = new JTextField();
        maNhomQuyenField.setEditable(false);
        maNhomQuyenField.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        idPanel.add(idLabel, BorderLayout.NORTH);
        idPanel.add(maNhomQuyenField, BorderLayout.CENTER);

        // Group name field
        JPanel namePanel = new JPanel(new BorderLayout(0, 5));
        namePanel.setOpaque(false);
        namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel nameLabel = new JLabel("Tên nhóm quyền:");
        nameLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        tenNhomQuyenField = new JTextField();
        tenNhomQuyenField.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(tenNhomQuyenField, BorderLayout.CENTER);

        // Description field
        JPanel descPanel = new JPanel(new BorderLayout(0, 5));
        descPanel.setOpaque(false);
        descPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel descLabel = new JLabel("Mô tả:");
        descLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        moTaNhomQuyenArea = new JTextArea();
        moTaNhomQuyenArea.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        moTaNhomQuyenArea.setLineWrap(true);
        moTaNhomQuyenArea.setWrapStyleWord(true);
        moTaNhomQuyenArea.setBorder(new LineBorder(new Color(229, 231, 235), 1));

        JScrollPane descScrollPane = new JScrollPane(moTaNhomQuyenArea);
        descScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));

        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(descScrollPane, BorderLayout.CENTER);

        // Permissions section
        JPanel permissionsPanel = new JPanel(new BorderLayout(0, 10));
        permissionsPanel.setOpaque(false);

        JLabel permissionsLabel = new JLabel("Danh sách quyền trong nhóm:");
        permissionsLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        permissionsLabel.setBorder(new EmptyBorder(15, 0, 5, 0));

        // Permissions table
        String[] columns = {"Mã quyền", "Tên quyền", "Thuộc nhóm"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only the "Thuộc nhóm" column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? Boolean.class : String.class;
            }
        };

        quyenChiTietTable = new JTable(model);
        quyenChiTietTable.setRowHeight(40);
        quyenChiTietTable.setShowVerticalLines(false);
        quyenChiTietTable.setGridColor(new Color(229, 231, 235));
        quyenChiTietTable.setSelectionBackground(new Color(243, 244, 246));
        quyenChiTietTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Style the table header
        JTableHeader header = quyenChiTietTable.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Center align the first column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        quyenChiTietTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Set column widths
        quyenChiTietTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        quyenChiTietTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        quyenChiTietTable.getColumnModel().getColumn(2).setPreferredWidth(80);

        JScrollPane permissionsScrollPane = new JScrollPane(quyenChiTietTable);
        permissionsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        permissionsScrollPane.setPreferredSize(new Dimension(500, 300));

        permissionsPanel.add(permissionsLabel, BorderLayout.NORTH);
        permissionsPanel.add(permissionsScrollPane, BorderLayout.CENTER);

        // Add components to form panel
        formPanel.add(idPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(descPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(permissionsPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        StyledButton cancelButton = new StyledButton("Hủy", new Color(107, 114, 128), 120, 35);
        StyledButton saveButton = new StyledButton("Lưu thay đổi", AppConstants.PRIMARY_COLOR, 120, 35);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveGroup());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(600, 650));
    }

    private void loadPermissionsForGroup() {
        // Clear existing data
        DefaultTableModel quyenChiTietModel = (DefaultTableModel) quyenChiTietTable.getModel();
        quyenChiTietModel.setRowCount(0);

        // Load permissions based on the selected group
        if (maNhomQuyen != null) {
            if (maNhomQuyen.equals("R001")) {
                // Admin group - has all permissions
                quyenChiTietModel.addRow(new Object[]{"Q001", "Quản lý sản phẩm", true});
                quyenChiTietModel.addRow(new Object[]{"Q002", "Quản lý nhân sự", true});
                quyenChiTietModel.addRow(new Object[]{"Q003", "Bán hàng", true});
                quyenChiTietModel.addRow(new Object[]{"Q004", "Nhập kho", true});
                quyenChiTietModel.addRow(new Object[]{"Q005", "Xem báo cáo", true});
                quyenChiTietModel.addRow(new Object[]{"Q006", "Quản lý hệ thống", true});
                quyenChiTietModel.addRow(new Object[]{"Q007", "Quản lý khách hàng", true});
                quyenChiTietModel.addRow(new Object[]{"Q008", "Quản lý nhà cung cấp", true});
            } else if (maNhomQuyen.equals("R002")) {
                // Sales group
                quyenChiTietModel.addRow(new Object[]{"Q001", "Quản lý sản phẩm", false});
                quyenChiTietModel.addRow(new Object[]{"Q002", "Quản lý nhân sự", false});
                quyenChiTietModel.addRow(new Object[]{"Q003", "Bán hàng", true});
                quyenChiTietModel.addRow(new Object[]{"Q004", "Nhập kho", false});
                quyenChiTietModel.addRow(new Object[]{"Q005", "Xem báo cáo", true});
                quyenChiTietModel.addRow(new Object[]{"Q006", "Quản lý hệ thống", false});
                quyenChiTietModel.addRow(new Object[]{"Q007", "Quản lý khách hàng", true});
                quyenChiTietModel.addRow(new Object[]{"Q008", "Quản lý nhà cung cấp", false});
            } else if (maNhomQuyen.equals("R003")) {
                // Inventory group
                quyenChiTietModel.addRow(new Object[]{"Q001", "Quản lý sản phẩm", true});
                quyenChiTietModel.addRow(new Object[]{"Q002", "Quản lý nhân sự", false});
                quyenChiTietModel.addRow(new Object[]{"Q003", "Bán hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q004", "Nhập kho", true});
                quyenChiTietModel.addRow(new Object[]{"Q005", "Xem báo cáo", true});
                quyenChiTietModel.addRow(new Object[]{"Q006", "Quản lý hệ thống", false});
                quyenChiTietModel.addRow(new Object[]{"Q007", "Quản lý khách hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q008", "Quản lý nhà cung cấp", true});
            } else if (maNhomQuyen.equals("R004")) {
                // Manager group
                quyenChiTietModel.addRow(new Object[]{"Q001", "Quản lý sản phẩm", true});
                quyenChiTietModel.addRow(new Object[]{"Q002", "Quản lý nhân sự", true});
                quyenChiTietModel.addRow(new Object[]{"Q003", "Bán hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q004", "Nhập kho", false});
                quyenChiTietModel.addRow(new Object[]{"Q005", "Xem báo cáo", true});
                quyenChiTietModel.addRow(new Object[]{"Q006", "Quản lý hệ thống", true});
                quyenChiTietModel.addRow(new Object[]{"Q007", "Quản lý khách hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q008", "Quản lý nhà cung cấp", false});
            } else {
                // New group - no permissions by default
                quyenChiTietModel.addRow(new Object[]{"Q001", "Quản lý sản phẩm", false});
                quyenChiTietModel.addRow(new Object[]{"Q002", "Quản lý nhân sự", false});
                quyenChiTietModel.addRow(new Object[]{"Q003", "Bán hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q004", "Nhập kho", false});
                quyenChiTietModel.addRow(new Object[]{"Q005", "Xem báo cáo", false});
                quyenChiTietModel.addRow(new Object[]{"Q006", "Quản lý hệ thống", false});
                quyenChiTietModel.addRow(new Object[]{"Q007", "Quản lý khách hàng", false});
                quyenChiTietModel.addRow(new Object[]{"Q008", "Quản lý nhà cung cấp", false});
            }
        }
    }

    private void saveGroup() {
        // Validate input fields
        if (tenNhomQuyenField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhóm quyền", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // In a real application, you would validate all fields and save to database

        // Save the permissions
        StringBuilder permissions = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) quyenChiTietTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean selected = (boolean) model.getValueAt(i, 2);
            if (selected) {
                String maQuyen = (String) model.getValueAt(i, 0);
                permissions.append(maQuyen).append(", ");
            }
        }

        String maVaiTro = maNhomQuyenField.getText();
        String tenVaiTro = tenNhomQuyenField.getText();
        String moTa = moTaNhomQuyenArea.getText();

        vaiTro = new VaiTro(
                maNhomQuyen.isEmpty() ? 0 : Integer.parseInt(maNhomQuyen),
                tenVaiTro,
                moTa
        );

        vaiTro = vaiTroBUS.themHoacSuaVaiTro(vaiTro);

        JOptionPane.showMessageDialog(
                this,
                "Đã lưu thay đổi cho nhóm quyền: " + tenNhomQuyenField.getText(),
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE
        );

        isConfirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getMaNhomQuyen() {
        return vaiTro.getMaVaiTro() + "";
    }

    public String getTenNhomQuyen() {
        return vaiTro.getTenVaiTro();
    }

    public String getMoTa() {
        return vaiTro.getMoTa();
    }
}

