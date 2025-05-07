package org.example.BUS;

import org.example.BUS.QuyenBUS;
import org.example.DAO.VaiTroDAO;
import org.example.DAO.QuyenDAO;
import org.example.DAO.NguoiDungDAO;
import org.example.DTO.VaiTro;
import org.example.DTO.Quyen;
import org.example.DTO.NguoiDung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class VaiTroBUS {
    private VaiTroDAO vaiTroDAO = new VaiTroDAO();
    private QuyenBUS quyenBUS = new QuyenBUS();
    private QuyenDAO quyenDAO = new QuyenDAO();
    private NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    private static final Logger logger = LoggerFactory.getLogger(VaiTro.class);

    public VaiTro themHoacSuaVaiTro(VaiTro vaiTro) {
        if (vaiTro.getMaVaiTro() == 0) {
            return themVaiTro(vaiTro);
        } else {
            return suaVaiTro(vaiTro);
        }
    }

    public VaiTro themVaiTro(VaiTro vaiTro) {
        return vaiTroDAO.themVaiTro(vaiTro)
                .orElseThrow(() -> new RuntimeException("Thêm vai trò thất bại!"));
    }

    public VaiTro suaVaiTro(VaiTro vaiTro) {
        if (vaiTro.getTenVaiTro().equals("Quản trị viên")) {
            logger.warn("Không thể sửa vai trò Quản trị viên!");
            JOptionPane.showMessageDialog(null, "Không thể sửa vai trò Quản trị viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return vaiTroDAO.suaVaiTro(vaiTro)
                .orElseThrow(() -> new RuntimeException("Sửa vai trò thất bại!"));
    }

    public boolean xoaVaiTro(int maVaiTro) {
        VaiTro vaiTro = layVaiTroTheoID(maVaiTro);
        if (vaiTro.getTenVaiTro().equals("Quản trị viên")) {
            logger.warn("Không thể xóa vai trò Quản trị viên!");
            JOptionPane.showMessageDialog(null, "Không thể xóa vai trò Quản trị viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return vaiTroDAO.xoaVaiTro(maVaiTro);
    }

    public List<VaiTro> danhSachVaitro() {
        List<VaiTro> vaiTroList = vaiTroDAO.layDanhSachTatCaVaiTro();

        if (vaiTroList == null || vaiTroList.isEmpty()) {
            vaiTroList = new ArrayList<>();
            vaiTroList.add(new VaiTro(0, "Quản trị viên", "Quản trị viên toàn quyền trên hệ thống"));
            vaiTroList.add(new VaiTro(1, "Quản lý", "Quản lý nhân sự, hệ thống"));
            vaiTroList.add(new VaiTro(2, "Nhân viên Bán hàng", "Nhân viên bán hàng "));

            for (VaiTro vaiTro : vaiTroList) {
                themVaiTro(vaiTro);
            }

            vaiTroList = vaiTroDAO.layDanhSachTatCaVaiTro();
        }

        return vaiTroList;
    }

    public VaiTro layVaiTroTheoID(int maVaiTro) {
        VaiTro vaiTro = vaiTroDAO.layVaiTroTheoID(maVaiTro);

        if(vaiTro == null) {
            logger.warn("Vai trò không tồn tại");
        }

        return vaiTro;
    }

    public List<VaiTro> danhSachVaitroKemSoLuongNguoiDung() {
        List<VaiTro> vaiTroList = vaiTroDAO.layDanhSachVaiTroVaSoLuongNguoiDung();

        if (vaiTroList == null || vaiTroList.isEmpty()) {
            logger.warn("Danh sách vai trò trống!");
        }

        return vaiTroList;
    }

    public List<VaiTro> timKiemVaiTro(String keyword) {
        List<VaiTro> vaiTroList = danhSachVaitroKemSoLuongNguoiDung();
        List<VaiTro> vaiTroListTimKiem = new ArrayList<>();

        if (vaiTroList == null || vaiTroList.isEmpty()) {
            logger.warn("Danh sách vai trò trống!");
        }

        for (VaiTro vaiTro : vaiTroList) {
            if (vaiTro.getTenVaiTro().toLowerCase().contains(keyword.toLowerCase())) {
                vaiTroListTimKiem.add(vaiTro);
            }
        }

        return vaiTroListTimKiem;
    }

    public List<Quyen> layDanhSachQuyenTheoVaiTro(int maVaiTro) {
        return quyenDAO.layDanhSachQuyenTheoVaiTro(maVaiTro);
    }   

    public boolean coTenVaiTro(String tenVaiTro) {
        return vaiTroDAO.coTenVaiTro(tenVaiTro);
    }

    public List<VaiTro> khoiTaoVaiTro() {
        List<VaiTro> danhSachVaiTro = new ArrayList<>();
        if (!coTenVaiTro("Quản trị viên")) {
            danhSachVaiTro.add(new VaiTro(0, "Quản trị viên", "Quản trị viên toàn quyền trên hệ thống"));
        }
        if (!coTenVaiTro("Quản lý")) {
            danhSachVaiTro.add(new VaiTro(1, "Quản lý", "Quản lý nhân sự, hệ thống"));
        }

        for (VaiTro vaiTro : danhSachVaiTro) {
            themVaiTro(vaiTro);
        }

        logger.info("Khởi tạo vai trò thành công");
        danhSachVaiTro = vaiTroDAO.layDanhSachTatCaVaiTro();

        boolean thanhCong = khoiTaoQuyenCuaQuanTriVien();
        if (!thanhCong) {
            logger.error("Khởi tạo quyền cho Quản trị viên thất bại");
        } else {
            logger.info("Khởi tạo quyền cho Quản trị viên thành công");
        }

        return danhSachVaiTro;
    }

    public boolean khoiTaoQuyenCuaQuanTriVien() {
        boolean thanhCong = true;
        VaiTro vaiTro = vaiTroDAO.layVaiTroTheoTen("Quản trị viên");
        List<Quyen> danhSachQuyen = quyenDAO.layDanhSachQuyenTheoVaiTro(vaiTro.getMaVaiTro());
        thanhCong = quyenBUS.capNhatQuyenVaoVaiTro(vaiTro.getMaVaiTro(), danhSachQuyen);
        return thanhCong;
    }

    public boolean ganVaiTroChoQuanTriVien() {
        NguoiDung nguoiDung = nguoiDungDAO.layNguoiDungTheoTenDangNhap("admin");
        if (nguoiDung == null) {
            nguoiDung = new NguoiDung();
            nguoiDung.setTenDangNhap("admin");
            nguoiDung.setMatKhau("0000");
            nguoiDung.setHoTen("Quản trị viên");
            nguoiDung.setNgaySinh(new Date());
            nguoiDung.setGioiTinh("Nam");
            nguoiDung.setDiaChi("123 Nguyễn Văn Cừ, Hồ Chí Minh");
            nguoiDung.setEmail("admin@gmail.com");
            nguoiDung.setSoDienThoai("0909090909");
            nguoiDung.setNgayVaoLam(new Date());
            nguoiDungDAO.themNguoiDung(nguoiDung);
        }
        nguoiDung.setMaVaiTro(vaiTroDAO.layVaiTroTheoTen("Quản trị viên").getMaVaiTro());
        return nguoiDungDAO.suaNguoiDung(nguoiDung) != null;
    }
}
