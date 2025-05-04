package org.example.GUI.Panels.ThongKe;

import org.example.BUS.NguoiDungBUS;
import org.example.BUS.SanPhamBUS;
import org.example.BUS.KhachHangBUS;
import org.example.DTO.SanPhamDTO;
import org.example.GUI.Components.StatisticCard;
import org.example.GUI.Constants.AppConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class ThongKePanel extends JPanel {
    private final SanPhamBUS sanPhamBUS;
    private final KhachHangBUS khachHangBUS;
    private final NguoiDungBUS nguoiDungBUS;
    private JPanel statsPanel;
    private JPanel chartsPanel;

    public ThongKePanel() {
        sanPhamBUS = new SanPhamBUS();
        khachHangBUS = new KhachHangBUS();
        nguoiDungBUS = new NguoiDungBUS();
        setLayout(new BorderLayout(20, 20));
        setBackground(AppConstants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initializePanel();
    }

    private void initializePanel() {
        // Header
        JPanel headerPanel = createHeaderPanel();

        // Statistics Cards
        statsPanel = createStatsPanel();

        // Charts
        chartsPanel = createChartsPanel();

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setOpaque(false);
        mainContent.add(statsPanel, BorderLayout.NORTH);
        mainContent.add(chartsPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    public void refreshData() {
        // Remove existing stats and charts panels
        removeAll();

        // Reinitialize the panel
        initializePanel();

        // Revalidate and repaint to update the UI
        revalidate();
        repaint();
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

        // Tổng sản phẩm (tổng SoLuong của tất cả sản phẩm)
        int totalQuantity = sanPhamBUS.layDanhSachTatCaSanPham().stream()
                .mapToInt(SanPhamDTO::getSoLuong)
                .sum();
        statsPanel.add(new StatisticCard("Tổng sản phẩm", String.format("%,d", totalQuantity), "+12.5%", true));

        // Tổng khách hàng (đếm số khách hàng từ KhachHangBUS)
        int totalCustomers = khachHangBUS.layDanhSachTatCaKhachHang().size();
        statsPanel.add(new StatisticCard("Khách hàng", String.format("%,d", totalCustomers), "+5.2%", true));

        // Giữ nguyên các thẻ giả lập
        statsPanel.add(new StatisticCard("Nhân viên", String.format("%,d", nguoiDungBUS.soLuongNguoiDungConHoatDong()), "-2.3%", false));
        statsPanel.add(new StatisticCard("Doanh thu tháng", "23 000 000", "+1.5%", true));

        return statsPanel;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);

        // Biểu đồ thống kê mượn sách (giữ nguyên dữ liệu giả lập)
        JFreeChart borrowChart = createBorrowChart();
        ChartPanel borrowChartPanel = new ChartPanel(borrowChart);
        borrowChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Biểu đồ thống kê theo sản phẩm (dữ liệu thực tế từ SanPhamBUS)
        JFreeChart categoryChart = createCategoryChart();
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        categoryChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Wrap charts in white panels
        JPanel borrowPanel = wrapChartInPanel(borrowChartPanel, "Thống kê sản phẩm bán");
        JPanel categoryPanel = wrapChartInPanel(categoryChartPanel, "Thống kê theo sản phẩm");

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
        dataset.addValue(200, "Bán", "T1");
        dataset.addValue(150, "Bán", "T2");
        dataset.addValue(180, "Bán", "T3");
        dataset.addValue(270, "Bán", "T4");
        dataset.addValue(230, "Bán", "T5");
        dataset.addValue(180, "Bán", "T6");

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

        // Lấy danh sách sản phẩm và sắp xếp theo số lượng tăng dần
        List<SanPhamDTO> products = sanPhamBUS.layDanhSachTatCaSanPham();
        products.stream()
                .sorted(Comparator.comparingInt(SanPhamDTO::getSoLuong))
                .forEach(sp -> {
                    String label = sp.getTenSanPham() != null ? sp.getTenSanPham() : "SP" + sp.getMaSanPham();
                    dataset.addValue(sp.getSoLuong(), "Số lượng", label);
                });

        JFreeChart chart = ChartFactory.createBarChart(
                null,
                "Sản phẩm",
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