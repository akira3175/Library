package org.example.GUI.Panels.KhuyenMai;

import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhuyenMaiPanel extends JPanel {
    private JTable table;
    private JTextField searchField;
    private JPanel danhSachPanel;
    private JComboBox<String> statusFilterComboBox;

    public KhuyenMaiPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));



        createHeaderPanel();
        createDanhSachPanel();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(AppConstants.PRIMARY_COLOR);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton addButton = new JButton("Thêm");
        addButton.setBackground(AppConstants.PRIMARY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        headerPanel.add(searchField, BorderLayout.CENTER);
        headerPanel.add(searchButton, BorderLayout.EAST);
        headerPanel.add(addButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createDanhSachPanel() {
        JPanel danhSachPanel = new JPanel(new BorderLayout());
        danhSachPanel.setBackground(AppConstants.BACKGROUND_COLOR);

        table = new JTable();
        table.setBackground(AppConstants.BACKGROUND_COLOR);
        table.setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(table);
        danhSachPanel.add(scrollPane, BorderLayout.CENTER);

        return danhSachPanel;
    }

    // private void xuatKhuyenMaiTable() {
    //     List<KhuyenMai> danhSachKhuyenMai = khuyenMaiBUS.layDanhSachKhuyenMai();
    //     table.setModel(new DefaultTableModel(danhSachKhuyenMai, columnNames));
    // }

    
}
