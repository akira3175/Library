/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.example.DAO.PhieuNhapDAO;
import org.example.DTO.PhieuNhap;

/**
 *
 * @author MTeumb
 */
public class PhieuNhapBUS {
    private PhieuNhapDAO phieunhap;
    
    public PhieuNhapBUS() {
        this.phieunhap = new PhieuNhapDAO();
    }
    
    public void hienThiPhieuNhapLenTable(JTable table) {
        List<PhieuNhap> listpn = phieunhap.layTatCaPhieuNhap();

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (PhieuNhap phieunhap : listpn) {
            Object[] row = new Object[]{
                phieunhap.getMaPhieuNhap(),
                phieunhap.getMaNguoiDung(),
                phieunhap.getMaNhaCungCap(),
                phieunhap.getThoiGianLap(),
                phieunhap.getTrangThai() == 1 ? "Đã Thanh Toán" : "Chưa thanh toán",};
            model.addRow(row);
        }

        table.setModel(model);
    }
}