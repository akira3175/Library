package org.example.BUS;

import org.example.DAO.SanPhamDAO;
import org.example.DTO.SanPhamDTO;
import org.example.Utils.ExcelUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SanPhamBUS {

    private SanPhamDAO sanPhamDAO;
    private ExcelUtils excelUtils;

    public SanPhamBUS() {
        this.sanPhamDAO = new SanPhamDAO();
        this.excelUtils = new ExcelUtils();
    }

    public void hienThiSanPhamLenTable(JTable table) {
        List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachTatCaSanPham();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SanPhamDTO sanPham : danhSachSanPham) {
            model.addRow(new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenLoaiSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getTenSanPham(),
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),
                sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public void hienThiSanPhamLenTable(JTable table, String filter) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<SanPhamDTO> danhSachSanPham;

        if ("Không hoạt động".equals(filter)) {
            danhSachSanPham = layDanhSachTatCaSanPhamKhongHoatDong();
        } else if ("Đang hoạt động".equals(filter)) {
            danhSachSanPham = layDanhSachTatCaSanPhamHoatDong();
        } else {
            danhSachSanPham = layDanhSachTatCaSanPham();
        }

        for (SanPhamDTO sanPham : danhSachSanPham) {
            model.addRow(new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenLoaiSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),
                sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public void HienThiSanPhamTimKiem(JTable table, String tuKhoa) {
        List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachTimKiem(tuKhoa);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SanPhamDTO sanPham : danhSachSanPham) {
            model.addRow(new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenLoaiSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),
                sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public void xuatDanhSachSanPhamRaExcel(JTable table) {
        List<SanPhamDTO> danhSachSanPham = layDanhSachTatCaSanPham();
        excelUtils.xuatDanhSachSanPhamRaExcel(danhSachSanPham, table);
    }

    public void xuatDanhSachSanPhamRaExcel(JTable table, String filter) {
        List<SanPhamDTO> danhSachSanPham;
        if ("Không hoạt động".equals(filter)) {
            danhSachSanPham = layDanhSachTatCaSanPhamKhongHoatDong();
        } else if ("Đang hoạt động".equals(filter)) {
            danhSachSanPham = layDanhSachTatCaSanPhamHoatDong();
        } else {
            danhSachSanPham = layDanhSachTatCaSanPham();
        }
        excelUtils.xuatDanhSachSanPhamRaExcel(danhSachSanPham, table);
    }

    public void hienThiSanPhamLocNangCao(JTable table, String tenLoaiSanPham, String minGiaVon, String maxGiaVon,
            String minGiaLoi, String maxGiaLoi, String minSoLuong, String maxSoLuong, String trangThai) {
        List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachSanPhamLocNangCao(tenLoaiSanPham, minGiaVon, maxGiaVon,
                minGiaLoi, maxGiaLoi, minSoLuong, maxSoLuong, trangThai);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SanPhamDTO sanPham : danhSachSanPham) {
            model.addRow(new Object[]{
                sanPham.getMaSanPham(),
                sanPham.getTenLoaiSanPham(),
                sanPham.getTenSanPham(),
                sanPham.getAnhSanPhamURL(),
                sanPham.getSoLuong(),
                sanPham.getGiaVon(),
                sanPham.getGiaLoi(),
                sanPham.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            });
        }
    }
    
    public void xuatDanhSachSanPhamLocNangCaoRaExcel(JTable table, String tenLoaiSanPham, String minGiaVon, String maxGiaVon, 
                                                 String minGiaLoi, String maxGiaLoi, String minSoLuong, String maxSoLuong, String trangThai) {
    List<SanPhamDTO> danhSachSanPham = sanPhamDAO.layDanhSachSanPhamLocNangCao(tenLoaiSanPham, minGiaVon, maxGiaVon, 
                                                                               minGiaLoi, maxGiaLoi, minSoLuong, maxSoLuong, trangThai);
    excelUtils.xuatDanhSachSanPhamRaExcel(danhSachSanPham, table);
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

    public List<SanPhamDTO> layDanhSachTatCaSanPham() {
        return sanPhamDAO.layDanhSachTatCaSanPham();
    }

    public List<SanPhamDTO> layDanhSachTatCaSanPhamHoatDong() {
        return sanPhamDAO.layDanhSachTatCaSanPhamHoatDong();
    }

    public List<SanPhamDTO> layDanhSachTatCaSanPhamKhongHoatDong() {
        return sanPhamDAO.layDanhSachTatCaSanPhamKhongHoatDong();
    }

    public SanPhamDTO laySanPhamTheoMa(int maSanPham) {
        return sanPhamDAO.laySanPhamTheoMa(maSanPham);
    }

    public int laySanPhamTheoMaMax() {
        return sanPhamDAO.layMaSanPhamTiepTheo();
    }

    public List<SanPhamDTO> layDanhSachLoaiSanPham() {
        return sanPhamDAO.layDanhSachLoaiSanPham();
    }
    
    public List<SanPhamDTO> xuatDanhSachSanPhamLocNangCaoRaExcel(String tenLoaiSanPham, String minGiaVon, String maxGiaVon,
            String minGiaLoi, String maxGiaLoi, String minSoLuong, String maxSoLuong, String trangThai){
        return sanPhamDAO.layDanhSachSanPhamLocNangCao(tenLoaiSanPham, minGiaVon, maxGiaVon, minGiaLoi, maxGiaLoi, minSoLuong, maxSoLuong, trangThai);
    }
}
