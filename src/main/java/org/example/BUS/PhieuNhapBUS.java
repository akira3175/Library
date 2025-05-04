/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import org.example.DAO.ChiTietPhieuNhapDAO;
import org.example.DAO.PhieuNhapDAO;
import org.example.DTO.PhieuNhapDTO;
import org.example.DTO.SanPhamDTO;

/**
 *
 * @author MTeumb
 */
public class PhieuNhapBUS {

    private PhieuNhapDAO pnDAO = new PhieuNhapDAO();
    private ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();

    public List<PhieuNhapDTO> layTatCaPhieuNhap() {
        return pnDAO.layTatCaPhieuNhap();
    }

    public String themPhieuNhap(PhieuNhapDTO pnDTO, List<SanPhamDTO> listSP) {
        if (listSP == null || listSP.isEmpty()) {
            return "Vui Lòng Chọn Sản Phẩm!";
        }
        
        if (pnDAO.themPhieuNhap(pnDTO)) {
            ctpnBUS.themChiTietPhieuNhap(listSP);
                
            
            return "Tạo Phiếu Nhập Thành Công!";
        }
        return "Tạo Phiếu Nhập Không Thành Công!";
    }
}
