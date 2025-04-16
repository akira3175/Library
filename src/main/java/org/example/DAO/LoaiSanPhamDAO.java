package org.example.DAO;

import org.example.DTO.LoaiSanPhamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {

    public List<LoaiSanPhamDTO> layDanhSachTatCaLoaiSanPham() {
        List<LoaiSanPhamDTO> danhSachLoaiSanPham = new ArrayList<>();
        String sql = "SELECT MaLoaiSanPham, TenLoaiSanPham, MoTa, TrangThai FROM loaisanpham";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LoaiSanPhamDTO loai = new LoaiSanPhamDTO();
                loai.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                loai.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                loai.setMoTa(rs.getString("MoTa"));
                loai.setTrangThai(rs.getBoolean("TrangThai"));
                danhSachLoaiSanPham.add(loai);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách loại sản phẩm: " + e.getMessage());
        }
        return danhSachLoaiSanPham;
    }

    public LoaiSanPhamDTO layLoaiSanPhamTheoMa(int maLoaiSanPham) {
        LoaiSanPhamDTO loai = null;
        String sql = "SELECT MaLoaiSanPham, TenLoaiSanPham, MoTa, TrangThai FROM loaisanpham WHERE MaLoaiSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maLoaiSanPham);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    loai = new LoaiSanPhamDTO();
                    loai.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                    loai.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                    loai.setMoTa(rs.getString("MoTa"));
                    loai.setTrangThai(rs.getBoolean("TrangThai"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy loại sản phẩm theo mã: " + e.getMessage());
        }
        return loai;
    }

    public List<LoaiSanPhamDTO> layDanhSachTimKiem(String tuKhoa) {
        List<LoaiSanPhamDTO> danhSachLoaiSanPham = new ArrayList<>();
        String sql = "SELECT MaLoaiSanPham, TenLoaiSanPham, MoTa, TrangThai FROM loaisanpham "
                + "WHERE TenLoaiSanPham LIKE ? OR MaLoaiSanPham LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String tuKhoaTimKiem = "%" + tuKhoa + "%";
            stmt.setString(1, tuKhoaTimKiem);
            stmt.setString(2, tuKhoaTimKiem);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LoaiSanPhamDTO loai = new LoaiSanPhamDTO();
                    loai.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                    loai.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                    loai.setMoTa(rs.getString("MoTa"));
                    loai.setTrangThai(rs.getBoolean("TrangThai"));
                    danhSachLoaiSanPham.add(loai);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm kiếm loại sản phẩm: " + e.getMessage());
        }
        return danhSachLoaiSanPham;
    }

    public boolean themLoaiSanPham(LoaiSanPhamDTO loai) {
        String sql = "INSERT INTO loaisanpham (MaLoaiSanPham, TenLoaiSanPham, MoTa, TrangThai) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loai.getMaLoaiSanPham());
            stmt.setString(2, loai.getTenLoaiSanPham());
            stmt.setString(3, loai.getMoTa());
            stmt.setBoolean(4, true);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm loại sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean suaLoaiSanPham(LoaiSanPhamDTO loai) {
        String sql = "UPDATE loaisanpham SET TenLoaiSanPham = ?, MoTa = ?, TrangThai = ? "
                + "WHERE MaLoaiSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loai.getTenLoaiSanPham());
            stmt.setString(2, loai.getMoTa());
            stmt.setBoolean(3, loai.isTrangThai());
            stmt.setInt(4, loai.getMaLoaiSanPham());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi sửa loại sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaLoaiSanPham(int maLoaiSanPham) {
        String checkSql = "SELECT COUNT(*) FROM sanpham WHERE MaLoaiSanPham = ?";
        String deleteSql = "DELETE FROM loaisanpham WHERE MaLoaiSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, maLoaiSanPham);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("❌ Không thể xóa vì loại sản phẩm đang được sử dụng.");
                    return false;
                }
            }
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, maLoaiSanPham);
                int rowsAffected = deleteStmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa loại sản phẩm: " + e.getMessage());
            return false;
        }
    }
}
