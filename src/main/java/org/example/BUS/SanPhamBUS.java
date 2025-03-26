package org.example.BUS;

import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SanPhamBUS {

    private SanPhamDAO sanPhamDAO;

    public SanPhamBUS() {
        this.sanPhamDAO = new SanPhamDAO();
    }

    public void hienThiSanPhamLenTable(JTable table) {
        List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachTatCaSanPham();

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (SanPhamDTO sanPham : danhSachSanPham) {
            Object[] row = new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenLoaiSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getTenSanPham(),
                sanPham.getNhaSanXuat(),
                sanPham.isTrangThai() ? "Hoạt động" : "Không hoạt động",
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),};
            model.addRow(row);
        }

        table.setModel(model);

        if (danhSachSanPham.isEmpty()) {
            System.out.println("️ Không có sản phẩm nào trong danh sách.");
        } else {
            System.out.println("Đa tải " + danhSachSanPham.size() + " sản phẩm lên bảng.");
        }
    }

    public void HienThiSanPhamTimKiem(JTable table, String tuKhoa) {
        List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachTimKiem(tuKhoa);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (SanPhamDTO sanPham : danhSachSanPham) {
            Object[] row = new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getMaLoaiSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getTenSanPham(),
                sanPham.getNhaSanXuat(),
                sanPham.isTrangThai() ? "Còn hàng" : "Không còn hàng",
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),};
            model.addRow(row);
        }

        table.setModel(model);

        if (danhSachSanPham.isEmpty()) {
            System.out.println("️ Không có sản phẩm nào trong danh sách.");
        } else {
            System.out.println(" Đa tai " + danhSachSanPham.size() + " san pham len bang.");
        }
    }

    public boolean themSanPham(SanPhamDTO sanPham) {
        return sanPhamDAO.themSanPham(sanPham);
    }

    public boolean suaSanPham(SanPhamDTO sanPham) {
        return sanPhamDAO.suaSanPham(sanPham);
    }

    public boolean xoaSanPham(int maSanPham) {
        return sanPhamDAO.xoaSanPham(maSanPham);
    }
}
