package org.example.GUI.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BooleanRenderer extends DefaultTableCellRenderer {
    private static final Color ACTIVE_COLOR = new Color(0, 200, 0);    // Màu xanh khi hoạt động
    private static final Color INACTIVE_COLOR = new Color(200, 0, 0);  // Màu đỏ khi không hoạt động

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Gọi super để áp dụng các thuộc tính mặc định của cell, bao gồm cả màu nền khi chọn
        JLabel defaultLabel = (JLabel) super.getTableCellRendererComponent(
                table, "", isSelected, hasFocus, row, column);

        // Tạo một CirclePanel để chứa cả hai: nền mặc định và hình tròn bên trong
        CirclePanel panel = new CirclePanel(defaultLabel.getBackground());

        // Đặt kích thước và căn chỉnh cho panel
        panel.setLayout(new GridBagLayout()); // Để căn giữa nội dung

        if (value instanceof Boolean) {
            boolean isActive = (Boolean) value;

            if (isActive) {
                panel.setCircleText("✓");
                panel.setCircleColor(ACTIVE_COLOR);
                panel.setCircleTextColor(Color.WHITE);
            } else {
                panel.setCircleText("✘");
                panel.setCircleColor(INACTIVE_COLOR);
                panel.setCircleTextColor(Color.WHITE);
            }
        }

        return panel;
    }
}