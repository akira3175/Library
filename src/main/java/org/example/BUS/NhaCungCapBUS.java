/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.BUS;

import java.util.List;
import java.util.regex.*;
import org.example.DAO.NhaCungCapDAO;
import org.example.DTO.NhaCungCapDTO;
import org.example.GUI.Panels.NhapKho.NhapHang;

/**
 *
 * @author MTeumb
 */
public class NhaCungCapBUS {

    NhaCungCapDAO ncc = new NhaCungCapDAO();

    public boolean laSoNguyenHopLe(String str) {
        return str != null
                && !str.trim().isEmpty()
                && str.length() == 10
                && str.matches("\\d{10}");
    }

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
        if (!laSoNguyenHopLe(ncc.getSoDienThoai())) {
            return "Số Điện Thoại Không Hợp Lệ";
        }
        if (!laSoNguyenHopLe(ncc.getFax())) {
            return "Số Fax Không Hợp Lệ";
        }

        if (!this.ncc.themNhaCungCap(ncc)) {
            return "Thêm Nhà Cung Cấp Không Thành Công!";
        }
        return "Thêm Nhà Cung Cấp Thành Công!";
    }

    public String suaNhaCungCap(NhaCungCapDTO ncc) {
        if (ncc.getTenNhaCungCap().isEmpty()) {
            return "Tên Nhà Cung Cấp Không Được Rỗng!";
        }
        if (ncc.getDiaChi().isEmpty()) {
            return "Địa Chỉ Không Được Rỗng!";
        }
        if (!laSoNguyenHopLe(ncc.getSoDienThoai())) {
            return "Số Điện Thoại Không Hợp Lệ";
        }
        if (!laSoNguyenHopLe(ncc.getFax())) {
            return "Số Fax Không Hợp Lệ";
        }

        if (!this.ncc.suaNhaCungCap(ncc)) {
            return "Sửa Nhà Cung Cấp Không Thành Công!";
        }
        return "Sửa Nhà Cung Cấp Thành Công!";
    }

    public String xoaNhaCungCap(int maNCC) {
        if (this.ncc.xoaNhaCungCap(maNCC)) {
            return "Xóa Nhà Cung Cấp Thành Công!";
        }
        return "Xóa Nhà Cung Cấp Không Thành Công!";
    }

    public List<NhaCungCapDTO> timKiemNCC(String input) {
        return this.ncc.timKiemNCC(input);
    }
}
