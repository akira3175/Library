package org.example.DAO;

import org.example.DTO.VaiTro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VaiTroDAO {
    private static final Logger logger = LoggerFactory.getLogger(VaiTroDAO.class);

    public Optional<VaiTro> themVaiTro(VaiTro vaiTro) {
        String sql = "INSERT INTO VaiTro(TenVaiTro, MoTa) VALUES(?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, vaiTro.getTenVaiTro());
            pstmt.setString(2, vaiTro.getMoTa());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        vaiTro.setMaVaiTro(generatedId);
                        logger.info("Thêm vai trò thành công với ID: {}", generatedId);
                        return Optional.of(vaiTro);
                    }
                }
            }

            logger.warn("Thêm vai trò không thành công (affectedRows = 0).");
            return Optional.empty();

        } catch (Exception e) {
            logger.error("Lỗi khi thêm vai trò: ", e);
            return Optional.empty();
        }
    }

    public Optional<VaiTro> suaVaiTro(VaiTro vaiTro) {
        String sql = "UPDATE VaiTro SET TenVaiTro = ?, MoTa = ? WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vaiTro.getTenVaiTro());
            pstmt.setString(2, vaiTro.getMoTa());
            pstmt.setInt(3, vaiTro.getMaVaiTro());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Cập nhật vai trò ID {} thành công.", vaiTro.getMaVaiTro());
                return Optional.of(vaiTro);
            } else {
                logger.warn("Không tìm thấy vai trò để cập nhật với ID {}", vaiTro.getMaVaiTro());
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật vai trò: ", e);
            return Optional.empty();
        }
    }

    public boolean xoaVaiTro(int maVaiTro) {
        String sql = "DELETE FROM VaiTro WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maVaiTro);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Xóa vai trò ID {} thành công.", maVaiTro);
                return true;
            } else {
                logger.warn("Không tìm thấy vai trò để xóa với ID {}", maVaiTro);
                return false;
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa vai trò: ", e);
            return false;
        }
    }

    public VaiTro layVaiTroTheoID(int maVaiTro) {
        String sql = "SELECT * FROM VaiTro WHERE MaVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maVaiTro);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    VaiTro vaiTro = new VaiTro();
                    vaiTro.setMaVaiTro(rs.getInt("MaVaiTro"));
                    vaiTro.setTenVaiTro(rs.getString("TenVaiTro"));
                    vaiTro.setMoTa(rs.getString("MoTa"));
                    return vaiTro;
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi khi lấy vai trò theo ID: ", e);
        }
        return null;
    }

    public List<VaiTro> layDanhSachTatCaVaiTro() {
        List<VaiTro> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM VaiTro";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                VaiTro vaiTro = new VaiTro();
                vaiTro.setMaVaiTro(rs.getInt("MaVaiTro"));
                vaiTro.setTenVaiTro(rs.getString("TenVaiTro"));
                vaiTro.setMoTa(rs.getString("MoTa"));
                danhSach.add(vaiTro);
            }

            logger.info("Lấy danh sách vai trò thành công. Số lượng: {}", danhSach.size());
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách vai trò: ", e);
        }
        return danhSach;
    }

    public List<VaiTro> layDanhSachVaiTroVaSoLuongNguoiDung() {
        List<VaiTro> danhSach = new ArrayList<>();
        String sql = "SELECT vt.MaVaiTro, vt.TenVaiTro, vt.MoTa, COUNT(nd.MaNguoiDung) AS SoLuongNguoiDung " +
                "FROM VaiTro vt " +
                "LEFT JOIN NguoiDung nd ON vt.MaVaiTro = nd.MaVaiTro " +
                "GROUP BY vt.MaVaiTro, vt.TenVaiTro, vt.MoTa";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                VaiTro vttk = new VaiTro();
                vttk.setMaVaiTro(rs.getInt("MaVaiTro"));
                vttk.setTenVaiTro(rs.getString("TenVaiTro"));
                vttk.setMoTa(rs.getString("MoTa"));
                vttk.setSoLuongNguoiDung(rs.getInt("SoLuongNguoiDung"));

                danhSach.add(vttk);
            }

            logger.info("Lấy danh sách vai trò kèm số lượng người dùng theo quyền thành công. Số lượng: {}", danhSach.size());
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách vai trò theo quyền: ", e);
        }

        return danhSach;
    }

    public boolean coTenVaiTro(String tenVaiTro) {
        String sql = "SELECT COUNT(MaVaiTro) FROM VaiTro WHERE TenVaiTro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tenVaiTro);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra tên vai trò: ", e);
        }
        return false;
    }
}
