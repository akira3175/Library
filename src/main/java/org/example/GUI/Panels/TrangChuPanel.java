package org.example.GUI.Panels;

import org.example.GUI.Components.StatisticCard;
import org.example.GUI.Constants.AppConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class TrangChuPanel extends JPanel {
    public TrangChuPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = createHeaderPanel();

        // Statistics Cards
        JPanel statsPanel = createStatsPanel();

        // Charts
        JPanel chartsPanel = createChartsPanel();

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setOpaque(false);
        mainContent.add(statsPanel, BorderLayout.NORTH);
        mainContent.add(chartsPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("Tổng quan");
        titleLabel.setFont(AppConstants.HEADER_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        header.add(titleLabel, BorderLayout.WEST);
        return header;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setOpaque(false);

        statsPanel.add(new StatisticCard("Tổng số sách", "1,234", "+12.5%", true));
        statsPanel.add(new StatisticCard("Độc giả", "856", "+5.2%", true));
        statsPanel.add(new StatisticCard("Sách đang mượn", "145", "-2.3%", false));
        statsPanel.add(new StatisticCard("Sách quá hạn", "23", "+1.5%", false));

        return statsPanel;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);

        // Biểu đồ thống kê mượn sách
        JFreeChart borrowChart = createBorrowChart();
        ChartPanel borrowChartPanel = new ChartPanel(borrowChart);
        borrowChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Biểu đồ thống kê theo thể loại
        JFreeChart categoryChart = createCategoryChart();
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        categoryChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Wrap charts in white panels
        JPanel borrowPanel = wrapChartInPanel(borrowChartPanel, "Thống kê mượn sách");
        JPanel categoryPanel = wrapChartInPanel(categoryChartPanel, "Thống kê theo thể loại");

        chartsPanel.add(borrowPanel);
        chartsPanel.add(categoryPanel);

        return chartsPanel;
    }

    private JPanel wrapChartInPanel(ChartPanel chartPanel, String title) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppConstants.TITLE_FONT);
        titleLabel.setForeground(AppConstants.TEXT_COLOR);

        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(chartPanel, BorderLayout.CENTER);

        return wrapper;
    }

    private JFreeChart createBorrowChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(200, "Mượn", "T1");
        dataset.addValue(150, "Mượn", "T2");
        dataset.addValue(180, "Mượn", "T3");
        dataset.addValue(270, "Mượn", "T4");
        dataset.addValue(230, "Mượn", "T5");
        dataset.addValue(180, "Mượn", "T6");

        JFreeChart chart = ChartFactory.createLineChart(
                null,
                "Tháng",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        customizeChart(chart);
        return chart;
    }

    private JFreeChart createCategoryChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(120, "Số lượng", "Văn học");
        dataset.addValue(80, "Số lượng", "Khoa học");
        dataset.addValue(60, "Số lượng", "Kỹ thuật");
        dataset.addValue(40, "Số lượng", "Kinh tế");
        dataset.addValue(30, "Số lượng", "Ngoại ngữ");

        JFreeChart chart = ChartFactory.createBarChart(
                null,
                "Thể loại",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        customizeChart(chart);
        return chart;
    }

    private void customizeChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
    }
}