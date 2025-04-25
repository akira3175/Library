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
import org.example.DTO.ChiTietPhieuNhap;

/**
 *
 * @author MTeumb
 */
public class ChiTietPhieuNhapDAO {

    public List<ChiTietPhieuNhap> layChiTietPhieuNhap(int id) {
        List<ChiTietPhieuNhap> c = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPhieuNhap=?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                    ctpn.setMaChiTietPhieuNhap(rs.getInt(1));
                    ctpn.setMaPhieuNhap(id);
                    ctpn.setMaSanPham(rs.getInt(3));
                    ctpn.setDonGia(rs.getInt(4));
                    ctpn.setSoLuong(rs.getInt(5));
                    c.add(ctpn);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
