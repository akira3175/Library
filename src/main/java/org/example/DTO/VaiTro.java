package org.example.DTO;

public class VaiTro {
    private int maVaiTro;
    private String tenVaiTro;
    private String moTa;
    private int soLuongNguoiDung;

    public VaiTro() {}

    public VaiTro(int maVaiTro, String tenVaiTro, String moTa) {
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
        this.moTa = moTa;
    }

    public VaiTro(int maVaiTro, String tenVaiTro, String moTa, int soLuongNguoiDung) {
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
        this.moTa = moTa;
        this.soLuongNguoiDung = soLuongNguoiDung;
    }

    public int getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(int maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuongNguoiDung() {
        return soLuongNguoiDung;
    }

    public void setSoLuongNguoiDung(int soLuongNguoiDung) {
        this.soLuongNguoiDung = soLuongNguoiDung;
    }

    @Override
    public String toString() {
        return tenVaiTro; // Dùng để hiển thị trong JComboBox
    }
}
