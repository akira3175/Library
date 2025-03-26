package org.example.GUI.Panels.BanHang;

import org.example.GUI.Constants.AppConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BanHangPanel extends JPanel {
    private JTable productTable;
    private JTable cartTable;
    private JTextField searchField;
    private JLabel totalLabel;
    private Map<String, Integer> cartItems;
    private Object[][] originalData;

    public BanHangPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cartItems = new HashMap<>();

        originalData = new Object[][]{
                {"SP001", "Nước suối", 5000, 100},
                {"SP002", "Trà xanh", 10000, 50},
                {"SP003", "Coca Cola", 12000, 75}
        };

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Bán nước uống");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterProducts(); }
        });

        actionPanel.add(searchField);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainContent() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        JPanel productPanel = createProductTable();
        JPanel cartPanel = createCartTable();

        contentPanel.add(productPanel);
        contentPanel.add(cartPanel);

        return contentPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);

        totalLabel = new JLabel("Tổng tiền: 0 VNĐ");
        totalLabel.setFont(AppConstants.NORMAL_FONT);
        totalLabel.setForeground(AppConstants.TEXT_COLOR);

        JButton checkoutButton = new JButton("Thanh toán");
        checkoutButton.setBackground(AppConstants.PRIMARY_COLOR);
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFocusPainted(false);
        checkoutButton.addActionListener(e -> processCheckout());

        footerPanel.add(totalLabel);
        footerPanel.add(checkoutButton);

        return footerPanel;
    }

    private JPanel createProductTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        String[] columns = {"Mã SP", "Tên nước uống", "Giá", "Tồn kho", "Thêm"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        model.setDataVector(originalData.clone(), columns);

        productTable = new JTable(model);
        productTable.setRowHeight(40);
        productTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);

        // Thiết lập renderer và editor cho cột "Thêm"
        productTable.getColumn("Thêm").setCellRenderer(new ButtonRenderer("Thêm"));
        productTable.getColumn("Thêm").setCellEditor(new ButtonEditor(new JCheckBox(), this::addToCart));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCartTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        String[] cartColumns = {"Tên sản phẩm", "Số lượng", "Giá", "Xóa"};
        DefaultTableModel cartModel = new DefaultTableModel(cartColumns, 0);

        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(40);
        cartTable.getTableHeader().setFont(AppConstants.NORMAL_FONT);

        // Thiết lập renderer và editor cho cột "Xóa"
        cartTable.getColumn("Xóa").setCellRenderer(new ButtonRenderer("Xóa"));
        cartTable.getColumn("Xóa").setCellEditor(new ButtonEditor(new JCheckBox(), this::removeFromCart));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void filterProducts() {
        String searchText = searchField.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Object[] row : originalData) {
            String productName = ((String) row[1]).toLowerCase();
            if (productName.contains(searchText)) {
                model.addRow(new Object[]{row[0], row[1], row[2], row[3], "Thêm"});
            }
        }
    }

    private void addToCart(String productName) {
        int availableStock = getStockByName(productName);
        int currentQuantity = cartItems.getOrDefault(productName, 0);
        if (currentQuantity + 1 > availableStock) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cartItems.put(productName, currentQuantity + 1);
        updateCartTable();
    }

    private void removeFromCart(String productName) {
        int currentQuantity = cartItems.getOrDefault(productName, 0);
        if (currentQuantity > 1) {
            cartItems.put(productName, currentQuantity - 1);
        } else {
            cartItems.remove(productName);
        }
        updateCartTable();
    }

    private void updateCartTable() {
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0);

        int totalAmount = 0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            int price = getPriceByName(name);
            int subtotal = price * quantity;
            totalAmount += subtotal;
            cartModel.addRow(new Object[]{name, quantity, subtotal, "Xóa"});
        }

        totalLabel.setText("Tổng tiền: " + totalAmount + " VNĐ");
    }

    private int getPriceByName(String productName) {
        for (Object[] row : originalData) {
            if (((String) row[1]).equals(productName)) {
                return (int) row[2];
            }
        }
        return 0;
    }

    private int getStockByName(String productName) {
        for (Object[] row : originalData) {
            if (((String) row[1]).equals(productName)) {
                return (int) row[3];
            }
        }
        return 0;
    }

    private void processCheckout() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel productModel = (DefaultTableModel) productTable.getModel();
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < productModel.getRowCount(); i++) {
                if (productModel.getValueAt(i, 1).equals(productName)) {
                    int currentStock = (int) productModel.getValueAt(i, 3);
                    productModel.setValueAt(currentStock - quantity, i, 3);
                    break;
                }
            }
            for (Object[] row : originalData) {
                if (((String) row[1]).equals(productName)) {
                    row[3] = (int) row[3] - quantity;
                    break;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Thanh toán thành công!\n" + totalLabel.getText(), 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        cartItems.clear();
        updateCartTable();
    }

    // Renderer cho nút
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setBackground(AppConstants.PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                       boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor cho nút
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String productName;
        private final java.util.function.Consumer<String> action;

        public ButtonEditor(JCheckBox checkBox, java.util.function.Consumer<String> action) {
            this.action = action;
            button = new JButton();
            button.setBackground(AppConstants.PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                action.accept(productName);
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            productName = (String) table.getValueAt(row, column == 4 ? 1 : 0); // Lấy tên sản phẩm từ cột thích hợp
            button.setText((String) value);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}