package org.example.BUS;

import org.example.DAO.KhachHangDAO;
import org.example.DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class KhachHangBUS {

    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        this.khachHangDAO = new KhachHangDAO();
    }

    public void hienThiKhachHangLenTable(JTable table, String filter) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<KhachHangDTO> danhSachKhachHang;

        if ("Không hoạt động".equals(filter)) {
            danhSachKhachHang = khachHangDAO.layDanhSachTatCaKhachHangKhongHoatDong();
        } else if ("Đang hoạt động".equals(filter)) {
            danhSachKhachHang = khachHangDAO.layDanhSachTatCaKhachHangHoatDong();
        } else {
            danhSachKhachHang = khachHangDAO.layDanhSachTatCaKhachHang();
        }

        for (KhachHangDTO khachHang : danhSachKhachHang) {
            model.addRow(new Object[]{
                khachHang.getMaKhachHang(),
                khachHang.getHoTen(),
                khachHang.getSoDienThoai(),
                khachHang.getDiaChi(),
                khachHang.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public void hienThiKhachHangTimKiem(JTable table, String tuKhoa) {
        List<KhachHangDTO> danhSachKhachHang = khachHangDAO.layDanhSachTimKiem(tuKhoa);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (KhachHangDTO khachHang : danhSachKhachHang) {
            model.addRow(new Object[]{
                khachHang.getMaKhachHang(),
                khachHang.getHoTen(),
                khachHang.getSoDienThoai(),
                khachHang.getDiaChi(),
                khachHang.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public boolean themKhachHang(KhachHangDTO khachHang) {
        try {
            return khachHangDAO.themKhachHang(khachHang);
        } catch (RuntimeException e) {
            throw e;
        }
    }
    
    public int layMaKhachHangTiepTheo(){
        return khachHangDAO.layMaKhachHangTiepTheo();
    }

    public boolean suaKhachHang(KhachHangDTO khachHang) {
        return khachHangDAO.suaKhachHang(khachHang);
    }

    public boolean xoaKhachHang(int maKhachHang) {
        return khachHangDAO.xoaKhachHang(maKhachHang);
    }

    public KhachHangDTO layKhachHangTheoMa(int maKhachHang) {
        return khachHangDAO.layKhachHangTheoMa(maKhachHang);
    }
    
    public List<KhachHangDTO> layDanhSachTatCaKhachHang(){
        return khachHangDAO.layDanhSachTatCaKhachHang();
    }
}