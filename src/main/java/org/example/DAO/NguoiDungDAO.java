package org.example.DAO;

import org.example.DTO.NguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NguoiDungDAO {
    private static final Logger logger = LoggerFactory.getLogger(NguoiDungDAO.class);

    public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
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
                            rs.getString("SoDienThoai"),
                            rs.getBoolean("ConHoatDong"),
                            rs.getString("TenDangNhap"),
                            rs.getDate("NgayVaoLam"),
                            rs.getString("MatKhau")
                    );
                }
            }

        } catch (SQLException e) {
            logger.error("Lỗi SQL khi thực hiện đăng nhập: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi hệ thống khi đăng nhập", e);
        }

        return null;
    }

    public NguoiDung layThongTinNguoiDungTheoID(int maNguoiDung) {
        String sql = "SELECT * FROM NguoiDung WHERE MaNguoiDung = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            try (ResultSet rs = ps.executeQuery()) {
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
                            rs.getString("SoDienThoai"),
                            rs.getBoolean("ConHoatDong"),
                            rs.getString("TenDangNhap"),
                            rs.getDate("NgayVaoLam"),
                            rs.getString("MatKhau")
                    );
                }
            }

        } catch (SQLException e) {
            logger.error("Lỗi SQL khi lấy người dùng: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi hệ thống khi lấy người dùng", e);
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
                nguoiDung.setTenDangNhap(rs.getString("TenDangNhap"));
                nguoiDung.setMaVaiTro(rs.getInt("MaVaiTro"));
                nguoiDung.setAvatarURL(rs.getString("AvatarURL"));
                nguoiDung.setHoTen(rs.getString("HoTen"));
                nguoiDung.setNgaySinh(rs.getDate("NgaySinh"));
                nguoiDung.setGioiTinh(rs.getString("GioiTinh"));
                nguoiDung.setDiaChi(rs.getString("DiaChi"));
                nguoiDung.setEmail(rs.getString("Email"));
                nguoiDung.setConHoatDong(rs.getBoolean("ConHoatDong"));
                nguoiDung.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                nguoiDung.setSoDienThoai(rs.getString("SoDienThoai"));

                danhSachNguoiDung.add(nguoiDung);
            }


            logger.info("Lấy danh sách người dùng thành công! Số lượng: {}", danhSachNguoiDung.size());
        } catch (SQLException e) {
            logger.error("Lấy danh sách người dùng thất bại! Message: {}", e.getMessage(), e);
        }
        return danhSachNguoiDung;
    }

    public NguoiDung themNguoiDung(NguoiDung nguoiDung) {
        String sql = "INSERT INTO NguoiDung (MaVaiTro, AvatarURL, HoTen, NgaySinh, GioiTinh, DiaChi, Email, ConHoatDong, MatKhau, TenDangNhap, SoDienThoai, NgayVaoLam) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, nguoiDung.getMaVaiTro());
            pstmt.setString(2, nguoiDung.getAvatarURL());
            pstmt.setString(3, nguoiDung.getHoTen());

            if (nguoiDung.getNgaySinh() != null) {
                pstmt.setDate(4, new java.sql.Date(nguoiDung.getNgaySinh().getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setString(5, nguoiDung.getGioiTinh());
            pstmt.setString(6, nguoiDung.getDiaChi());
            pstmt.setString(7, nguoiDung.getEmail());
            pstmt.setBoolean(8, nguoiDung.isConHoatDong());
            pstmt.setString(9, nguoiDung.getMatKhau());
            pstmt.setString(10, nguoiDung.getTenDangNhap());
            pstmt.setString(11, nguoiDung.getSoDienThoai());
            if (nguoiDung.getNgayVaoLam() != null) {
                java.sql.Date ngayVaoLamSql = new java.sql.Date(nguoiDung.getNgayVaoLam().getTime());
                pstmt.setDate(12, ngayVaoLamSql);
            } else {
                pstmt.setNull(12, Types.DATE);
            }


            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        nguoiDung.setMaNguoiDung(generatedId); // Cập nhật lại đối tượng
                        logger.info("Thêm người dùng thành công với ID: {}", generatedId);
                        return nguoiDung;
                    }
                }
            }

            logger.warn("Thêm người dùng không thành công (affectedRows = 0).");
            return null;

        } catch (SQLException e) {
            logger.error("Lỗi khi thêm người dùng: {}", e.getMessage(), e);
            return null;
        }
    }

    public NguoiDung suaNguoiDung(NguoiDung nguoiDung) {
        String sql = "UPDATE NguoiDung SET " +
                "MaVaiTro = ?, AvatarURL = ?, HoTen = ?, NgaySinh = ?, GioiTinh = ?, " +
                "DiaChi = ?, Email = ?, ConHoatDong = ?, MatKhau = ?, TenDangNhap = ?, " +
                "SoDienThoai = ?, NgayVaoLam = ? " +
                "WHERE MaNguoiDung = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, nguoiDung.getMaVaiTro());
            pstmt.setString(2, nguoiDung.getAvatarURL());
            pstmt.setString(3, nguoiDung.getHoTen());

            if (nguoiDung.getNgaySinh() != null) {
                pstmt.setDate(4, new java.sql.Date(nguoiDung.getNgaySinh().getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setString(5, nguoiDung.getGioiTinh());
            pstmt.setString(6, nguoiDung.getDiaChi());
            pstmt.setString(7, nguoiDung.getEmail());
            pstmt.setBoolean(8, nguoiDung.isConHoatDong());
            pstmt.setString(9, nguoiDung.getMatKhau());
            pstmt.setString(10, nguoiDung.getTenDangNhap());
            pstmt.setString(11, nguoiDung.getSoDienThoai());

            if (nguoiDung.getNgayVaoLam() != null) {
                pstmt.setDate(12, new java.sql.Date(nguoiDung.getNgayVaoLam().getTime()));
            } else {
                pstmt.setNull(12, Types.DATE);
            }

            pstmt.setInt(13, nguoiDung.getMaNguoiDung());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Cập nhật người dùng thành công với ID: {}", nguoiDung.getMaNguoiDung());
                return nguoiDung;
            } else {
                logger.warn("Không tìm thấy người dùng để cập nhật: ID = {}", nguoiDung.getMaNguoiDung());
                return null;
            }

        } catch (SQLException e) {
            logger.error("Lỗi khi cập nhật người dùng: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean xoaNguoiDung(int maNguoiDung) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maNguoiDung);
            int affectedRows = pstmt.executeUpdate();

            logger.info("Xóa người dùng thành công!");

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Xóa người dùng thất bại! Message: {}", e.getMessage(), e);
            return false;
        }
    }


}
