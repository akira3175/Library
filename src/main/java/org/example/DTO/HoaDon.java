package org.example.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon {
    private int maHoaDon;
    private int maNguoiDung;
    private int maKhachHang;
    private int maKhuyenMai;
    private Date ngayLap;
    private int tienGiam;
    private int thanhTien;
    private boolean trangThai;
    private List<ChiTietHoaDon> chiTietHoaDons;

    public HoaDon() {
        this.chiTietHoaDons = new ArrayList<>();
        this.trangThai = true; // Default to active
    }

    // Getters and Setters
    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(int maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }
    public int getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(int maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }
    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }
    public int getTienGiam() { return tienGiam; }
    public void setTienGiam(int tienGiam) { this.tienGiam = tienGiam; }
    public int getThanhTien() { return thanhTien; }
    public void setThanhTien(int thanhTien) { this.thanhTien = thanhTien; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
    public List<ChiTietHoaDon> getChiTietHoaDons() { return chiTietHoaDons; }
    public void setChiTietHoaDons(List<ChiTietHoaDon> chiTietHoaDons) { this.chiTietHoaDons = chiTietHoaDons; }
}