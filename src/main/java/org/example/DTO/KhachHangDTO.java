package org.example.DTO;

public class KhachHangDTO {
    private int maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private boolean trangThai;

    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKhachHang, String hoTen, String soDienThoai, String diaChi, boolean trangThai) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public KhachHangDTO(KhachHangDTO other) {
        this.maKhachHang = other.maKhachHang;
        this.hoTen = other.hoTen;
        this.soDienThoai = other.soDienThoai;
        this.diaChi = other.diaChi;
        this.trangThai = other.trangThai;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}