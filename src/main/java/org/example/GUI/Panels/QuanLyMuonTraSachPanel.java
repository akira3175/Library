package org.example.GUI.Panels;

import org.example.GUI.Constants.AppConstants;
import org.example.GUI.Components.StatisticCard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyMuonTraSachPanel extends JPanel {
    private JTable table;
    private JTextField searchField;

    public QuanLyMuonTraSachPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 20));
        headerPanel.setOpaque(false);

        // Title and Actions
        JPanel topPanel = new JPanel(new BorderLayout(20, 0));
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Quản lý mượn trả");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm...");

        JButton addButton = new JButton("Thêm phiếu mượn");
        addButton.setBackground(AppConstants.PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        actionPanel.add(searchField);
        actionPanel.add(addButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(actionPanel, BorderLayout.EAST);

        // Statistics Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(new StatisticCard("Đang mượn", "145", "23 phiếu mới", true));
        statsPanel.add(new StatisticCard("Đã trả", "892", "12 phiếu mới", true));
        statsPanel.add(new StatisticCard("Quá hạn", "23", "+5 phiếu", false));
        statsPanel.add(new StatisticCard("Tổng phiếu", "1060", "+35 tháng này", true));

        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(statsPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createMainContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        String[] columns = {"Mã phiếu", "Độc giả", "Sách", "Ngày mượn", "Hạn trả", "Trạng thái"};
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