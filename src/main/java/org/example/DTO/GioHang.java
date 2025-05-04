package org.example.DTO;

public class GioHang {
    private SanPhamDTO sanPham;
    private int soLuong;

    public GioHang(SanPhamDTO sanPham, int soLuong) {
        this.sanPham = sanPham;
        this.soLuong = soLuong;
    }

    // Getters and Setters
    public SanPhamDTO getSanPham() { return sanPham; }
    public void setSanPham(SanPhamDTO sanPham) { this.sanPham = sanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}