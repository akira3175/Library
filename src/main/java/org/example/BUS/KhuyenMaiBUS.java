package org.example.BUS;

import java.util.ArrayList;
import java.util.List;
import org.example.DAO.KhuyenMaiDAO;
import org.example.DTO.KhuyenMai;

public class KhuyenMaiBUS {

    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();

    public List<KhuyenMai> layDanhSachKhuyenMai() {
        return khuyenMaiDAO.layDanhSachTatCaKhuyenMai();
    }

    public List<KhuyenMai> timKiemKhuyenMai(String input) {
        List<KhuyenMai> danhSachKhuyenMai = khuyenMaiDAO.layDanhSachTatCaKhuyenMai();
        List<KhuyenMai> ketQua = new ArrayList<>();
        for (KhuyenMai khuyenMai : danhSachKhuyenMai) {
            if (khuyenMai.getTenKhuyenMai().toLowerCase().contains(input.toLowerCase())) {
                ketQua.add(khuyenMai);
            }
        }
        return ketQua;
    }

    public boolean kiemTraTenKhuyenMaiTonTai(String tenKhuyenMai) {
        List<KhuyenMai> danhSachKhuyenMai = khuyenMaiDAO.layDanhSachTatCaKhuyenMai();
        for (KhuyenMai km : danhSachKhuyenMai) {
            if (km.getTenKhuyenMai().equalsIgnoreCase(tenKhuyenMai)) {
                return true;
            }
        }
        return false;
    }

    public List<KhuyenMai> layDanhSachKhuyenMaiHoatDong() {
        return khuyenMaiDAO.layDanhSachKhuyenMaiHoatDong();
    }

    public KhuyenMai themKhuyenMai(KhuyenMai khuyenMai) {
        if (kiemTraTenKhuyenMaiTonTai(khuyenMai.getTenKhuyenMai())) {
            return null;
        }
        return khuyenMaiDAO.themKhuyenMai(khuyenMai);
    }

    public boolean suaKhuyenMai(KhuyenMai khuyenMai) {
        return khuyenMaiDAO.suaKhuyenMai(khuyenMai);
    }

    public boolean xoaKhuyenMai(int maKhuyenMai) {
        return khuyenMaiDAO.xoaKhuyenMai(maKhuyenMai);
    }

    public KhuyenMai layKhuyenMaiTheoMa(int maKhuyenMai) {
        return khuyenMaiDAO.layKhuyenMaiTheoMa(maKhuyenMai);
    }
}
