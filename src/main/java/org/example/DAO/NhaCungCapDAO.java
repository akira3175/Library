/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.DAO;

import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.DTO.NhaCungCapDTO;

/**
 *
 * @author MTeumb
 */
public class NhaCungCapDAO {

    public List<NhaCungCapDTO> layTatCaNhaCungCap() {
        List<NhaCungCapDTO> ncc = new ArrayList<>();
        String sql = "select * from NhaCungCap";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                NhaCungCapDTO i = new NhaCungCapDTO();
                i.setMaNhaCungCap(rs.getInt("MaNhaCungCap"));
                i.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
                i.setDiaChi(rs.getString("DiaChi"));
                i.setSoDienThoai(rs.getInt("SoDienThoai"));
                i.setFax(rs.getInt("Fax"));
                i.setTrangThai(rs.getInt("TrangThai"));
                ncc.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ncc;
    }

    public NhaCungCapDTO layMotNhaCungCap(int maNhaCungCap) {
        NhaCungCapDTO i = null;
        String sql = "select * from NhaCungCap where NhaCungCap = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, maNhaCungCap);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                i.setMaNhaCungCap(maNhaCungCap);
                i.setTenNhaCungCap(rs.getString("TenNhaCungCap"));
                i.setDiaChi(rs.getString("DiaChi"));
                i.setSoDienThoai(rs.getInt("SoDienThoai"));
                i.setFax(rs.getInt("Fax"));
                i.setTrangThai(rs.getInt("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public boolean themNhaCungCap(NhaCungCapDTO ncc) {
        String sql = "insert into NhaCungCap(TenNhaCungCap, DiaChi, SoDienThoai, Fax, TrangThai) "
                + "values (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ncc.getTenNhaCungCap());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setInt(3, ncc.getSoDienThoai());
            stmt.setInt(4, ncc.getFax());
            stmt.setInt(5, 1);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaNhaCungCap(NhaCungCapDTO ncc) {
        String sql = "insert into NhaCungCap(TenNhaCungCap, DiaChi, SoDienThoai, Fax, TrangThai) "
                + "values (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ncc.getTenNhaCungCap());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setInt(3, ncc.getFax());
            stmt.setInt(4, ncc.getSoDienThoai());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
