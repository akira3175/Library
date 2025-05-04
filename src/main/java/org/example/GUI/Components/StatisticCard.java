package org.example.GUI.Components;

import org.example.GUI.Constants.AppConstants;

import javax.swing.*;
import java.awt.*;

public class StatisticCard extends JPanel {
    private JLabel titleLabel;
    private JLabel valueLabel;
    private JLabel changeLabel;

    public StatisticCard(String title, String value, String change, boolean isPositive) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Title
        titleLabel = new JLabel(title);
        titleLabel.setFont(AppConstants.NORMAL_FONT);
        titleLabel.setForeground(AppConstants.LIGHT_TEXT_COLOR);

        // Value
        valueLabel = new JLabel(value);
        valueLabel.setFont(AppConstants.HEADER_FONT);
        valueLabel.setForeground(AppConstants.TEXT_COLOR);

        // Change percentage
        // changeLabel = new JLabel(change);
        // changeLabel.setFont(AppConstants.NORMAL_FONT);
        // changeLabel.setForeground(isPositive ? AppConstants.SECONDARY_COLOR : Color.RED);

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(valueLabel, BorderLayout.WEST);
//        bottomPanel.add(changeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }
}