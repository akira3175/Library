package org.example.DTO;

public class Quyen {
    private int maQuyen;
    private String tenQuyen;
    private boolean isChecked;

    public Quyen() {
    }

    public Quyen(int maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public Quyen(int maQuyen, String tenQuyen, boolean isChecked) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.isChecked = isChecked;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    
    
}
