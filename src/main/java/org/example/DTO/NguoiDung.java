package org.example.DTO;

import java.util.Date;

public class NguoiDung {
    private int maNguoiDung;
    private int maVaiTro;
    private String avatarURL;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String email;
    private boolean conHoatDong;
    private String tenDangNhap;
    private String matKhau;

    // Constructor mặc định
    public NguoiDung() {}

    // Constructor đầy đủ
    public NguoiDung(int maNguoiDung, int maVaiTro, String avatarURL, String hoTen, Date ngaySinh,
                     String gioiTinh, String diaChi, String email, boolean conHoatDong,
                     String tenDangNhap) {
        this.maNguoiDung = maNguoiDung;
        this.maVaiTro = maVaiTro;
        this.avatarURL = avatarURL;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.conHoatDong = conHoatDong;
        this.tenDangNhap = tenDangNhap;
    }

    // Getters and Setters
    public int getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(int maNguoiDung) { this.maNguoiDung = maNguoiDung; }

    public int getMaVaiTro() {
        return maVaiTro;
    }
    public void setMaVaiTro(int maVaiTro) { this.maVaiTro = maVaiTro; }

    public String getAvatarURL() { return avatarURL; }
    public void setAvatarURL(String avatarURL) { this.avatarURL = avatarURL; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isConHoatDong() { return conHoatDong; }
    public void setConHoatDong(boolean conHoatDong) { this.conHoatDong = conHoatDong; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}
