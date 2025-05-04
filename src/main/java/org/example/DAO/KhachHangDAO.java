package org.example.DAO;

import org.example.DTO.KhachHangDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public List<KhachHangDTO> layDanhSachTatCaKhachHang() {
        List<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachKhachHang.add(khachHang);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy danh sách khách hàng: " + e.getMessage(), e);
        }
        return danhSachKhachHang;
    }

    public List<KhachHangDTO> layDanhSachTatCaKhachHangHoatDong() {
        List<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang WHERE TrangThai = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachKhachHang.add(khachHang);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy danh sách khách hàng hoạt động: " + e.getMessage(), e);
        }
        return danhSachKhachHang;
    }

    public List<KhachHangDTO> layDanhSachTatCaKhachHangKhongHoatDong() {
        List<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang WHERE TrangThai = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachKhachHang.add(khachHang);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy danh sách khách hàng không hoạt động: " + e.getMessage(), e);
        }
        return danhSachKhachHang;
    }

    public KhachHangDTO layKhachHangTheoMa(int maKhachHang) {
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang WHERE MaKhachHang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhachHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                    khachHang.setHoTen(rs.getString("HoTen"));
                    khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                    khachHang.setDiaChi(rs.getString("DiaChi"));
                    khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                    return khachHang;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy khách hàng theo mã: " + e.getMessage(), e);
        }
        return null;
    }

    public int layMaKhachHangTiepTheo() {
        String sql = "SELECT MAX(MaKhachHang) AS MaxMaKhachHang FROM khachhang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxMaKhachHang = rs.getInt("MaxMaKhachHang");
                return maxMaKhachHang + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi lấy mã khách hàng tiếp theo: " + e.getMessage(), e);
        }
    }

    public List<KhachHangDTO> layDanhSachTimKiem(String tuKhoa) {
        List<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang " +
                     "WHERE HoTen LIKE ? OR SoDienThoai LIKE ? OR MaKhachHang LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String tuKhoaTimKiem = "%" + tuKhoa + "%";
            stmt.setString(1, tuKhoaTimKiem);
            stmt.setString(2, tuKhoaTimKiem);
            stmt.setString(3, tuKhoaTimKiem);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                    khachHang.setHoTen(rs.getString("HoTen"));
                    khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                    khachHang.setDiaChi(rs.getString("DiaChi"));
                    khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                    danhSachKhachHang.add(khachHang);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi tìm kiếm khách hàng: " + e.getMessage(), e);
        }
        return danhSachKhachHang;
    }

    public boolean themKhachHang(KhachHangDTO khachHang) {
        String checkSql = "SELECT COUNT(*) FROM khachhang WHERE SoDienThoai = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, khachHang.getSoDienThoai());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Số điện thoại đã tồn tại!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra số điện thoại: " + e.getMessage(), e);
        }

        String sql = "INSERT INTO khachhang (MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, khachHang.getMaKhachHang());
            stmt.setString(2, khachHang.getHoTen());
            stmt.setString(3, khachHang.getSoDienThoai());
            stmt.setString(4, khachHang.getDiaChi());
            stmt.setBoolean(5, khachHang.getTrangThai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi thêm khách hàng: " + e.getMessage(), e);
        }
    }

    public boolean suaKhachHang(KhachHangDTO khachHang) {
        String checkSql = "SELECT COUNT(*) FROM khachhang WHERE SoDienThoai = ? AND MaKhachHang != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, khachHang.getSoDienThoai());
            checkStmt.setInt(2, khachHang.getMaKhachHang());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Số điện thoại đã được sử dụng bởi khách hàng khác!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra số điện thoại: " + e.getMessage(), e);
        }

        String sql = "UPDATE khachhang SET HoTen = ?, SoDienThoai = ?, DiaChi = ?, TrangThai = ? " +
                     "WHERE MaKhachHang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, khachHang.getHoTen());
            stmt.setString(2, khachHang.getSoDienThoai());
            stmt.setString(3, khachHang.getDiaChi());
            stmt.setBoolean(4, khachHang.getTrangThai());
            stmt.setInt(5, khachHang.getMaKhachHang());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi sửa khách hàng: " + e.getMessage(), e);
        }
    }

    public boolean xoaKhachHang(int maKhachHang) {
        String sql = "UPDATE khachhang SET TrangThai = ? WHERE MaKhachHang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, false);
            stmt.setInt(2, maKhachHang);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi SQL khi xóa khách hàng: " + e.getMessage(), e);
        }
    }
}