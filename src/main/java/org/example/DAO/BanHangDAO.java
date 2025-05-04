package org.example.DAO;

import org.example.DTO.ChiTietHoaDon;
import org.example.DTO.HoaDon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanHangDAO {
    private static final Logger logger = LoggerFactory.getLogger(BanHangDAO.class);

    public BanHangDAO() {
        // Initialize database connection if needed
    }

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> hoaDons = new ArrayList<>();
        String sqlHoaDon = "SELECT MaHoaDon, MaNguoiDung, MaKhachHang, MaKhuyenMai, NgayLap, TienGiam, ThanhTien, TrangThai FROM hoadon";
        String sqlChiTiet = "SELECT MaChiTietHoaDon, MaHoaDon, MaSanPham, SoLuong, DonGia FROM chitiethoadon WHERE MaHoaDon = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtHoaDon = conn.prepareStatement(sqlHoaDon);
             PreparedStatement stmtChiTiet = conn.prepareStatement(sqlChiTiet)) {

            // Fetch all invoices
            ResultSet rsHoaDon = stmtHoaDon.executeQuery();
            while (rsHoaDon.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rsHoaDon.getInt("MaHoaDon"));
                hoaDon.setMaNguoiDung(rsHoaDon.getInt("MaNguoiDung"));
                hoaDon.setMaKhachHang(rsHoaDon.getInt("MaKhachHang"));
                hoaDon.setMaKhuyenMai(rsHoaDon.getInt("MaKhuyenMai"));
                hoaDon.setNgayLap(rsHoaDon.getTimestamp("NgayLap"));
                hoaDon.setTienGiam(rsHoaDon.getInt("TienGiam"));
                hoaDon.setThanhTien(rsHoaDon.getInt("ThanhTien"));
                hoaDon.setTrangThai(rsHoaDon.getBoolean("TrangThai"));

                // Fetch details for this invoice
                stmtChiTiet.setInt(1, hoaDon.getMaHoaDon());
                ResultSet rsChiTiet = stmtChiTiet.executeQuery();
                List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
                while (rsChiTiet.next()) {
                    ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                    chiTiet.setMaChiTietHoaDon(rsChiTiet.getInt("MaChiTietHoaDon"));
                    chiTiet.setMaHoaDon(rsChiTiet.getInt("MaHoaDon"));
                    chiTiet.setMaSanPham(rsChiTiet.getInt("MaSanPham"));
                    chiTiet.setSoLuong(rsChiTiet.getInt("SoLuong"));
                    chiTiet.setDonGia(rsChiTiet.getInt("DonGia"));
                    chiTietHoaDons.add(chiTiet);
                }
                hoaDon.setChiTietHoaDons(chiTietHoaDons);

                hoaDons.add(hoaDon);
            }

            logger.info("Đã lấy {} hóa đơn từ cơ sở dữ liệu.", hoaDons.size());
            return hoaDons;

        } catch (SQLException e) {
            logger.error("Lỗi khi lấy danh sách hóa đơn: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách hóa đơn: " + e.getMessage());
        }
    }

    public HoaDon taoHoaDon(HoaDon hoaDon) {
        // Placeholder implementation; replace with actual database insert logic
        String sqlHoaDon = "INSERT INTO hoadon (MaNguoiDung, MaKhachHang, MaKhuyenMai, NgayLap, TienGiam, ThanhTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlChiTiet = "INSERT INTO chitiethoadon (MaHoaDon, MaSanPham, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtHoaDon = conn.prepareStatement(sqlHoaDon, PreparedStatement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false);

            // Insert HoaDon
            stmtHoaDon.setInt(1, hoaDon.getMaNguoiDung());
            stmtHoaDon.setInt(2, hoaDon.getMaKhachHang());
            stmtHoaDon.setInt(3, hoaDon.getMaKhuyenMai());
            stmtHoaDon.setTimestamp(4, new java.sql.Timestamp(hoaDon.getNgayLap().getTime()));
            stmtHoaDon.setInt(5, hoaDon.getTienGiam());
            stmtHoaDon.setInt(6, hoaDon.getThanhTien());
            stmtHoaDon.setBoolean(7, hoaDon.isTrangThai());
            stmtHoaDon.executeUpdate();

            // Get generated MaHoaDon
            ResultSet rs = stmtHoaDon.getGeneratedKeys();
            if (rs.next()) {
                hoaDon.setMaHoaDon(rs.getInt(1));
            }

            // Insert ChiTietHoaDon
            try (PreparedStatement stmtChiTiet = conn.prepareStatement(sqlChiTiet)) {
                for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDons()) {
                    stmtChiTiet.setInt(1, hoaDon.getMaHoaDon());
                    stmtChiTiet.setInt(2, chiTiet.getMaSanPham());
                    stmtChiTiet.setInt(3, chiTiet.getSoLuong());
                    stmtChiTiet.setInt(4, chiTiet.getDonGia());
                    stmtChiTiet.executeUpdate();
                }
            }

            conn.commit();
            logger.info("Tạo hóa đơn thành công với MaHoaDon: {}", hoaDon.getMaHoaDon());
            return hoaDon;

        } catch (SQLException e) {
            logger.error("Lỗi khi tạo hóa đơn: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể tạo hóa đơn: " + e.getMessage());
        }
    }
}