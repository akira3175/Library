/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import java.util.regex.*;
import org.example.DAO.NhaCungCapDAO;
import org.example.DTO.NhaCungCapDTO;

/**
 *
 * @author MTeumb
 */
public class NhaCungCapBUS {

    NhaCungCapDAO ncc = new NhaCungCapDAO();

    public List<NhaCungCapDTO> layTatCaNhaCungCap() {
        return ncc.layTatCaNhaCungCap();
    }

    public NhaCungCapDTO layMotNhaCungCap(int maNhaCungCap) {
        return ncc.layMotNhaCungCap(maNhaCungCap);
    }
    
    public String themNhaCungCap(NhaCungCapDTO ncc) {
        if (ncc.getTenNhaCungCap().isEmpty()) {
            return "Tên Nhà Cung Cấp Không Được Rỗng!";
        }
        if (ncc.getDiaChi().isEmpty()) {
            return "Địa Chỉ Không Được Rỗng!";
        }
        
        if (!this.ncc.themNhaCungCap(ncc)) {
            return "Thêm Nhà Cung Cấp Không Thành Công!";
        }
        return "Thêm Nhà Cung Cấp Thành Công!";
    }
}
