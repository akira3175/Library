package org.example.GUI.Panels.NhanSu;

import org.example.GUI.Constants.AppConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NhanSuPanel extends JPanel {
    private JTable table;
    private JTextField searchField;

    public NhanSuPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
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

        JButton addButton = new JButton("Thêm người dùng");
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

        String[] columns = {"Mã độc giả", "Họ tên", "Email", "Số điện thoại", "Ngày đăng ký", "Trạng thái"};
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