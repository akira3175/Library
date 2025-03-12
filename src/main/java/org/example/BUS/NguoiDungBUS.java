package org.example.BUS;

import org.example.DAO.NguoiDungDAO;
import org.example.DTO.NguoiDung;

public class NguoiDungBUS {
    private NguoiDungDAO nguoiDungDAO;

    public NguoiDungBUS() {
        this.nguoiDungDAO = new NguoiDungDAO();
    }

    public NguoiDung xuLyDangNhap(String tenDangNhap, String matKhau) {
        NguoiDung nguoiDung = nguoiDungDAO.dangNhap(tenDangNhap, matKhau);
        if (nguoiDung == null) {
            System.out.println("❌ Đăng nhập thất bại: Sai tài khoản hoặc mật khẩu.");
            return null;
        }
        if (!nguoiDung.isConHoatDong()) {
            System.out.println("⚠️ Tài khoản này đã bị vô hiệu hóa.");
            return null;
        }
        return nguoiDung;
    }
}