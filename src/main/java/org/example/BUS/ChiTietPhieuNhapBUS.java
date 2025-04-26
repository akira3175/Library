/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.example.DAO.ChiTietPhieuNhapDAO;
import org.example.DTO.ChiTietPhieuNhapDTO;

/**
 *
 * @author MTeumb
 */
public class ChiTietPhieuNhapBUS {

    private ChiTietPhieuNhapDAO ctpnDAO;

    public ChiTietPhieuNhapBUS() {
        this.ctpnDAO = new ChiTietPhieuNhapDAO();
    }

    public List<ChiTietPhieuNhapDTO> layChiTietPhieuNhap(int id) {
        return ctpnDAO.layChiTietPhieuNhap(id);
    }
}
