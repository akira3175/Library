package org.example.BUS;

import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.example.DAO.BanHangDAO;
import org.example.DTO.ChiTietHoaDon;
import org.example.DTO.HoaDon;
import org.example.DTO.KhachHangDTO;
import org.example.DTO.KhuyenMai;
import org.example.DTO.SanPhamDTO;
import org.example.DTO.GioHang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BanHangBUS {
    private static final Logger logger = LoggerFactory.getLogger(BanHangBUS.class);
    private BanHangDAO banHangDAO;
    private SanPhamBUS sanPhamBUS;
    private List<GioHang> cart;

    public BanHangBUS() {
        this.banHangDAO = new BanHangDAO();
        this.sanPhamBUS = new SanPhamBUS();
        this.cart = new ArrayList<>();
    }

    public void addToCart(int maSanPham, int soLuong) {
        SanPhamDTO sanPham = sanPhamBUS.laySanPhamTheoMa(maSanPham);
        if (sanPham == null || !sanPham.getTrangThai()) {
            throw new RuntimeException("Sản phẩm không tồn tại hoặc không hoạt động!");
        }
        if (sanPham.getSoLuong() < soLuong) {
            throw new RuntimeException("Số lượng sản phẩm trong kho không đủ!");
        }

        for (GioHang item : cart) {
            if (item.getSanPham().getMaSanPham() == maSanPham) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                logger.info("Cập nhật số lượng sản phẩm {} trong giỏ hàng: {}", sanPham.getTenSanPham(), item.getSoLuong());
                return;
            }
        }
        cart.add(new GioHang(sanPham, soLuong));
        logger.info("Thêm sản phẩm {} vào giỏ hàng, số lượng: {}", sanPham.getTenSanPham(), soLuong);
    }

    public List<GioHang> getCart() {
        return cart;
    }

    public void removeFromCart(int maSanPham) {
        cart.removeIf(item -> item.getSanPham().getMaSanPham() == maSanPham);
        logger.info("Xóa sản phẩm {} khỏi giỏ hàng.", maSanPham);
    }

    public void clearCart() {
        cart.clear();
        logger.info("Giỏ hàng đã được xóa.");
    }

    public void resetCart() {
        clearCart();
    }

    public HoaDon taoHoaDon(KhachHangDTO khachHang, KhuyenMai khuyenMai) {
        if (cart.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaNguoiDung(NguoiDungBUS.getNguoiDungHienTai().getMaNguoiDung());
        hoaDon.setMaKhachHang(khachHang.getMaKhachHang());
        hoaDon.setNgayLap(new Date());

        int thanhTien = 0;
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
        for (GioHang item : cart) {
            ChiTietHoaDon chiTiet = new ChiTietHoaDon();
            chiTiet.setMaSanPham(item.getSanPham().getMaSanPham());
            chiTiet.setSoLuong(item.getSoLuong());
            chiTiet.setDonGia((int) item.getSanPham().getGiaLoi() + item.getSanPham().getGiaVon());
            chiTietHoaDons.add(chiTiet);
            thanhTien += chiTiet.getDonGia() * chiTiet.getSoLuong();

            // Update product quantity
            SanPhamDTO sanPham = item.getSanPham();
            sanPham.setSoLuong(sanPham.getSoLuong() - item.getSoLuong());
            sanPhamBUS.suaSanPham(sanPham);
        }

        int tienGiam = 0;
        if (khuyenMai != null && khuyenMai.getMaKhuyenMai() > 0) {
            if (thanhTien >= khuyenMai.getDieuKienHoaDon()) {
                hoaDon.setMaKhuyenMai(khuyenMai.getMaKhuyenMai());
                tienGiam = khuyenMai.getSoTienKhuyenMai();
            } else {
                logger.warn("Hóa đơn không đủ điều kiện khuyến mãi: {}", khuyenMai.getTenKhuyenMai());
            }
        }
        hoaDon.setTienGiam(tienGiam);
        hoaDon.setThanhTien(thanhTien - tienGiam);
        hoaDon.setChiTietHoaDons(chiTietHoaDons);

        HoaDon savedHoaDon = banHangDAO.taoHoaDon(hoaDon);
        if (savedHoaDon == null) {
            throw new RuntimeException("Tạo hóa đơn thất bại!");
        }

        clearCart();
        logger.info("Tạo hóa đơn thành công với ID: {}", savedHoaDon.getMaHoaDon());
        return savedHoaDon;
    }

    public List<HoaDon> getAllHoaDon() {
        try {
            List<HoaDon> hoaDons = banHangDAO.getAllHoaDon();
            logger.info("Lấy danh sách {} hóa đơn.", hoaDons.size());
            return hoaDons;
        } catch (RuntimeException e) {
            logger.error("Lỗi khi lấy danh sách hóa đơn: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void exportHoaDonToPDF(HoaDon hoaDon, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(new File(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("HOA DON BAN HANG")
                    .setBold().setFontSize(20));
            document.add(new Paragraph("Ma hoa don: " + hoaDon.getMaHoaDon()));
            document.add(new Paragraph("Ngay lap: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(hoaDon.getNgayLap())));
            document.add(new Paragraph("Nhan vien: " + NguoiDungBUS.getNguoiDungHienTai().getHoTen()));
            document.add(new Paragraph("Khach hang: Khach hhng ID " + hoaDon.getMaKhachHang()));

            float[] columnWidths = {50, 200, 50, 100, 100};
            Table table = new Table(columnWidths);
            table.addCell("STT");
            table.addCell("Ten san pham");
            table.addCell("So luong");
            table.addCell("Don gia");
            table.addCell("Thanh tien");

            int stt = 1;
            for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDons()) {
                SanPhamDTO sanPham = sanPhamBUS.laySanPhamTheoMa(chiTiet.getMaSanPham());
                table.addCell(String.valueOf(stt++));
                table.addCell(sanPham.getTenSanPham());
                table.addCell(String.valueOf(chiTiet.getSoLuong()));
                table.addCell(String.format("%,d", chiTiet.getDonGia()));
                table.addCell(String.format("%,d", chiTiet.getDonGia() * chiTiet.getSoLuong()));
            }

            document.add(table);
            document.add(new Paragraph("Tong tien: " + String.format("%,d", hoaDon.getThanhTien() + hoaDon.getTienGiam())));
            document.add(new Paragraph("Giam gia: " + String.format("%,d", hoaDon.getTienGiam())));
            document.add(new Paragraph("Thanh tien: " + String.format("%,d", hoaDon.getThanhTien())));

            document.close();
            logger.info("Xuất hóa đơn {} ra PDF thành công: {}", hoaDon.getMaHoaDon(), filePath);
        } catch (Exception e) {
            logger.error("Lỗi khi xuất PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Xuất PDF thất bại!");
        }
    }

    public SanPhamDTO laySanPhamTheoMa(int maSanPham) {
        return sanPhamBUS.laySanPhamTheoMa(maSanPham);
    }
}