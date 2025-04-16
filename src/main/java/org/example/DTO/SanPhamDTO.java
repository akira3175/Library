package org.example.DTO;

public class SanPhamDTO {

    private int MaSanPham;
    private int MaLoaiSanPham;
    private String tenLoaiSanPham;
    private String AnhSanPhamURL;
    private String TenSanPham;
    private String NhaSanXuat;
    private String sanphamcol;
    private int SoLuong;
    private boolean TrangThai;
    private double GiaVon;
    private double GiaLoi;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int MaSanPham, int MaLoaiSanPham, String AnhSanPhamURL, String TenSanPham,
                      String NhaSanXuat, String sanphamcol, boolean TrangThai,
                      int SoLuong, double GiaVon, double GiaLoi) {
        this.MaSanPham = MaSanPham;
    this.MaLoaiSanPham = MaLoaiSanPham;
        this.tenLoaiSanPham = null;
        this.AnhSanPhamURL = AnhSanPhamURL;
        this.TenSanPham = TenSanPham;
        this.NhaSanXuat = NhaSanXuat;
        this.sanphamcol = sanphamcol;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.GiaVon = GiaVon;
        this.GiaLoi = GiaLoi;
    }

    public SanPhamDTO(SanPhamDTO other) {
        this.MaSanPham = other.getMaSanPham();
        this.MaLoaiSanPham = other.getMaLoaiSanPham();
        this.tenLoaiSanPham = other.getTenLoaiSanPham();
        this.AnhSanPhamURL = other.getAnhSanPhamURL();
        this.TenSanPham = other.getTenSanPham();
        this.NhaSanXuat = other.getNhaSanXuat();
        this.sanphamcol = other.getsanphamcol();
        this.SoLuong = other.getSoLuong();
        this.TrangThai = other.getTrangThai();
        this.GiaVon = other.getGiaVon();
        this.GiaLoi = other.getGiaLoi();
    }

    public int getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(int MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getMaLoaiSanPham() {
        return MaLoaiSanPham;
    }

    public void setMaLoaiSanPham(int MaLoaiSanPham) {
        this.MaLoaiSanPham = MaLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getAnhSanPhamURL() {
        return AnhSanPhamURL;
    }

    public void setAnhSanPhamURL(String AnhSanPhamURL) {
        this.AnhSanPhamURL = AnhSanPhamURL;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public String getNhaSanXuat() {
        return NhaSanXuat;
    }

    public void setNhaSanXuat(String NhaSanXuat) {
        this.NhaSanXuat = NhaSanXuat;
    }

    public String getsanphamcol() {
        return sanphamcol;
    }

    public void setsanphamcol(String sanphamcol) {
        this.sanphamcol = sanphamcol;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }
    

    public double getGiaVon() {
        return GiaVon;
    }

    public void setGiaVon(double GiaVon) {
        this.GiaVon = GiaVon;
    }

    public double getGiaLoi() {
        return GiaLoi;
    }

    public void setGiaLoi(double GiaLoi) {
        this.GiaLoi = GiaLoi;
    }
}