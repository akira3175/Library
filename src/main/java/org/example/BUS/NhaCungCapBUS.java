/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
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
    
    public boolean themNhaCungCap(NhaCungCapDTO ncc) {
        return this.ncc.themNhaCungCap(ncc);
    }
}
