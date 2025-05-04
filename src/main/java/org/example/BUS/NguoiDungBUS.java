package org.example.BUS;

import org.example.DAO.NguoiDungDAO;
import org.example.DTO.NguoiDung;
import org.example.DTO.VaiTro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class NguoiDungBUS {
    private static NguoiDung nguoiDungHienTai;
    private static final Logger logger = LoggerFactory.getLogger(NguoiDungBUS.class);
    private static NguoiDungDAO nguoiDungDAO;
    private VaiTroBUS vaiTroBUS;

    public NguoiDungBUS() {
        this.nguoiDungDAO = new NguoiDungDAO();
        this.vaiTroBUS = new VaiTroBUS();
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
            logger.warn("Danh sách người dùng trống!");
        }

        return nguoiDungList;
    }

    public NguoiDung layNguoiDungTheoID(int maNguoiDung) {
        NguoiDung nguoiDung = nguoiDungDAO.layThongTinNguoiDungTheoID(maNguoiDung);

        if (nguoiDung == null) {
            logger.warn("Người dùng không tồn tại!");
        }

        return nguoiDung;
    }

    public NguoiDung themNguoiDung(NguoiDung nguoiDung) {
        nguoiDung.setMatKhau(gereratePassword());

        return nguoiDungDAO.themNguoiDung(nguoiDung);
    }

    public NguoiDung suaNguoiDung(NguoiDung nguoiDung) {
        return nguoiDungDAO.suaNguoiDung(nguoiDung);
    }

    public NguoiDung thoiViecHoacKichHoatLaiNguoiDung(NguoiDung nguoiDung) {
        VaiTro vaiTro = vaiTroBUS.layVaiTroTheoID(nguoiDung.getMaVaiTro());

        if (vaiTro.getTenVaiTro().equals("Quản trị viên")) {
            logger.warn("Không thể kích hoạt/tạm dừng tài khoản Quản trị viên!");
            return null;
        }

        nguoiDung.setConHoatDong(!nguoiDung.isConHoatDong());
        return nguoiDungDAO.suaNguoiDung(nguoiDung);
    }

    public NguoiDung resetMatKhau(NguoiDung nguoiDung) {
        VaiTro vaiTro = vaiTroBUS.layVaiTroTheoID(nguoiDung.getMaVaiTro());

        if (vaiTro.getTenVaiTro().equals("Quản trị viên")) {
            logger.warn("Không thể reset mật khẩu cho tài khoản Quản trị viên!");
            return null;
        }
        
        nguoiDung.setMatKhau(gereratePassword());
        return nguoiDungDAO.suaNguoiDung(nguoiDung);
    }

    private String gereratePassword() {
        return "0000";
    }

    public String datLaiMatKhau(String matKhauCu, String matKhauMoi, String nhapLaiMatKhau) {
        if (!matKhauMoi.equals(nhapLaiMatKhau)) {
            logger.warn("Mật khẩu không khớp!");
            return "Mật khẩu không khớp!";
        }

        if (matKhauCu.equals(matKhauMoi)) {
            logger.warn("Mật khẩu mới không được trùng với mật khẩu cũ!");
            return "Mật khẩu mới không được trùng với mật khẩu cũ!";
        }
        
        NguoiDung nguoiDung = nguoiDungDAO.layThongTinNguoiDungTheoID(nguoiDungHienTai.getMaNguoiDung());

        if (!nguoiDung.getMatKhau().equals(matKhauCu)) {
            logger.warn("Mật khẩu cũ không đúng!");
            return "Mật khẩu cũ không đúng!";
        }

        nguoiDung.setMatKhau(matKhauMoi);
        nguoiDungDAO.suaNguoiDung(nguoiDung);

        return "Đổi mật khẩu thành công!";
    }

    public int soLuongNguoiDungConHoatDong() {
        return nguoiDungDAO.layDanhSachTatCaNguoiDung().stream()
            .filter(NguoiDung::isConHoatDong)
            .mapToInt(NguoiDung::getMaNguoiDung)
            .sum();
    }
}