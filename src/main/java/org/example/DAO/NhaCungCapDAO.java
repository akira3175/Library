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
                i.setSoDienThoai(rs.getString("SoDienThoai"));
                i.setFax(rs.getString("Fax"));
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
                i.setSoDienThoai(rs.getString("SoDienThoai"));
                i.setFax(rs.getString("Fax"));
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
            stmt.setString(3, ncc.getSoDienThoai());
            stmt.setString(4, ncc.getFax());
            stmt.setInt(5, 1);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaNhaCungCap(NhaCungCapDTO ncc) {
        String sql = "update NhaCungCap set TenNhaCungCap = ?, DiaChi = ?, SoDienThoai = ?, Fax = ? "
                + "where MaNhaCungCap = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ncc.getTenNhaCungCap());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setString(3, ncc.getSoDienThoai());
            stmt.setString(4, ncc.getFax());
            stmt.setInt(5, ncc.getMaNhaCungCap());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaNhaCungCap(int maNCC) {
        String sql = "update NhaCungCap set TrangThai = ? "
                + "where MaNhaCungCap = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 0);
            stmt.setInt(2, maNCC);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<NhaCungCapDTO> timKiemNCC(String input) {
        List<NhaCungCapDTO> listNCC = new ArrayList<>();
        String tukhoa = "%" + input + "%";
        String sql = "select * from NhaCungCap "
                + "where MaNhaCungCap like ? or TenNhaCungCap like ? ";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tukhoa);
            stmt.setString(2, tukhoa);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setMaNhaCungCap(rs.getInt("MaNhaCungCap"));
                    ncc.setTenNhaCungCap(rs.getString("TenNhaCungcap"));
                    ncc.setDiaChi(rs.getString("DiaChi"));
                    ncc.setSoDienThoai(rs.getString("SoDienThoai"));
                    ncc.setFax(rs.getString("Fax"));
                    ncc.setTrangThai(rs.getInt("TrangThai"));

                    listNCC.add(ncc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNCC;
    }
}
