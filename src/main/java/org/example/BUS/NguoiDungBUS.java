package org.example.BUS;

import org.example.DAO.NguoiDungDAO;
import org.example.DTO.NguoiDung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NguoiDungBUS {
    private static NguoiDung nguoiDungHienTai;
    private static final Logger logger = LoggerFactory.getLogger(NguoiDungBUS.class);
    private static NguoiDungDAO nguoiDungDAO;

    public NguoiDungBUS() {
        this.nguoiDungDAO = new NguoiDungDAO();
    }

    public static NguoiDung xuLyDangNhap(String tenDangNhap, String matKhau) {
        logger.info("Bắt đầu đăng nhập cho: {}", tenDangNhap);

        NguoiDung nguoiDung = nguoiDungDAO.dangNhap(tenDangNhap, matKhau);

        if (nguoiDung == null) {
            logger.warn("Đăng nhập thất bại: Sai tài khoản hoặc mật khẩu.");
            return null;
        }

        if (!nguoiDung.isConHoatDong()) {
            logger.warn("Tài khoản {} đã bị vô hiệu hóa.", tenDangNhap);
            return null;
        }

        nguoiDungHienTai = nguoiDung;

        logger.info("Đăng nhập thành công: {}", tenDangNhap);
        return nguoiDung;
    }

    public static NguoiDung getNguoiDungHienTai() {
        return nguoiDungHienTai;
    }

    public static void dangXuat() {
        nguoiDungHienTai = null;
    }

    public List<NguoiDung> danhSachNguoiDung() {
        List<NguoiDung> nguoiDungList = nguoiDungDAO.layDanhSachTatCaNguoiDung();

        if (nguoiDungList == null || nguoiDungList.isEmpty()) {
            logger.warn("⚠️ Danh sách người dùng trống!");
        }

        return nguoiDungList;
    }
}