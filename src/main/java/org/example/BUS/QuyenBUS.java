package org.example.BUS;

import org.example.DAO.QuyenDAO;
import org.example.DTO.Quyen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class QuyenBUS {
    private QuyenDAO quyenDAO;
    private static final Logger logger = LoggerFactory.getLogger(QuyenBUS.class);

    public QuyenBUS() {
        quyenDAO = new QuyenDAO();
    }

    public List<Quyen> layDanhSachQuyen() {
        return quyenDAO.layDanhSachQuyen();
    }

    public Quyen layQuyenTheoMa(int maQuyen) {
        return quyenDAO.layQuyenTheoMa(maQuyen);
    }

    public List<Quyen> layDanhSachQuyenTheoVaiTro(int maVaiTro) {
        return quyenDAO.layDanhSachQuyenTheoVaiTro(maVaiTro);
    }

    public boolean themQuyenVaoVaiTro(int maVaiTro, List<Quyen> danhSachQuyen) {
        boolean thanhCong = true;
        for (Quyen quyen : danhSachQuyen) {
            if (!quyenDAO.themQuyenVaoVaiTro(maVaiTro, quyen.getMaQuyen())) {
                logger.info("Thêm quyền {} vào vai trò {} thất bại", quyen.getTenQuyen(), maVaiTro);
                thanhCong = false;
            }
        }
        logger.info("Thêm quyền {} vào vai trò {} thành công", danhSachQuyen.get(0).getTenQuyen(), maVaiTro);
        return thanhCong;
    }

    public boolean xoaQuyenKhoiVaiTro(int maVaiTro, List<Quyen> danhSachQuyen) {
        boolean thanhCong = true;
        for (Quyen quyen : danhSachQuyen) {
            logger.info("Xóa quyền {} khỏi vai trò {} thất bại", quyen.getTenQuyen(), maVaiTro);
            thanhCong = false;
        }
        logger.info("Xóa quyền {} khỏi vai trò {} thành công", danhSachQuyen.get(0).getTenQuyen(), maVaiTro);
        return thanhCong;
    }

    public boolean capNhatQuyenVaoVaiTro(int maVaiTro, List<Quyen> danhSachQuyen) {
        boolean thanhCong = true;

        danhSachQuyen.removeIf(quyen -> !quyen.isChecked());

        if (quyenDAO.capNhatQuyenVaoVaiTro(maVaiTro, danhSachQuyen).isEmpty()) {
            thanhCong = false;
        }
        logger.info("Cập nhật quyền {} vào vai trò {} thành công", danhSachQuyen.get(0).getTenQuyen(), maVaiTro);
        return thanhCong;
    }
}
