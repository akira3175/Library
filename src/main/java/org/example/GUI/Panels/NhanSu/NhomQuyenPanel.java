package org.example.GUI.Panels.NhanSu;

import org.example.BUS.VaiTroBUS;
import org.example.DTO.VaiTro;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;
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
import java.util.List;

public class NhomQuyenPanel extends JPanel {
    private JTable vaiTroTable;
    private JTextField searchField;
    private VaiTroBUS vaiTroBUS;

    public NhomQuyenPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(AppConstants.BACKGROUND_COLOR);
        vaiTroBUS = new VaiTroBUS();

        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(AppConstants.BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        contentPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel listPanel = createNhomQuyenListPanel();

        mainPanel.add(listPanel, BorderLayout.CENTER);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        loadSampleData(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Quản lý vai trò");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm vai trò...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                List<VaiTro> danhSachVaiTro = vaiTroBUS.timKiemVaiTro(keyword);
                loadSampleData(danhSachVaiTro);
            }
        });

        StyledButton refreshButton = new StyledButton("Làm mới", new Color(107, 114, 128), 120, 35);
        StyledButton addButton = new StyledButton("Thêm vai trò", AppConstants.PRIMARY_COLOR, 150, 35);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddGroupDialog();
            }
        });

        actionPanel.add(searchField);
        actionPanel.add(refreshButton);
        actionPanel.add(addButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createNhomQuyenListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(249, 250, 251));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Danh sách vai trò");
        titleLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        titleLabel.setForeground(new Color(17, 24, 39));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Table
        String[] columns = {"Mã nhóm", "Tên vai trò", "Số người dùng", "Mô tả"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        vaiTroTable = new JTable(model);
        vaiTroTable.setRowHeight(45);
        vaiTroTable.setShowVerticalLines(false);
        vaiTroTable.setGridColor(new Color(229, 231, 235));
        vaiTroTable.setSelectionBackground(new Color(243, 244, 246));
        vaiTroTable.setSelectionForeground(new Color(17, 24, 39));
        vaiTroTable.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        JTableHeader header = vaiTroTable.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        vaiTroTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        vaiTroTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        vaiTroTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        vaiTroTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        vaiTroTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        vaiTroTable.getColumnModel().getColumn(3).setPreferredWidth(300);

        vaiTroTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = vaiTroTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        showGroupDetailsDialog(selectedRow);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(vaiTroTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(new Color(249, 250, 251));
        actionPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        StyledButton editButton = new StyledButton("Sửa", new Color(59, 130, 246), 120, 35);
        StyledButton deleteButton = new StyledButton("Xóa", new Color(239, 68, 68), 120, 35);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = vaiTroTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showGroupDetailsDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(
                            NhomQuyenPanel.this,
                            "Vui lòng chọn một vai trò để sửa",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = vaiTroTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String maVaiTro = vaiTroTable.getValueAt(selectedRow, 0).toString();
                    String tenVaiTro = (String) vaiTroTable.getValueAt(selectedRow, 1);

                    int confirm = JOptionPane.showConfirmDialog(
                            NhomQuyenPanel.this,
                            "Bạn có chắc chắn muốn xóa vai trò " + tenVaiTro + "?",
                            "Xác nhận xóa",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean thanhCong = vaiTroBUS.xoaVaiTro(Integer.parseInt(maVaiTro));
                        if (thanhCong) {
                            DefaultTableModel model = (DefaultTableModel) vaiTroTable.getModel();
                            model.removeRow(selectedRow);

                            JOptionPane.showMessageDialog(
                                    NhomQuyenPanel.this,
                                    "Đã xóa vai trò " + tenVaiTro,
                                    "Thành công",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    NhomQuyenPanel.this,
                                    "Không thể xóa vai trò " + tenVaiTro,
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            NhomQuyenPanel.this,
                            "Vui lòng chọn một nhóm quyền để xóa",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadSampleData(List<VaiTro> danhSachVaiTro) {
        if (danhSachVaiTro == null) {
            danhSachVaiTro = vaiTroBUS.danhSachVaitroKemSoLuongNguoiDung();
        }
        DefaultTableModel nhomQuyenModel = (DefaultTableModel) vaiTroTable.getModel();
        nhomQuyenModel.setRowCount(0);

        for(VaiTro vaiTro: danhSachVaiTro) {
            nhomQuyenModel.addRow(new Object[]{vaiTro.getMaVaiTro(), vaiTro.getTenVaiTro(), vaiTro.getSoLuongNguoiDung(), vaiTro.getMoTa()});
        }
    }

    private void showGroupDetailsDialog(int row) {
        DefaultTableModel model = (DefaultTableModel) vaiTroTable.getModel();

        String maNhomQuyen = model.getValueAt(row, 0).toString();
        String tenNhomQuyen = (String) model.getValueAt(row, 1);
        String moTa = (String) model.getValueAt(row, 3);

        ChiTietNhomQuyenDialog dialog = new ChiTietNhomQuyenDialog(
                SwingUtilities.getWindowAncestor(this),
                maNhomQuyen,
                tenNhomQuyen,
                moTa
        );

        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            model.setValueAt(dialog.getTenNhomQuyen(), row, 1);
            model.setValueAt(dialog.getMoTa(), row, 3);
        }
    }

    private void showAddGroupDialog() {
        ChiTietNhomQuyenDialog dialog = new ChiTietNhomQuyenDialog(
                SwingUtilities.getWindowAncestor(this),
                "",
                "",
                ""
        );

        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            DefaultTableModel model = (DefaultTableModel) vaiTroTable.getModel();
            model.addRow(new Object[]{
                    dialog.getMaNhomQuyen(),
                    dialog.getTenNhomQuyen(),
                    0,
                    dialog.getMoTa()
            });
        }
    }
}
