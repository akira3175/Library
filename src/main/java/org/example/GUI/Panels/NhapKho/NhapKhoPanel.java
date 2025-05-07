package org.example.GUI.Panels.NhapKho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.example.BUS.ChiTietPhieuNhapBUS;
import org.example.GUI.Constants.AppConstants;
import org.example.BUS.PhieuNhapBUS;
import org.example.DTO.ChiTietPhieuNhapDTO;
import org.example.DTO.PhieuNhapDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Panels.SanPham.SanPhamPanel;

/**
 *
 * @author MTeumb
 */
public class NhapKhoPanel extends JPanel {

    private PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    private ChiTietPhieuNhapBUS ctpnBUS;

    private JTable jTable1;
    private JTextField searchField;
    private StyledButton importButton;
    private JLabel titleLabel;

    public NhapKhoPanel() {
        initComponents();
        ctpnBUS = new ChiTietPhieuNhapBUS();
        loadPhieuNhap();
    }

    public void loadPhieuNhap() {
        List<PhieuNhapDTO> listpn = pnBUS.layTatCaPhieuNhap();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);

        for (PhieuNhapDTO phieunhap : listpn) {
            Object[] row = new Object[]{
                    phieunhap.getMaPhieuNhap(),
                    phieunhap.getHoTenNguoiDung(),
                    phieunhap.getTenNhaCungCap(),
                    phieunhap.getThoiGianLap(),
                    phieunhap.getTrangThai() == 1 ? "Đã Thanh Toán" : "Chưa thanh toán",};
            model.addRow(row);
        }

        jTable1.setModel(model);
    }

    public void hienThiChiTietPhieuNhap(JTable table, int id) {
        List<ChiTietPhieuNhapDTO> listct = ctpnBUS.layChiTietPhieuNhap(id);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ChiTietPhieuNhapDTO ctpn : listct) {
            Object[] row = new Object[]{
                    ctpn.getTenSanPham(),
                    ctpn.getDonGia(),
                    ctpn.getSoLuong(),
                    ctpn.getDonGia() * ctpn.getSoLuong(),};
            model.addRow(row);
        }
        table.setModel(model);
    }

    public void loadKetQuaTimKiem(String tukhoa) {
        List<PhieuNhapDTO> listPN = pnBUS.timKiemPhieuNhap(tukhoa);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (PhieuNhapDTO i : listPN) {
            Object[] row = new Object[]{
                    i.getMaPhieuNhap(),
                    i.getHoTenNguoiDung(),
                    i.getTenNhaCungCap(),
                    i.getThoiGianLap(),
                    i.getTrangThai() == 1 ? "Đã Thanh Toán" : "Chưa thanh toán",};
            model.addRow(row);
        }

        jTable1.setModel(model);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        titleLabel = new JLabel("Quản lý nhập kho");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(AppConstants.NORMAL_FONT);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm phiếu nhập...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        importButton = new StyledButton("Nhập hàng", AppConstants.PRIMARY_COLOR, 120, 35);
        importButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importButtonMouseClicked(evt);
            }
        });

        actionPanel.add(searchLabel);
        actionPanel.add(searchField);
        actionPanel.add(importButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(229, 231, 235), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        String[] columns = {"Mã phiếu nhập", "Người nhập", "Nhà cung cấp", "Thời gian lập", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1 = new JTable(model);
        jTable1.setRowHeight(40);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(229, 231, 235));
        jTable1.setSelectionBackground(new Color(243, 244, 246));
        jTable1.setSelectionForeground(new Color(17, 24, 39));
        jTable1.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(120);

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String tukhoa = searchField.getText();

        if (tukhoa.isEmpty()) {
            loadPhieuNhap();
        } else {
            loadKetQuaTimKiem(tukhoa);
        }
    }

    private void importButtonMouseClicked(java.awt.event.MouseEvent evt) {
        NhapHang nhapHangDialog = new NhapHang(null, true);
        nhapHangDialog.setVisible(true);
        SanPhamPanel sp = new SanPhamPanel();

        loadPhieuNhap();
        sp.XuatSanPhamTable();
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            int id = (int) jTable1.getValueAt(selectedRow, 0);
            ChiTietPhieuNhap dialog = new ChiTietPhieuNhap(null, true);
            hienThiChiTietPhieuNhap(dialog.getTable(), id);

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
}
