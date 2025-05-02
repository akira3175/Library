/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.example.DTO.PhieuNhapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author MTeumb
 */
public class PhieuNhapDAO {

    private static final Logger logger = LoggerFactory.getLogger(NguoiDungDAO.class);

    public List<PhieuNhapDTO> layTatCaPhieuNhap() {
        List<PhieuNhapDTO> p = new ArrayList<>();
        String sql = "SELECT pn.MaPhieuNhap, pn.MaNguoiDung, nd.HoTen, pn.MaNhaCungCap, ncc.TenNhaCungCap, pn.ThoiGianLap, pn.TrangThai "
                + "FROM PhieuNhap pn "
                + "JOIN NhaCungCap ncc ON pn.MaNhaCungCap = ncc.MaNhaCungCap "
                + "JOIN NguoiDung nd ON pn.MaNguoiDung = nd.MaNguoiDung ";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                phieuNhap.setMaPhieuNhap(rs.getInt("MaPhieuNhap"));
                phieuNhap.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                phieuNhap.setHoTenNguoiDung(rs.getString("HoTen"));
                phieuNhap.setMaNhaCungCap(rs.getInt("MaNhaCungCap"));
                phieuNhap.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
                phieuNhap.setThoiGianLap(rs.getDate("ThoiGianLap"));
                phieuNhap.setTrangThai(rs.getInt("TrangThai"));
                p.add(phieuNhap);
            }
        } catch (SQLException e) {
            logger.error("Lấy danh sách người dùng thất bại! Message: {}", e.getMessage(), e);
        }
        return p;
    }

    public boolean themPhieuNhap(PhieuNhapDTO pnDTO) {
        String sql = "insert into PhieuNhap(MaNguoiDung, MaNhaCungCap, ThoiGianLap) "
                + "values (?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pnDTO.getMaNguoiDung());
            stmt.setInt(2, pnDTO.getMaNhaCungCap());
            stmt.setTimestamp(3, Timestamp.valueOf(now));

            int rowInserted = stmt.executeUpdate();
            return rowInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
