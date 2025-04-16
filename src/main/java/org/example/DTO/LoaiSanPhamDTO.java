package org.example.DTO;

public class LoaiSanPhamDTO {
    private int maLoaiSanPham;
    private String tenLoaiSanPham;
    private String moTa;
    private boolean trangThai;

    public LoaiSanPhamDTO() {
    }

    public LoaiSanPhamDTO(int maLoaiSanPham, String tenLoaiSanPham, String moTa, boolean trangThai) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public LoaiSanPhamDTO(LoaiSanPhamDTO other) {
        this.maLoaiSanPham = other.getMaLoaiSanPham();
        this.tenLoaiSanPham = other.getTenLoaiSanPham();
        this.moTa = other.getMoTa();
        this.trangThai = other.isTrangThai();
    }

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}