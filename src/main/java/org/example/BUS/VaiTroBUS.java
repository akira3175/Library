package org.example.BUS;

import org.example.DAO.VaiTroDAO;
import org.example.DTO.VaiTro;
import org.example.DTO.Quyen;
import org.example.DAO.QuyenDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VaiTroBUS {
    private VaiTroDAO vaiTroDAO = new VaiTroDAO();
    private QuyenDAO quyenDAO = new QuyenDAO();
    private static final Logger logger = LoggerFactory.getLogger(VaiTro.class);

    public VaiTro themHoacSuaVaiTro(VaiTro vaiTro) {
        if (vaiTro.getMaVaiTro() == 0) {
            return themVaiTro(vaiTro);
        } else {
            return suaVaiTro(vaiTro);
        }
    }

    public VaiTro themVaiTro(VaiTro vaiTro) {
        return vaiTroDAO.themVaiTro(vaiTro)
                .orElseThrow(() -> new RuntimeException("Thêm vai trò thất bại!"));
    }

    public VaiTro suaVaiTro(VaiTro vaiTro) {
        return vaiTroDAO.suaVaiTro(vaiTro)
                .orElseThrow(() -> new RuntimeException("Sửa vai trò thất bại!"));
    }

    public List<VaiTro> danhSachVaitro() {
        List<VaiTro> vaiTroList = vaiTroDAO.layDanhSachTatCaVaiTro();

        if (vaiTroList == null || vaiTroList.isEmpty()) {
            logger.warn("Danh sách vai trò trống!");
        }

        return vaiTroList;
    }

    public VaiTro layVaiTroTheoID(int maVaiTro) {
        VaiTro vaiTro = vaiTroDAO.layVaiTroTheoID(maVaiTro);

        if(vaiTro == null) {
            logger.warn("Vai trò không tồn tại");
        }

        return vaiTro;
    }

    public List<VaiTro> danhSachVaitroKemSoLuongNguoiDung() {
        List<VaiTro> vaiTroList = vaiTroDAO.layDanhSachVaiTroVaSoLuongNguoiDung();

        if (vaiTroList == null || vaiTroList.isEmpty()) {
            logger.warn("Danh sách vai trò trống!");
        }

        return vaiTroList;
    }

    public List<Quyen> layDanhSachQuyenTheoVaiTro(int maVaiTro) {
        return quyenDAO.layDanhSachQuyenTheoVaiTro(maVaiTro);
    }   
}
