/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;


import java.util.List;
import org.example.DAO.PhieuNhapDAO;
import org.example.DTO.PhieuNhapDTO;

/**
 *
 * @author MTeumb
 */
public class PhieuNhapBUS {
    private PhieuNhapDAO phieunhap = new PhieuNhapDAO();
    
    public List<PhieuNhapDTO> layTatCaPhieuNhap () {
        return phieunhap.layTatCaPhieuNhap();
    }
}