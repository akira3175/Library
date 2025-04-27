package org.example.DTO;

public class ChiTietPhieuNhapDTO {
    
    private int MaChiTietPhieuNhap;
    private int MaPhieuNhap;
    private int MaSanPham;
    private int DonGia;
    private int SoLuong;
    private String TenSanPham;
    
    public ChiTietPhieuNhapDTO(int MaChiTietPhieuNhap, int MaPhieuNhap, int MaSanPham, int DonGia, int SoLuong, String TenSanPham) {
        this.MaChiTietPhieuNhap = MaChiTietPhieuNhap;
        this.MaPhieuNhap = MaPhieuNhap;
        this.MaSanPham = MaSanPham;
        this.DonGia = DonGia;
        this.SoLuong = SoLuong;
        this.TenSanPham = TenSanPham;
    }
    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public ChiTietPhieuNhapDTO() {
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