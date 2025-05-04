package org.example.GUI.Panels.NhapKho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.example.BUS.NguoiDungBUS;
import org.example.BUS.NhaCungCapBUS;
import org.example.BUS.PhieuNhapBUS;
import org.example.BUS.SanPhamBUS;
import org.example.DTO.NguoiDung;
import org.example.DTO.NhaCungCapDTO;
import org.example.DTO.PhieuNhapDTO;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StyledButton;
import org.example.GUI.Constants.AppConstants;

/**
 *
 * @author MTeumb
 */
public class NhapHang extends javax.swing.JDialog {

    public List<SanPhamDTO> spDuocChonList = new ArrayList<>();
    SanPhamBUS spBUS = new SanPhamBUS();

    // UI Components
    private JSplitPane jSplitPane2;
    private JPanel jPanel1, jPanel2;
    private javax.swing.JTable jTable1, jTable2;
    private JTextField jTextField1, jTextField2, jTextField3;
    private javax.swing.JComboBox<NhaCungCapDTO> jComboBox1;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;
    private StyledButton addButton, importButton, addSupplierButton, deleteButton;

    /**
     * Creates new form NhapHang
     */
    public NhapHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadSanPham();
        loadNhaCungCap();
    }

    public void loadSanPham() {
        List<SanPhamDTO> spList = new ArrayList<>();
        spList = spBUS.layDanhSachTatCaSanPham();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (SanPhamDTO i : spList) {
            Object[] row = new Object[]{
                    i.getMaSanPham(),
                    i.getTenSanPham(),
                    i.getSoLuong(),
                    i.getGiaVon()};
            model.addRow(row);
        }
        jTable1.setModel(model);
    }

    public void loadNhaCungCap() {
        NhaCungCapBUS ncc = new NhaCungCapBUS();
        List<NhaCungCapDTO> nccList = ncc.layTatCaNhaCungCap();

        for (NhaCungCapDTO i : nccList) {
            jComboBox1.addItem(i);
        }
    }

    public void loadSanPhamDuocChon() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int tong = 0;

        model.setRowCount(0);

        for (SanPhamDTO i : spDuocChonList) {
            tong = tong + i.getGiaVon() * i.getSoLuong();
            Object[] row = new Object[]{
                    i.getMaSanPham(),
                    i.getTenSanPham(),
                    i.getGiaVon(),
                    i.getSoLuong()};
            model.addRow(row);
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedAmount = formatter.format(tong) + " VNĐ";
        jLabel3.setText(formattedAmount);

        jTable2.setModel(model);
    }

    public static boolean kiemTraSoLuong(String input) {
        return Pattern.matches("^[1-9]\\d*$", input);
    }

    public boolean kiemTraSanPham(int MaSanPham, int soLuong, int giaNhap) {
        for (SanPhamDTO i : spDuocChonList) {
            if (MaSanPham == i.getMaSanPham() && giaNhap == i.getGiaVon()) {
                int tam = i.getSoLuong();
                i.setSoLuong(tam + soLuong);
                return true;
            }
        }
        return false;
    }

    public void loadSanPhamTimKiem(String input) {
        List<SanPhamDTO> listSP = spBUS.layDanhSachSanPhamTimKiem(input);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (SanPhamDTO i : listSP) {
            model.addRow(new Object[]{
                    i.getMaSanPham(),
                    i.getTenSanPham(),
                    i.getSoLuong(),
                    i.getGiaVon(),});
        }

        jTable1.setModel(model);
    }

    public void xoaSanPhamTheoMa(int maCanXoa) {
        Iterator<SanPhamDTO> iterator = spDuocChonList.iterator();
        while (iterator.hasNext()) {
            SanPhamDTO sp = iterator.next();
            if (sp.getMaSanPham() == maCanXoa) {
                iterator.remove();
            }
        }
    }

    private void initComponents() {
        setTitle("Nhập hàng");
        setSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        setResizable(false);

        // Main split pane
        jSplitPane2 = new JSplitPane();
        jSplitPane2.setDividerLocation(500);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.setDividerSize(5);
        jSplitPane2.setBorder(null);

        // Left panel - Product selection
        jPanel1 = createLeftPanel();
        jSplitPane2.setLeftComponent(jPanel1);

        // Right panel - Selected products and checkout
        jPanel2 = createRightPanel();
        jSplitPane2.setRightComponent(jPanel2);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jSplitPane2, BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        // Header panel with search
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Danh sách sản phẩm");
        titleLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 16));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);

        jLabel4 = new JLabel("Tìm kiếm:");
        jLabel4.setFont(AppConstants.NORMAL_FONT);

        jTextField2 = new JTextField();
        jTextField2.putClientProperty("JTextField.placeholderText", "Nhập tên sản phẩm...");
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        searchPanel.add(jLabel4, BorderLayout.WEST);
        searchPanel.add(jTextField2, BorderLayout.CENTER);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table panel
        String[] columns = {"Mã SP", "Tên sản phẩm", "Tồn kho", "Giá vốn"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1 = new javax.swing.JTable(model);
        jTable1.setRowHeight(35);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(new Color(229, 231, 235));
        jTable1.setSelectionBackground(new Color(243, 244, 246));
        jTable1.setSelectionForeground(new Color(17, 24, 39));
        jTable1.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Style the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        // Set column widths
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(jTable1);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Footer panel with add controls
        JPanel footerPanel = new JPanel(new BorderLayout(10, 0));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        inputPanel.setOpaque(false);

        jLabel1 = new JLabel("Số lượng:");
        jLabel1.setFont(AppConstants.NORMAL_FONT);

        jTextField1 = new JTextField("1");
        jTextField1.setPreferredSize(new Dimension(60, 35));
        jTextField1.setFont(AppConstants.NORMAL_FONT);

        jLabel6 = new JLabel("Giá nhập:");
        jLabel6.setFont(AppConstants.NORMAL_FONT);

        jTextField3 = new JTextField();
        jTextField3.setPreferredSize(new Dimension(100, 35));
        jTextField3.setFont(AppConstants.NORMAL_FONT);

        addButton = new StyledButton("Thêm", AppConstants.PRIMARY_COLOR, 100, 35);
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        inputPanel.add(jLabel1);
        inputPanel.add(jTextField1);
        inputPanel.add(jLabel6);
        inputPanel.add(jTextField3);

        footerPanel.add(inputPanel, BorderLayout.CENTER);
        footerPanel.add(addButton, BorderLayout.EAST);

        // Add all components to the panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        // Header panel with supplier selection
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Giỏ hàng nhập");
        titleLabel.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 16));

        JPanel supplierPanel = new JPanel(new BorderLayout(0, 5));
        supplierPanel.setOpaque(false);

        JPanel supplierSelectionPanel = new JPanel(new BorderLayout(10, 0));
        supplierSelectionPanel.setOpaque(false);

        jLabel5 = new JLabel("Nhà cung cấp:");
        jLabel5.setFont(AppConstants.NORMAL_FONT);

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox1.setFont(AppConstants.NORMAL_FONT);
        jComboBox1.setPreferredSize(new Dimension(200, 35));

        supplierSelectionPanel.add(jLabel5, BorderLayout.WEST);
        supplierSelectionPanel.add(jComboBox1, BorderLayout.CENTER);

        addSupplierButton = new StyledButton("Thêm nhà cung cấp", new Color(59, 130, 246), 150, 35);
        addSupplierButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        supplierPanel.add(supplierSelectionPanel, BorderLayout.NORTH);
        supplierPanel.add(addSupplierButton, BorderLayout.SOUTH);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(supplierPanel, BorderLayout.EAST);

        // Table panel
        String[] columns = {"Mã SP", "Tên sản phẩm", "Giá nhập", "Số lượng"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable2 = new javax.swing.JTable(model);
        jTable2.setRowHeight(35);
        jTable2.setShowVerticalLines(false);
        jTable2.setGridColor(new Color(229, 231, 235));
        jTable2.setSelectionBackground(new Color(243, 244, 246));
        jTable2.setSelectionForeground(new Color(17, 24, 39));
        jTable2.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.PLAIN, 13));

        // Style the table header
        JTableHeader header = jTable2.getTableHeader();
        header.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 13));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(107, 114, 128));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        // Center align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable2.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        jTable2.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jTable2.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        // Set column widths
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(jTable2);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Footer panel with total and checkout
        JPanel footerPanel = new JPanel(new BorderLayout(10, 0));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        totalPanel.setOpaque(false);

        jLabel2 = new JLabel("Tổng tiền:");
        jLabel2.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));

        jLabel3 = new JLabel("0 VNĐ");
        jLabel3.setFont(new Font(AppConstants.NORMAL_FONT.getFamily(), Font.BOLD, 14));
        jLabel3.setForeground(new Color(220, 38, 38)); // Red color for total

        totalPanel.add(jLabel2);
        totalPanel.add(jLabel3);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);

        deleteButton = new StyledButton("Xóa", new Color(239, 68, 68), 100, 35);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        importButton = new StyledButton("Nhập hàng", AppConstants.PRIMARY_COLOR, 120, 35);
        importButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        actionPanel.add(deleteButton);
        actionPanel.add(importButton);

        footerPanel.add(totalPanel, BorderLayout.WEST);
        footerPanel.add(actionPanel, BorderLayout.EAST);

        // Add all components to the panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        // Add product to cart
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        SanPhamDTO chon = new SanPhamDTO();
        chon.setMaSanPham(Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString()));
        chon.setTenSanPham(jTable1.getValueAt(selectedRow, 1).toString());

        String soLuong = jTextField1.getText();
        String donGia = jTextField3.getText();

        if (kiemTraSoLuong(soLuong)) {
            if (kiemTraSoLuong(donGia)) {
                int giaNhap = Integer.parseInt(donGia);
                int sl = Integer.parseInt(soLuong);

                if (!kiemTraSanPham(chon.getMaSanPham(), sl, giaNhap)) {
                    SanPhamDTO i = new SanPhamDTO();

                    i.setMaSanPham(chon.getMaSanPham());
                    i.setTenSanPham(chon.getTenSanPham());
                    i.setGiaVon(giaNhap);
                    i.setSoLuong(sl);

                    spDuocChonList.add(i);
                    loadSanPhamDuocChon();
                } else {
                    loadSanPhamDuocChon();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
        // Process import
        if (spDuocChonList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một sản phẩm để nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PhieuNhapDTO pnDTO = new PhieuNhapDTO();
        PhieuNhapBUS pnBUS = new PhieuNhapBUS();

        NhaCungCapDTO nccChon = (NhaCungCapDTO) jComboBox1.getSelectedItem();
        if (nccChon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NguoiDung ndHienTai = NguoiDungBUS.getNguoiDungHienTai();

        pnDTO.setMaNguoiDung(ndHienTai.getMaNguoiDung());
        pnDTO.setMaNhaCungCap(nccChon.getMaNhaCungCap());

        String thongBao = pnBUS.themPhieuNhap(pnDTO, spDuocChonList);
        JOptionPane.showMessageDialog(this, thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        spDuocChonList.clear();
        loadSanPham();
        loadSanPhamDuocChon();
    }

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {
        // Add new supplier
        NhaCungCap dialog = new NhaCungCap(null, true);
        dialog.setVisible(true);

        // Refresh supplier list
        jComboBox1.removeAllItems();
        loadNhaCungCap();
    }

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {
        // Search products
        String timKiem = jTextField2.getText();

        if (timKiem.isEmpty()) {
            loadSanPham();
        } else {
            loadSanPhamTimKiem(timKiem);
        }
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        // Delete selected product from cart
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            int maSP = (int) jTable2.getValueAt(selectedRow, 0);
            xoaSanPhamTheoMa(maSP);
            loadSanPhamDuocChon();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
}
