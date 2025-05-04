package org.example.GUI.Panels.NhapKho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.example.BUS.NhaCungCapBUS;
import org.example.DTO.NhaCungCapDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

/**
 *
 * @author MTeumb
 */
public class NhaCungCap extends javax.swing.JDialog {

    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    // UI Components
    private JTextField tenNhaCungCapField;
    private JTextField diaChiField;
    private JTextField soDienThoaiField;
    private JTextField faxField;
    private JTextField searchField;
    private JTable supplierTable;
    private StyledButton addButton;
    private StyledButton editButton;
    private StyledButton deleteButton;

    /**
     * Creates new form NhaCungCap
     */
    public NhaCungCap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadNhaCungCap();
    }

    public void loadNhaCungCap() {
        List<NhaCungCapDTO> listNCC = nccBUS.layTatCaNhaCungCap();
        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        model.setRowCount(0);

        for (NhaCungCapDTO i : listNCC) {
            if (i.getTrangThai() == 1) {
                Object[] row = new Object[]{
                        i.getMaNhaCungCap(),
                        i.getTenNhaCungCap(),
                        i.getDiaChi(),
                        i.getSoDienThoai(),
                        i.getFax()
                };
                model.addRow(row);
            }
        }
        supplierTable.setModel(model);
    }

    public void loadKetQuaTimKiem(String input) {
        List<NhaCungCapDTO> listNCC = nccBUS.timKiemNCC(input);
        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        model.setRowCount(0);

        for (NhaCungCapDTO i : listNCC) {
            if (i.getMaNhaCungCap() != 0) {
                Object[] row = new Object[]{
                        i.getMaNhaCungCap(),
                        i.getTenNhaCungCap(),
                        i.getDiaChi(),
                        i.getSoDienThoai(),
                        i.getFax()
                };
                model.addRow(row);
            } else {
                return;
            }
        }
        supplierTable.setModel(model);
    }

    private void initComponents() {
        setTitle("Quản lý nhà cung cấp");
        setSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Form panel
        JPanel formPanel = createFormPanel();

        // Table panel
        JPanel tablePanel = createTablePanel();

        // Search and action panel
        JPanel actionPanel = createActionPanel();

        // Add components to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                "Thông tin nhà cung cấp",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14),
                new Color(107, 114, 128)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // Tên nhà cung cấp
        JLabel tenLabel = new JLabel("Tên nhà cung cấp:");
        tenLabel.setFont(AppConstants.NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        panel.add(tenLabel, gbc);

        tenNhaCungCapField = new JTextField();
        tenNhaCungCapField.setFont(AppConstants.NORMAL_FONT);
        tenNhaCungCapField.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        panel.add(tenNhaCungCapField, gbc);

        // Địa chỉ
        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        diaChiLabel.setFont(AppConstants.NORMAL_FONT);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        panel.add(diaChiLabel, gbc);

        diaChiField = new JTextField();
        diaChiField.setFont(AppConstants.NORMAL_FONT);
        diaChiField.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        panel.add(diaChiField, gbc);

        // Số điện thoại
        JLabel sdtLabel = new JLabel("Số điện thoại:");
        sdtLabel.setFont(AppConstants.NORMAL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.15;
        panel.add(sdtLabel, gbc);

        soDienThoaiField = new JTextField();
        soDienThoaiField.setFont(AppConstants.NORMAL_FONT);
        soDienThoaiField.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.35;
        panel.add(soDienThoaiField, gbc);

        // Fax
        JLabel faxLabel = new JLabel("Fax:");
        faxLabel.setFont(AppConstants.NORMAL_FONT);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.15;
        panel.add(faxLabel, gbc);

        faxField = new JTextField();
        faxField.setFont(AppConstants.NORMAL_FONT);
        faxField.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.35;
        panel.add(faxField, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                "Danh sách nhà cung cấp",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14),
                new Color(107, 114, 128)
        ));

        // Create table
        String[] columns = {"Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Fax"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        supplierTable = new JTable(model);
        supplierTable.setRowHeight(35);
        supplierTable.setShowVerticalLines(false);
        supplierTable.setGridColor(new Color(229, 231, 235));
        supplierTable.setSelectionBackground(new Color(243, 244, 246));
        supplierTable.setSelectionForeground(new Color(17, 24, 39));
        supplierTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Style the table header
        JTableHeader header = supplierTable.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        // Center align ID column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        supplierTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Set column widths
        supplierTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        supplierTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        supplierTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        supplierTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        supplierTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        // Add selection listener
        supplierTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRowSelected();
            }
        });

        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(AppConstants.NORMAL_FONT);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Nhập tên nhà cung cấp...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setFont(AppConstants.NORMAL_FONT);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        deleteButton = new StyledButton("Xóa", new Color(239, 68, 68), 100, 35);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton = new StyledButton("Sửa", new Color(59, 130, 246), 100, 35);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        addButton = new StyledButton("Thêm", AppConstants.PRIMARY_COLOR, 100, 35);
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });

        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);

        panel.add(searchPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void tableRowSelected() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow >= 0) {
            String ten = String.valueOf(supplierTable.getValueAt(selectedRow, 1));
            String diaChi = String.valueOf(supplierTable.getValueAt(selectedRow, 2));
            String sdt = String.valueOf(supplierTable.getValueAt(selectedRow, 3));
            String fax = String.valueOf(supplierTable.getValueAt(selectedRow, 4));

            tenNhaCungCapField.setText(ten);
            diaChiField.setText(diaChi);
            soDienThoaiField.setText(sdt);
            faxField.setText(fax);
        }
    }

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String tukhoa = searchField.getText();

        if (tukhoa.isEmpty()) {
            loadNhaCungCap();
        } else {
            loadKetQuaTimKiem(tukhoa);
        }
    }

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {
        NhaCungCapDTO nccDTO = new NhaCungCapDTO();

        // Validate input
        if (tenNhaCungCapField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tenNhaCungCapField.requestFocus();
            return;
        }

        nccDTO.setTenNhaCungCap(tenNhaCungCapField.getText().trim());
        nccDTO.setDiaChi(diaChiField.getText().trim());
        nccDTO.setSoDienThoai(soDienThoaiField.getText().trim());
        nccDTO.setFax(faxField.getText().trim());

        String message = nccBUS.themNhaCungCap(nccDTO);
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Clear fields after adding
        clearFields();
        loadNhaCungCap();
    }

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow != -1) {
            // Validate input
            if (tenNhaCungCapField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà cung cấp", "Lỗi", JOptionPane.ERROR_MESSAGE);
                tenNhaCungCapField.requestFocus();
                return;
            }

            int maNCC = (int) supplierTable.getValueAt(selectedRow, 0);
            String ten = tenNhaCungCapField.getText().trim();
            String diaChi = diaChiField.getText().trim();
            String sdt = soDienThoaiField.getText().trim();
            String fax = faxField.getText().trim();

            NhaCungCapDTO ncc = new NhaCungCapDTO();
            ncc.setMaNhaCungCap(maNCC);
            ncc.setTenNhaCungCap(ten);
            ncc.setDiaChi(diaChi);
            ncc.setSoDienThoai(sdt);
            ncc.setFax(fax);

            String message = nccBUS.suaNhaCungCap(ncc);
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after editing
            clearFields();
            loadNhaCungCap();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow != -1) {
            int maNCC = (int) supplierTable.getValueAt(selectedRow, 0);
            String tenNCC = (String) supplierTable.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa nhà cung cấp \"" + tenNCC + "\"?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String message = nccBUS.xoaNhaCungCap(maNCC);
                JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields after deleting
                clearFields();
                loadNhaCungCap();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        tenNhaCungCapField.setText("");
        diaChiField.setText("");
        soDienThoaiField.setText("");
        faxField.setText("");
        tenNhaCungCapField.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NhaCungCap dialog = new NhaCungCap(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;

}