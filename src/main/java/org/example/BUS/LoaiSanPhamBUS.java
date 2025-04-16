package org.example.BUS;

import org.example.DAO.LoaiSanPhamDAO;
import org.example.DTO.LoaiSanPhamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LoaiSanPhamBUS {

    private LoaiSanPhamDAO loaiSanPhamDAO;

    public LoaiSanPhamBUS() {
        loaiSanPhamDAO = new LoaiSanPhamDAO();
    }

    public List<LoaiSanPhamDTO> layDanhSachLoaiSanPham() {
        return loaiSanPhamDAO.layDanhSachTatCaLoaiSanPham();
    }

    public boolean themLoaiSanPham(LoaiSanPhamDTO loai) {
        return loaiSanPhamDAO.themLoaiSanPham(loai);
    }

    public boolean suaLoaiSanPham(LoaiSanPhamDTO loai) {
        return loaiSanPhamDAO.suaLoaiSanPham(loai);
    }

    public boolean xoaLoaiSanPham(int maLoaiSanPham) {
        return loaiSanPhamDAO.xoaLoaiSanPham(maLoaiSanPham);
    }

    public void hienThiLoaiSanPhamLenTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<LoaiSanPhamDTO> danhSach = layDanhSachLoaiSanPham();
        for (LoaiSanPhamDTO loai : danhSach) {
            model.addRow(new Object[]{
                loai.getMaLoaiSanPham(),
                loai.getTenLoaiSanPham(),
                loai.getMoTa(),
                loai.isTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }
}
