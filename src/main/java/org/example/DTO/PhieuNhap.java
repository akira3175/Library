package org.example.DTO;

import java.util.Date;


public class PhieuNhap {
    private int maPhieuNhap;
    private int maNguoiDung;
    private int maNhaCungCap;
    private Date thoiGianLap;
    private int trangThai;

    public PhieuNhap(int maPhieuNhap, int maNguoiDung, int maNhaCungCap,
    Date thoiGianLap, int trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNguoiDung = maNguoiDung;
        this.maNhaCungCap = maNhaCungCap;
        this.thoiGianLap = thoiGianLap;
        this.trangThai = trangThai;
    }


    // Getters
    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public Date getThoiGianLap() {
        return thoiGianLap;
    }

    public int getTrangThai() {
        return trangThai;
    }

    // Setters
    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    
    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public void setThoiGianLap(Date thoiGianLap) {
        this.thoiGianLap = thoiGianLap;
    } 

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}