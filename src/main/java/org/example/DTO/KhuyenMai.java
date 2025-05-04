package org.example.DTO;

import java.util.Date;

public class KhuyenMai {
    private int maKhuyenMai;
    private String tenKhuyenMai;
    private int soTienKhuyenMai;
    private int dieuKienHoaDon;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Boolean trangThai;

    // Getters and Setters
    public int getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(int maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }
    public String getTenKhuyenMai() { return tenKhuyenMai; }
    public void setTenKhuyenMai(String tenKhuyenMai) { this.tenKhuyenMai = tenKhuyenMai; }
    public int getSoTienKhuyenMai() { return soTienKhuyenMai; }
    public void setSoTienKhuyenMai(int soTienKhuyenMai) { this.soTienKhuyenMai = soTienKhuyenMai; }
    public int getDieuKienHoaDon() { return dieuKienHoaDon; }
    public void setDieuKienHoaDon(int dieuKienHoaDon) { this.dieuKienHoaDon = dieuKienHoaDon; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public Boolean getTrangThai() { return trangThai; }
    public void setTrangThai(Boolean trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return maKhuyenMai == 0 ? "Không áp dụng" : tenKhuyenMai + " (-" + soTienKhuyenMai + " VNĐ)";
    }
}