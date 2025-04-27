/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.DTO.ChiTietPhieuNhapDTO;

/**
 *
 * @author MTeumb
 */
public class ChiTietPhieuNhapDAO {

    public List<ChiTietPhieuNhapDTO> layChiTietPhieuNhap(int id) {
        List<ChiTietPhieuNhapDTO> c = new ArrayList<>();
        String sql = "SELECT ct.MaChiTietPhieuNhap, ct.MaSanPham, sp.TenSanPham, ct.DonGia, ct.SoLuong "
                + "FROM ChiTietPhieuNhap ct "
                + "JOIN SanPham sp ON sp.MaSanPham = ct.MaSanPham "
                + "WHERE ct.MaPhieuNhap = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
                    ctpn.setMaChiTietPhieuNhap(rs.getInt("MaChiTietPhieuNhap"));
                    ctpn.setMaPhieuNhap(id);
                    ctpn.setMaSanPham(rs.getInt("MaSanPham"));
                    ctpn.setTenSanPham(rs.getString("TenSanPham"));
                    ctpn.setDonGia(rs.getInt("DonGia"));
                    ctpn.setSoLuong(rs.getInt("SoLuong"));
                    c.add(ctpn);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
