package org.example.GUI.Panels.ThongKe;

import org.example.BUS.NguoiDungBUS;
import org.example.BUS.SanPhamBUS;
import org.example.BUS.KhachHangBUS;
import org.example.BUS.BanHangBUS;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.HoaDon;
import org.example.GUI.Components.StatisticCard;
import org.example.GUI.Constants.AppConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ThongKePanel extends JPanel {
    private final SanPhamBUS sanPhamBUS;
    private final KhachHangBUS khachHangBUS;
    private final NguoiDungBUS nguoiDungBUS;
    private final BanHangBUS banHangBUS;
    private JPanel statsPanel;
    private JPanel chartsPanel;

    public ThongKePanel() {
        sanPhamBUS = new SanPhamBUS();
        khachHangBUS = new KhachHangBUS();
        nguoiDungBUS = new NguoiDungBUS();
        banHangBUS = new BanHangBUS();
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

        // Tổng nhân viên
        statsPanel.add(new StatisticCard("Nhân viên", String.format("%,d", nguoiDungBUS.soLuongNguoiDungConHoatDong()), "-2.3%", false));

        // Doanh thu tháng
        long monthlyRevenue = calculateMonthlyRevenue();
        statsPanel.add(new StatisticCard("Doanh thu tháng", String.format("%,d", monthlyRevenue), "+1.5%", true));

        return statsPanel;
    }

    private long calculateMonthlyRevenue() {
        List<HoaDon> invoices = banHangBUS.getAllHoaDon();
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        return invoices.stream()
                .filter(hd -> {
                    Calendar invoiceCal = Calendar.getInstance();
                    invoiceCal.setTime(hd.getNgayLap());
                    return invoiceCal.get(Calendar.MONTH) == currentMonth &&
                           invoiceCal.get(Calendar.YEAR) == currentYear &&
                           hd.isTrangThai(); // Only include active invoices
                })
                .mapToLong(HoaDon::getThanhTien)
                .sum();
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);

        // Biểu đồ doanh thu sản phẩm bán theo ngày
        JFreeChart dailyRevenueChart = createDailyRevenueChart();
        ChartPanel dailyRevenueChartPanel = new ChartPanel(dailyRevenueChart);
        dailyRevenueChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Biểu đồ thống kê theo sản phẩm
        JFreeChart categoryChart = createCategoryChart();
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        categoryChartPanel.setPreferredSize(new Dimension(500, AppConstants.CHART_HEIGHT));

        // Wrap charts in white panels
        JPanel dailyRevenuePanel = wrapChartInPanel(dailyRevenueChartPanel, "Doanh thu sản phẩm bán theo ngày");
        JPanel categoryPanel = wrapChartInPanel(categoryChartPanel, "Thống kê theo sản phẩm");

        chartsPanel.add(dailyRevenuePanel);
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

    private JFreeChart createDailyRevenueChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        // Get invoices and group by date for the last 7 days
        List<HoaDon> invoices = banHangBUS.getAllHoaDon();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -6); // Start from 7 days ago
        Date startDate = cal.getTime();

        // Create a map of date to revenue
        Map<String, Long> dailyRevenue = new TreeMap<>();
        for (int i = 0; i < 7; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            String dateKey = sdf.format(cal.getTime());
            dailyRevenue.put(dateKey, 0L);
        }

        for (HoaDon hd : invoices) {
            if (!hd.isTrangThai()) continue; // Skip inactive invoices
            Date invoiceDate = hd.getNgayLap();
            if (invoiceDate.after(startDate) || invoiceDate.equals(startDate)) {
                String dateKey = sdf.format(invoiceDate);
                dailyRevenue.merge(dateKey, (long) hd.getThanhTien(), Long::sum);
            }
        }

        // Add data to dataset
        dailyRevenue.forEach((date, revenue) -> {
            dataset.addValue(revenue, "Doanh thu", date);
        });

        JFreeChart chart = ChartFactory.createBarChart(
                null,
                "Ngày",
                "Doanh thu (VNĐ)",
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