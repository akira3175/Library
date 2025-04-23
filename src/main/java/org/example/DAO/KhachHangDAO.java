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
            System.out.println("Lỗi SQL khi lấy danh sách khách hàng: " + e.getMessage());
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
            System.out.println("Lỗi SQL khi lấy danh sách khách hàng hoạt động: " + e.getMessage());
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
            System.out.println("Lỗi SQL khi lấy danh sách khách hàng không hoạt động: " + e.getMessage());
        }
        return danhSachKhachHang;
    }

    public KhachHangDTO layKhachHangTheoMa(int maKhachHang) {
        String sql = "SELECT MaKhachHang, HoTen, SoDienThoai, DiaChi, TrangThai FROM khachhang WHERE MaKhachHang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maKhachHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKhachHang(rs.getInt("MaKhachHang"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                khachHang.setDiaChi(rs.getString("DiaChi"));
                khachHang.setTrangThai(rs.getBoolean("TrangThai"));
                return khachHang;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi lấy khách hàng theo mã: " + e.getMessage());
        }
        return null;
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

            ResultSet rs = stmt.executeQuery();
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
            System.out.println("Lỗi SQL khi tìm kiếm khách hàng: " + e.getMessage());
        }
        return danhSachKhachHang;
    }

    public boolean themKhachHang(KhachHangDTO khachHang) {
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
            System.out.println("Lỗi SQL khi thêm khách hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean suaKhachHang(KhachHangDTO khachHang) {
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
            System.out.println("Lỗi SQL khi sửa khách hàng: " + e.getMessage());
            return false;
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
            System.out.println("Lỗi SQL khi xóa khách hàng: " + e.getMessage());
            return false;
        }
    }
}