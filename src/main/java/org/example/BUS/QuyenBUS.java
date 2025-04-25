package org.example.BUS;

import org.example.DAO.QuyenDAO;
import org.example.DTO.Quyen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    public boolean coTenQuyen(String tenQuyen) {
        return quyenDAO.coTenQuyen(tenQuyen);
    }

    public List<Quyen> khoiTaoQuyen() {
        List<Quyen> danhSachQuyen = new ArrayList<>();
        danhSachQuyen.add(new Quyen(1, "Bán hàng"));
        danhSachQuyen.add(new Quyen(2, "Quản lý khuyến mãi"));
        danhSachQuyen.add(new Quyen(3, "Quản lý khách hàng"));
        danhSachQuyen.add(new Quyen(4, "Quản lý nhân sự"));
        danhSachQuyen.add(new Quyen(5, "Quản lý kho"));
        danhSachQuyen.add(new Quyen(6, "Quản lý sản phẩm"));
        danhSachQuyen.add(new Quyen(7, "Thống kê"));
        

        for (Quyen quyen : danhSachQuyen) {
            themQuyen(quyen);
        }
        danhSachQuyen = quyenDAO.layDanhSachQuyen();
        return danhSachQuyen;
    }

    public boolean themQuyen(Quyen quyen) {
        return quyenDAO.themQuyen(quyen);
    }
}
