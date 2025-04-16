package org.example.GUI.Panels.NhanSu;

import org.example.BUS.VaiTroBUS;
import org.example.BUS.QuyenBUS;
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
import java.util.List;
import org.example.DTO.Quyen;


public class ChiTietNhomQuyenDialog extends JDialog {
    private JTextField maVaiTroField;
    private JTextField tenVaiTroField;
    private JTextArea moTaVaiTroArea;
    private JTable quyenChiTietTable;
    private boolean isConfirmed = false;
    private String maVaiTro;
    private VaiTro vaiTro;
    private VaiTroBUS vaiTroBUS;
    private QuyenBUS quyenBUS;
    private List<Quyen> danhSachQuyen;

    public ChiTietNhomQuyenDialog(Window owner, String maVaiTro, String tenVaiTro, String moTa) {
        super(owner, "Chi tiết vai trò");
        vaiTroBUS = new VaiTroBUS();
        quyenBUS = new QuyenBUS();
        this.maVaiTro = maVaiTro;
        
        initComponents();

        // Populate fields with group data
        maVaiTroField.setText(maVaiTro);
        tenVaiTroField.setText(tenVaiTro);
        moTaVaiTroArea.setText(moTa);

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

        JLabel idLabel = new JLabel("Mã vai trò:");
        idLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        maVaiTroField = new JTextField();
        maVaiTroField.setEditable(false);
        maVaiTroField.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        idPanel.add(idLabel, BorderLayout.NORTH);
        idPanel.add(maVaiTroField, BorderLayout.CENTER);

        // Group name field
        JPanel namePanel = new JPanel(new BorderLayout(0, 5));
        namePanel.setOpaque(false);
        namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel nameLabel = new JLabel("Tên vai trò:");
        nameLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        tenVaiTroField = new JTextField();
        tenVaiTroField.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(tenVaiTroField, BorderLayout.CENTER);

        // Description field
        JPanel descPanel = new JPanel(new BorderLayout(0, 5));
        descPanel.setOpaque(false);
        descPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel descLabel = new JLabel("Mô tả:");
        descLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));

        moTaVaiTroArea = new JTextArea();
        moTaVaiTroArea.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));
        moTaVaiTroArea.setLineWrap(true);
        moTaVaiTroArea.setWrapStyleWord(true);
        moTaVaiTroArea.setBorder(new LineBorder(new Color(229, 231, 235), 1));

        JScrollPane descScrollPane = new JScrollPane(moTaVaiTroArea);
        descScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));

        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(descScrollPane, BorderLayout.CENTER);

        // Permissions section
        JPanel permissionsPanel = new JPanel(new BorderLayout(0, 10));
        permissionsPanel.setOpaque(false);

        JLabel permissionsLabel = new JLabel("Danh sách quyền trong vai trò:");
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

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 2 && row >= 0 && row < danhSachQuyen.size()) {
                Boolean isSelected = (Boolean) model.getValueAt(row, column);
                danhSachQuyen.get(row).setIsChecked(isSelected);
            }
        });

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
        if (maVaiTro.isEmpty() || maVaiTro == null) {
            danhSachQuyen = quyenBUS.layDanhSachQuyen();
            for (Quyen quyen : danhSachQuyen) {
                quyenChiTietModel.addRow(new Object[] {
                    quyen.getMaQuyen(),
                    quyen.getTenQuyen(),
                    false
                });
            }
        } else {
            danhSachQuyen = vaiTroBUS.layDanhSachQuyenTheoVaiTro(Integer.parseInt(maVaiTro));
            for (Quyen quyen : danhSachQuyen) {
                quyenChiTietModel.addRow(new Object[] {
                    quyen.getMaQuyen(),
                    quyen.getTenQuyen(),
                    quyen.isChecked()
                }); 
            }
        }
    }

    private void saveGroup() {
        // Validate input fields
        if (tenVaiTroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên vai trò", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // In a real application, you would validate all fields and save to database

        // Save the permissions
        StringBuilder permissions = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) quyenChiTietTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean selected = (boolean) model.getValueAt(i, 2);
            if (selected) {
                String maQuyen = model.getValueAt(i, 0).toString();
                permissions.append(maQuyen).append(", ");
            }
        }

        String maVaiTro = maVaiTroField.getText();
        String tenVaiTro = tenVaiTroField.getText();
        String moTa = moTaVaiTroArea.getText();

        vaiTro = new VaiTro(
                maVaiTro.isEmpty() ? 0 : Integer.parseInt(maVaiTro),
                tenVaiTro,
                moTa
        );

        System.out.println(vaiTro.getMaVaiTro());
        vaiTro = vaiTroBUS.themHoacSuaVaiTro(vaiTro);
        System.out.println(vaiTro.getMaVaiTro());
        quyenBUS.capNhatQuyenVaoVaiTro(vaiTro.getMaVaiTro(), danhSachQuyen);

        JOptionPane.showMessageDialog(
                this,
                "Đã lưu thay đổi cho vai trò: " + tenVaiTroField.getText(),
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
        return tenVaiTroField.getText();
    }

    public String getMoTa() {
        return moTaVaiTroArea.getText();
    }
}

