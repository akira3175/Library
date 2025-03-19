package org.example.GUI.Panels.SanPham;

import org.example.GUI.Constants.AppConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SanPhamPanel extends JPanel {
    private JTable table;
    private JTextField searchField;

    public SanPhamPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Main Content
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Quản lý sách");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        // Search and Add Book Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm sách...");

        JButton addButton = new JButton("Thêm sách mới");
        addButton.setBackground(AppConstants.PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        actionPanel.add(searchField);
        actionPanel.add(addButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Table
        String[] columns = {"Mã sách", "Tên sách", "Tác giả", "Thể loại", "Số lượng", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().setFont(AppConstants.NORMAL_FONT);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }
}