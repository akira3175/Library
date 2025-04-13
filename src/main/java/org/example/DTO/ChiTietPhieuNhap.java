package org.example.DTO;

public class ChiTietPhieuNhap {
    
    private int MaChiTietPhieuNhap;
    private int MaPhieuNhap;
    private int MaSanPham;
    private int DonGia;
    private int SoLuong;

    public ChiTietPhieuNhap(int MaChiTietPhieuNhap, int MaPhieuNhap, int MaSanPham, int DonGia, int SoLuong) {
        this.MaChiTietPhieuNhap = MaChiTietPhieuNhap;
        this.MaPhieuNhap = MaPhieuNhap;
        this.MaSanPham = MaSanPham;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
    }

    public ChiTietPhieuNhap() {
    }

    public int getMaChiTietPhieuNhap() {
        return MaChiTietPhieuNhap;
    }

    public void setMaChiTietPhieuNhap(int MaChiTietPhieuNhap) {
        this.MaChiTietPhieuNhap = MaChiTietPhieuNhap;
    }

    public int getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(int MaPhieuNhap) {
        this.MaPhieuNhap = MaPhieuNhap;
    }

    public int getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(int MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int DonGia) {
        this.DonGia = DonGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }
}
