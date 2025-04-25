package org.example.DAO;

import org.example.DTO.Quyen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    private static final Logger logger = LoggerFactory.getLogger(QuyenDAO.class);

    public List<Quyen> layDanhSachQuyen() {
        List<Quyen> danhSachQuyen = new ArrayList<>();
        String sql = "SELECT * FROM Quyen";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Quyen quyen = new Quyen(rs.getInt("MaQuyen"), rs.getString("TenQuyen"));
                danhSachQuyen.add(quyen);
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi lấy danh sách quyền: ", e);
        }
        return danhSachQuyen;
    }

    public Quyen layQuyenTheoMa(int maQuyen) {
        String sql = "SELECT * FROM Quyen WHERE MaQuyen = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maQuyen);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Quyen(rs.getInt("MaQuyen"), rs.getString("TenQuyen"));
            }
        } catch (SQLException e) {
            logger.error("Lỗi khi lấy quyền theo ID: ", e);
        }
        return null;
    }

    public boolean themQuyen(Quyen quyen) {
        String sql = "INSERT INTO Quyen (TenQuyen) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, quyen.getTenQuyen());
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Lỗi khi thêm quyền: ", e);
        }
        return false;
    }

    public boolean suaQuyen(Quyen quyen) {
        String sql = "UPDATE Quyen SET TenQuyen = ? WHERE MaQuyen = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, quyen.getTenQuyen());
            pstmt.setInt(2, quyen.getMaQuyen());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Lỗi khi sửa quyền: ", e);
        }
        return false;
    }
    
    public List<Quyen> layDanhSachQuyenTheoVaiTro(int maVaiTro) {
        List<Quyen> danhSachQuyen = new ArrayList<>();
        String sql = "SELECT q.*, vq.MaVaiTro FROM Quyen q " +
                     "LEFT JOIN VaiTro_Quyen vq ON q.MaQuyen = vq.MaQuyen " +
                     "AND vq.MaVaiTro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maVaiTro);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int maQuyen = rs.getInt("MaQuyen");
                String tenQuyen = rs.getString("TenQuyen");
                boolean isChecked = rs.getObject("MaVaiTro") != null;

                Quyen quyen = new Quyen(maQuyen, tenQuyen, isChecked);
                danhSachQuyen.add(quyen);
            }
            logger.info("Lấy danh sách quyền theo vai trò thành công. Số lượng: {}", danhSachQuyen.size());
        } catch (SQLException e) {
            logger.error("Lỗi khi lấy danh sách quyền theo vai trò: ", e);
        }
        return danhSachQuyen;
    }

    public boolean themQuyenVaoVaiTro(int maVaiTro, int maQuyen) {
        String sql = "INSERT INTO VaiTro_Quyen (MaVaiTro, MaQuyen) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maVaiTro);
            pstmt.setInt(2, maQuyen);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Lỗi khi thêm quyền vào vai trò: ", e);
        }
        return false;
    }

    public boolean xoaQuyenKhoiVaiTro(int maVaiTro, int maQuyen) {
        String sql = "DELETE FROM VaiTro_Quyen WHERE MaVaiTro = ? AND MaQuyen = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maVaiTro);
            pstmt.setInt(2, maQuyen);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Lỗi khi xóa quyền khỏi vai trò: ", e);
        }
        return false;
    }

    public List<Quyen> capNhatQuyenVaoVaiTro(int maVaiTro, List<Quyen> danhSachQuyen) {
        String deleteSql = "DELETE FROM VaiTro_Quyen WHERE MaVaiTro = ?";
        String insertSql = "INSERT INTO VaiTro_Quyen (MaVaiTro, MaQuyen) VALUES (?, ?)";
        Connection conn = null;
    
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
    
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, maVaiTro);
                deleteStmt.executeUpdate();
            }
    
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                for (Quyen quyen : danhSachQuyen) {
                    insertStmt.setInt(1, maVaiTro);
                    insertStmt.setInt(2, quyen.getMaQuyen());
                    insertStmt.executeUpdate();
                }
            }
    
            conn.commit();
            logger.info("Cập nhật quyền vai trò thành công");
    
        } catch (Exception e) {
            danhSachQuyen = null;
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    logger.error("Lỗi khi rollback cập nhật quyền vai trò: ", ex);
                }
            }
            logger.error("Lỗi khi cập nhật quyền vai trò: ", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Lỗi khi đóng kết nối: ", e);
                }
            }
        }
    
        return danhSachQuyen;
    }    

    public boolean coTenQuyen(String tenQuyen) {
        String sql = "SELECT COUNT(MaQuyen) FROM Quyen WHERE TenQuyen = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tenQuyen);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi khi kiểm tra tên quyền: ", e);
        }
        return false;
    }
}
