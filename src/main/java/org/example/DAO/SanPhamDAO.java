package org.example.DAO;

import org.example.DTO.SanPhamDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    // Lấy danh sách tất cả sản phẩm
    public List<SanPhamDTO> layDanhSachTatCaSanPham() {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        // **Chỗ cần thay đổi**: JOIN để lấy TenLoaiSanPham, không lấy MaLoaiSanPham trực tiếp từ sanpham
        String sql = "SELECT sp.MaSanPham, lsp.TenLoaiSanPham, sp.AnhSanPhamURL, sp.TenSanPham, "
                + "sp.NhaSanXuat, sp.SoLuong, sp.GiaVon, sp.GiaLoi, sp.TrangThai, sp.sanphamcol "
                + "FROM sanpham sp "
                + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SanPhamDTO sanPham = new SanPhamDTO();
                sanPham.setMaSanPham(rs.getInt("MaSanPham"));
                sanPham.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                sanPham.setAnhSanPhamURL(rs.getString("AnhSanPhamURL"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setNhaSanXuat(rs.getString("NhaSanXuat"));
                sanPham.setTrangThai(rs.getBoolean("TrangThai"));
                sanPham.setsanphamcol(rs.getString("sanphamcol"));
                sanPham.setSoLuong(rs.getInt("SoLuong"));
                sanPham.setGiaVon(rs.getDouble("GiaVon"));
                sanPham.setGiaLoi(rs.getDouble("GiaLoi"));

                danhSachSanPham.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }

    public List<SanPhamDTO> layDanhSachTimKiem(String tuKhoa) {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        // Sử dụng PreparedStatement để tránh SQL Injection
        String sql = "SELECT * FROM sanpham WHERE TenSanPham LIKE ? OR MaSanPham LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị từ khóa tìm kiếm với ký tự % để tìm kiếm gần đúng
            String tuKhoaTimKiem = "%" + tuKhoa + "%";
            stmt.setString(1, tuKhoaTimKiem); // Tìm theo TenSanPham
            stmt.setString(2, tuKhoaTimKiem); // Tìm theo MaSanPham

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SanPhamDTO sanPham = new SanPhamDTO();
                    sanPham.setMaSanPham(rs.getInt("MaSanPham"));
                    sanPham.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                    sanPham.setAnhSanPhamURL(rs.getString("AnhSanPhamURL"));
                    sanPham.setTenSanPham(rs.getString("TenSanPham"));
                    sanPham.setNhaSanXuat(rs.getString("NhaSanXuat"));
                    sanPham.setTrangThai(rs.getBoolean("TrangThai"));
                    sanPham.setsanphamcol(rs.getString("sanphamcol"));
                    sanPham.setSoLuong(rs.getInt("SoLuong"));
                    sanPham.setGiaVon(rs.getDouble("GiaVon"));
                    sanPham.setGiaLoi(rs.getDouble("GiaLoi"));

                    danhSachSanPham.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }

    public List<SanPhamDTO> layDanhSachLoaiSanPham() {
        List<SanPhamDTO> danhSach = new ArrayList<>();
        String sql = "SELECT MaLoaiSanPham, TenLoaiSanPham FROM loaisanpham";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SanPhamDTO loai = new SanPhamDTO();
                loai.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                loai.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                danhSach.add(loai);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi lấy danh sách loại sản phẩm: " + e.getMessage());
        }
        return danhSach;
    }

    public List<SanPhamDTO> layDanhSachNhaSanXuat() {
        List<SanPhamDTO> danhSach = new ArrayList<>();
        String sql = "SELECT MaNhaCungCap, TenNhaCungCap FROM nhacungcap";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SanPhamDTO nhaSX = new SanPhamDTO();
                nhaSX.setMaNhaCungCap(rs.getInt("MaNhaCungCap"));
                nhaSX.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
                danhSach.add(nhaSX);
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL khi lấy danh sách nhà sản xuất: " + e.getMessage());
        }
        return danhSach;
    }

    public boolean themSanPham(SanPhamDTO sanPham) {
        String sql = "INSERT INTO sanpham (MaSanPham, MaLoaiSanPham, AnhSanPhamURL, TenSanPham, "
                + "NhaSanXuat, SoLuong, GiaVon, GiaLoi, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sanPham.getMaSanPham());
            stmt.setInt(2, sanPham.getMaLoaiSanPham());
            stmt.setString(3, sanPham.getAnhSanPhamURL());
            stmt.setString(4, sanPham.getTenSanPham());
            stmt.setString(5, sanPham.getNhaSanXuat());
            stmt.setInt(6, sanPham.getSoLuong());
            stmt.setDouble(7, sanPham.getGiaVon());
            stmt.setDouble(8, sanPham.getGiaLoi());
            stmt.setBoolean(9, sanPham.getSoLuong() > 0);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean suaSanPham(SanPhamDTO sanPham) {
        String sql = "UPDATE sanpham SET MaLoaiSanPham = ?, AnhSanPhamURL = ?, TenSanPham = ?, "
                + "NhaSanXuat = ?, SoLuong = ?, GiaVon = ?, GiaLoi = ?, TrangThai = ? "
                + "WHERE MaSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sanPham.getMaLoaiSanPham());
            stmt.setString(2, sanPham.getAnhSanPhamURL());
            stmt.setString(3, sanPham.getTenSanPham());
            stmt.setString(4, sanPham.getNhaSanXuat());
            stmt.setInt(5, sanPham.getSoLuong());
            stmt.setDouble(6, sanPham.getGiaVon());
            stmt.setDouble(7, sanPham.getGiaLoi());
            stmt.setBoolean(8, sanPham.getSoLuong() > 0); // TrangThai dựa trên SoLuong
            stmt.setInt(9, sanPham.getMaSanPham());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi sửa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaSanPham(int maSanPham) {
        String sql = "DELETE FROM sanpham WHERE MaSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maSanPham);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }
}
