package org.example.DAO;

import org.example.DTO.KhuyenMai;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class KhuyenMaiDAO {
    private static final Logger logger = LoggerFactory.getLogger(KhuyenMaiDAO.class);

    public List<KhuyenMai> layDanhSachTatCaKhuyenMai() {
        List<KhuyenMai> danhSachKhuyenMai = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKhuyenMai(rs.getInt("MaKhuyenMai"));
                khuyenMai.setTenKhuyenMai(rs.getString("TenKhuyenMai"));
                khuyenMai.setSoTienKhuyenMai(rs.getInt("SoTienKhuyenMai"));
                khuyenMai.setDieuKienHoaDon(rs.getInt("DieuKienHoaDon"));
                khuyenMai.setNgayBatDau(rs.getDate("NgayBatDau"));
                khuyenMai.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                khuyenMai.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachKhuyenMai.add(khuyenMai);
            }
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi lấy danh sách khuyến mãi: {}", e.getMessage(), e);
        }
        return danhSachKhuyenMai;
    }

    public List<KhuyenMai> layDanhSachKhuyenMaiHoatDong() {
        List<KhuyenMai> danhSachKhuyenMai = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai WHERE TrangThai = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKhuyenMai(rs.getInt("MaKhuyenMai"));
                khuyenMai.setTenKhuyenMai(rs.getString("TenKhuyenMai"));
                khuyenMai.setSoTienKhuyenMai(rs.getInt("SoTienKhuyenMai"));
                khuyenMai.setDieuKienHoaDon(rs.getInt("DieuKienHoaDon"));
                khuyenMai.setNgayBatDau(rs.getDate("NgayBatDau"));
                khuyenMai.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                khuyenMai.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachKhuyenMai.add(khuyenMai);
            }
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi lấy danh sách khuyến mãi hoạt động: {}", e.getMessage(), e);
        }
        return danhSachKhuyenMai;
    }

    public KhuyenMai themKhuyenMai(KhuyenMai khuyenMai) {
        String sql = "INSERT INTO khuyenmai (TenKhuyenMai, SoTienKhuyenMai, DieuKienHoaDon, NgayBatDau, NgayKetThuc, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, khuyenMai.getTenKhuyenMai());
            stmt.setInt(2, khuyenMai.getSoTienKhuyenMai());
            stmt.setInt(3, khuyenMai.getDieuKienHoaDon());
            stmt.setDate(4, new java.sql.Date(khuyenMai.getNgayBatDau().getTime()));
            stmt.setDate(5, new java.sql.Date(khuyenMai.getNgayKetThuc().getTime()));
            stmt.setBoolean(6, khuyenMai.getTrangThai());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        khuyenMai.setMaKhuyenMai(generatedId);
                        logger.info("Khuyến mãi đã được thêm thành công với ID: {}", generatedId);
                        return khuyenMai;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi thêm khuyến mãi: {}", e.getMessage(), e);
        }
        return null;
    }
    
    public boolean suaKhuyenMai(KhuyenMai khuyenMai) {
        String sql = "UPDATE khuyenmai SET TenKhuyenMai = ?, SoTienKhuyenMai = ?, DieuKienHoaDon = ?, NgayBatDau = ?, NgayKetThuc = ?, TrangThai = ? WHERE MaKhuyenMai = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, khuyenMai.getTenKhuyenMai());
            stmt.setInt(2, khuyenMai.getSoTienKhuyenMai());
            stmt.setInt(3, khuyenMai.getDieuKienHoaDon());
            stmt.setDate(4, new java.sql.Date(khuyenMai.getNgayBatDau().getTime()));
            stmt.setDate(5, new java.sql.Date(khuyenMai.getNgayKetThuc().getTime()));
            stmt.setBoolean(6, khuyenMai.getTrangThai());
            stmt.setInt(7, khuyenMai.getMaKhuyenMai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi sửa khuyến mãi: {}", e.getMessage(), e);
        }
        return false;
    }

    public boolean xoaKhuyenMai(int maKhuyenMai) {
        String sql = "DELETE FROM khuyenmai WHERE MaKhuyenMai = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhuyenMai);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Khuyến mãi đã được xóa thành công với ID: {}", maKhuyenMai);
                return true;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi xóa khuyến mãi: {}", e.getMessage(), e);
        }
        return false;
    }
    
    public KhuyenMai layKhuyenMaiTheoMa(int maKhuyenMai) {
        String sql = "SELECT * FROM khuyenmai WHERE MaKhuyenMai = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhuyenMai);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    KhuyenMai khuyenMai = new KhuyenMai();
                    khuyenMai.setMaKhuyenMai(rs.getInt("MaKhuyenMai"));
                    khuyenMai.setTenKhuyenMai(rs.getString("TenKhuyenMai"));
                    khuyenMai.setSoTienKhuyenMai(rs.getInt("SoTienKhuyenMai"));
                    khuyenMai.setDieuKienHoaDon(rs.getInt("DieuKienHoaDon"));
                    khuyenMai.setNgayBatDau(rs.getDate("NgayBatDau"));
                    khuyenMai.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                    khuyenMai.setTrangThai(rs.getBoolean("TrangThai"));
                    return khuyenMai;
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi SQL khi lấy khuyến mãi theo ID: {}", e.getMessage(), e);
        }
        return null;
    }
}
