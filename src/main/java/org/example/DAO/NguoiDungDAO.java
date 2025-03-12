package org.example.DAO;

import org.example.DTO.NguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new NguoiDung(
                        rs.getInt("MaNguoiDung"),
                        rs.getInt("MaVaiTro"),
                        rs.getString("AvatarURL"),
                        rs.getString("HoTen"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("Email"),
                        rs.getBoolean("ConHoatDong"),
                        rs.getString("TenDangNhap")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NguoiDung> layDanhSachTatCaNguoiDung() {
        List<NguoiDung> danhSachNguoiDung = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NguoiDung nguoiDung = new NguoiDung();
                nguoiDung.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                nguoiDung.setMaVaiTro(rs.getInt("MaVaiTro"));
                nguoiDung.setAvatarURL(rs.getString("AvatarURL"));
                nguoiDung.setHoTen(rs.getString("HoTen"));
                nguoiDung.setNgaySinh(rs.getDate("NgaySinh"));
                nguoiDung.setGioiTinh(rs.getString("GioiTinh"));
                nguoiDung.setDiaChi(rs.getString("DiaChi"));
                nguoiDung.setEmail(rs.getString("Email"));
                nguoiDung.setConHoatDong(rs.getBoolean("ConHoatDong"));

                danhSachNguoiDung.add(nguoiDung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNguoiDung;
    }

    public boolean themNguoiDung(NguoiDung nguoiDung) {
        String sql = "INSERT INTO NguoiDung (MaVaiTro, AvatarURL, HoTen, NgaySinh, GioiTinh, DiaChi, Email, ConHoatDong) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nguoiDung.getMaVaiTro());
            pstmt.setString(2, nguoiDung.getAvatarURL());
            pstmt.setString(3, nguoiDung.getHoTen());
            pstmt.setDate(4, new java.sql.Date(nguoiDung.getNgaySinh().getTime()));
            pstmt.setString(5, nguoiDung.getGioiTinh());
            pstmt.setString(6, nguoiDung.getDiaChi());
            pstmt.setString(7, nguoiDung.getEmail());
            pstmt.setBoolean(8, nguoiDung.isConHoatDong());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNguoiDung(int maNguoiDung) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maNguoiDung);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
