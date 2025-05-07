package org.example.DAO;

import org.example.DTO.SanPhamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public int layMaSanPhamTiepTheo() {
        String sql = "SELECT MAX(MaSanPham) AS MaxMaSanPham FROM sanpham";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxMaSanPham = rs.getInt("MaxMaSanPham");
                return maxMaSanPham + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi khi lấy mã sản phẩm tiếp theo: " + e.getMessage());
            return -1;
        }
    }

    public List<SanPhamDTO> layDanhSachTatCaSanPham() {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
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
                sanPham.setGiaVon(rs.getInt("GiaVon"));
                int giaVon = rs.getInt("GiaVon");
                int giaLoi = rs.getInt("GiaLoi");
                sanPham.setGiaLoi(giaVon > 0 ? giaVon + giaLoi : 0);
                danhSachSanPham.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }

    public List<SanPhamDTO> layDanhSachTatCaSanPhamHoatDong() {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        String sql = "SELECT sp.MaSanPham, lsp.TenLoaiSanPham, sp.AnhSanPhamURL, sp.TenSanPham, "
                + "sp.NhaSanXuat, sp.SoLuong, sp.GiaVon, sp.GiaLoi, sp.TrangThai, sp.sanphamcol "
                + "FROM sanpham sp "
                + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham "
                + "WHERE sp.TrangThai = 1";

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
                sanPham.setGiaVon(rs.getInt("GiaVon"));
                int giaVon = rs.getInt("GiaVon");
                int giaLoi = rs.getInt("GiaLoi");
                sanPham.setGiaLoi(giaVon > 0 ? giaVon + giaLoi : 0);
                danhSachSanPham.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }

    public List<SanPhamDTO> layDanhSachTatCaSanPhamKhongHoatDong() {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        String sql = "SELECT sp.MaSanPham, lsp.TenLoaiSanPham, sp.AnhSanPhamURL, sp.TenSanPham, "
                + "sp.NhaSanXuat, sp.SoLuong, sp.GiaVon, sp.GiaLoi, sp.TrangThai, sp.sanphamcol "
                + "FROM sanpham sp "
                + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham "
                + "WHERE sp.TrangThai = 0";

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
                sanPham.setGiaVon(rs.getInt("GiaVon"));
                int giaVon = rs.getInt("GiaVon");
                int giaLoi = rs.getInt("GiaLoi");
                sanPham.setGiaLoi(giaVon > 0 ? giaVon + giaLoi : 0);
                danhSachSanPham.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }

    public SanPhamDTO laySanPhamTheoMa(int maSanPham) {
        Connection conn = DatabaseConnection.getConnection();
        SanPhamDTO sanPham = null;
        try {
            String query = "SELECT sp.*, lsp.TenLoaiSanPham "
                    + "FROM sanpham sp "
                    + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham "
                    + "WHERE sp.MaSanPham = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sanPham = new SanPhamDTO();
                sanPham.setMaSanPham(rs.getInt("MaSanPham"));
                sanPham.setMaLoaiSanPham(rs.getInt("MaLoaiSanPham"));
                sanPham.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setNhaSanXuat(rs.getString("NhaSanXuat"));
                sanPham.setSoLuong(rs.getInt("SoLuong"));
                sanPham.setGiaVon(rs.getInt("GiaVon"));
                sanPham.setGiaLoi(rs.getInt("GiaLoi"));
                sanPham.setAnhSanPhamURL(rs.getString("AnhSanPhamURL"));
                sanPham.setTrangThai(rs.getBoolean("TrangThai"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPham;
    }

    public List<SanPhamDTO> layDanhSachTimKiem(String tuKhoa) {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        String sql = "SELECT sp.MaSanPham, lsp.TenLoaiSanPham, sp.AnhSanPhamURL, sp.TenSanPham, "
                + "sp.NhaSanXuat, sp.SoLuong, sp.GiaVon, sp.GiaLoi, sp.TrangThai, sp.sanphamcol "
                + "FROM sanpham sp "
                + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham "
                + "WHERE sp.TenSanPham LIKE ? OR sp.MaSanPham LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String tuKhoaTimKiem = "%" + tuKhoa + "%";
            stmt.setString(1, tuKhoaTimKiem);
            stmt.setString(2, tuKhoaTimKiem);

            try (ResultSet rs = stmt.executeQuery()) {
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
                    sanPham.setGiaVon(rs.getInt("GiaVon"));
                    int giaVon = rs.getInt("GiaVon");
                    int giaLoi = rs.getInt("GiaLoi");
                    sanPham.setGiaLoi(giaVon > 0 ? giaVon + giaLoi : 0);
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
    public boolean them(SanPhamDTO sanPham){
        String sql = "insert into sanpham(MaSanPham, MaLoaiSanPham, AnhSanPhamURL, TenSanPham, "
                + "NhaSanXuat, SoLuong, GiaVon, GiaLoi, TrangThai) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,sanPham.getMaSanPham());
            stmt.setInt(2,sanPham.getMaLoaiSanPham());
            stmt.setString(3, sanPham.getAnhSanPhamURL());
            stmt.setString (4, sanPham.getTenSanPham());
            stmt.setString(5, sanPham.getNhaSanXuat());
            stmt.setInt(6, sanPham.getSoLuong());
            stmt.setInt(7, sanPham.getGiaVon());
            stmt.setInt(8, sanPham.getGiaLoi());
            stmt.setBoolean(9,true);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected>0;
            
        }
        catch(SQLException  e){
            return false;
        }
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
            stmt.setInt(7, sanPham.getGiaVon());
            stmt.setInt(8, sanPham.getGiaLoi());
            stmt.setBoolean(9, true);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(" Lỗi khi thêm sản phẩm: " + e.getMessage());
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
            stmt.setInt(6, sanPham.getGiaVon());
            stmt.setInt(7, sanPham.getGiaLoi());
            stmt.setBoolean(8, sanPham.getTrangThai());
            stmt.setInt(9, sanPham.getMaSanPham());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(" Lỗi khi sửa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaSanPham(int maSanPham) {
        String sql = "UPDATE sanpham SET TrangThai = ? WHERE MaSanPham = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(2, maSanPham);
            stmt.setBoolean(1, false);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(" Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean nhapSanPham(List<SanPhamDTO> listSP) {
        String updateSql = "UPDATE SanPham SET SoLuong = ?, GiaVon = ? WHERE MaSanPham = ?";
        String insertSql = "INSERT INTO SanPham (MaSanPham, TenLoaiSanPham, TenSanPham, AnhSanPhamURL, SoLuong, GiaVon, GiaLoi, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement updateStmt = conn.prepareStatement(updateSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            int totalAffected = 0;
            for (SanPhamDTO i : listSP) {
                SanPhamDTO spCu = laySanPhamTheoMa(i.getMaSanPham());
                if (spCu != null) {

                    int tongSoLuong = spCu.getSoLuong() + i.getSoLuong();
                    int GiaMoi;
                    if (tongSoLuong == 0) {
                        GiaMoi = spCu.getGiaVon();
                    } else {
                        GiaMoi = (spCu.getGiaVon() * spCu.getSoLuong() + i.getGiaVon() * i.getSoLuong()) / tongSoLuong;
                    }
                    updateStmt.setInt(1, tongSoLuong);
                    updateStmt.setInt(2, GiaMoi);
                    updateStmt.setInt(3, i.getMaSanPham());
                    totalAffected += updateStmt.executeUpdate();
                } else {

                    insertStmt.setInt(1, i.getMaSanPham());
                    insertStmt.setString(2, i.getTenLoaiSanPham());
                    insertStmt.setString(3, i.getTenSanPham());
                    insertStmt.setString(4, i.getAnhSanPhamURL());
                    insertStmt.setInt(5, i.getSoLuong());
                    insertStmt.setInt(6, i.getGiaVon());
                    insertStmt.setInt(7, i.getGiaLoi());
                    insertStmt.setBoolean(8, i.getTrangThai());
                    totalAffected += insertStmt.executeUpdate();
                }
            }
            return totalAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SanPhamDTO> layDanhSachSanPhamLocNangCao(String tenLoaiSanPham, String minGiaVon, String maxGiaVon,
            String minGiaLoi, String maxGiaLoi, String minSoLuong, String maxSoLuong, String trangThai) {
        List<SanPhamDTO> danhSachSanPham = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT sp.MaSanPham, lsp.TenLoaiSanPham, sp.AnhSanPhamURL, sp.TenSanPham, "
                + "sp.NhaSanXuat, sp.SoLuong, sp.GiaVon, sp.GiaLoi, sp.TrangThai, sp.sanphamcol "
                + "FROM sanpham sp "
                + "LEFT JOIN loaisanpham lsp ON sp.MaLoaiSanPham = lsp.MaLoaiSanPham WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (!tenLoaiSanPham.equals("Tất cả")) {
            sql.append(" AND lsp.TenLoaiSanPham = ?");
            params.add(tenLoaiSanPham);
        }

        if (!minGiaVon.isEmpty()) {
            sql.append(" AND sp.GiaVon >= ?");
            params.add(Double.parseDouble(minGiaVon));
        }
        if (!maxGiaVon.isEmpty()) {
            sql.append(" AND sp.GiaVon <= ?");
            params.add(Double.parseDouble(maxGiaVon));
        }

        if (!minGiaLoi.isEmpty()) {
            sql.append(" AND (sp.GiaVon + sp.GiaLoi) >= ?");
            params.add(Double.parseDouble(minGiaLoi));
        }
        if (!maxGiaLoi.isEmpty()) {
            sql.append(" AND (sp.GiaVon + sp.GiaLoi) <= ?");
            params.add(Double.parseDouble(maxGiaLoi));
        }

        if (!minSoLuong.isEmpty()) {
            sql.append(" AND sp.SoLuong >= ?");
            params.add(Integer.parseInt(minSoLuong));
        }
        if (!maxSoLuong.isEmpty()) {
            sql.append(" AND sp.SoLuong <= ?");
            params.add(Integer.parseInt(maxSoLuong));
        }

        if (!trangThai.equals("Tất cả")) {
            sql.append(" AND sp.TrangThai = ?");
            params.add(trangThai.equals("Đang hoạt động") ? 1 : 0);
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
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
                    sanPham.setGiaVon(rs.getInt("GiaVon"));
                    int giaVon = rs.getInt("GiaVon");
                    int giaLoi = rs.getInt("GiaLoi");
                    sanPham.setGiaLoi(giaVon > 0 ? giaVon + giaLoi : 0);
                    danhSachSanPham.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }
}
