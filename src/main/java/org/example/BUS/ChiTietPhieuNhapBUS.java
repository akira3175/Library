/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.example.DAO.ChiTietPhieuNhapDAO;
import org.example.DTO.ChiTietPhieuNhap;

/**
 *
 * @author MTeumb
 */
public class ChiTietPhieuNhapBUS {

    private ChiTietPhieuNhapDAO ctpnDAO;

    public ChiTietPhieuNhapBUS() {
        this.ctpnDAO = new ChiTietPhieuNhapDAO();
    }

    public void hienThiChiTietPhieuNhap(JTable table, int id) {
        List<ChiTietPhieuNhap> listct = ctpnDAO.layChiTietPhieuNhap(id);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ChiTietPhieuNhap ctpn : listct) {
            Object[] row = new Object[]{
                ctpn.getMaSanPham(),
                ctpn.getDonGia(),
                ctpn.getSoLuong(),
                ctpn.getDonGia() * ctpn.getSoLuong(),};
            model.addRow(row);
        }
        table.setModel(model);
    }

}
