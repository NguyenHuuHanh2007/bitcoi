package com.example.hocjavafx.model;
// Kế thừa lớp Entity của bạn
public class SanPham extends Entity {
    private String tenSanPham;
    private double giaBan;
    private int soLuong;

    public SanPham(int id, String tenSanPham, double giaBan, int soLuong) {
        super(id); // Gọi hàm khởi tạo của Entity cha để gán ID
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    // Các hàm Getter (Không cần viết hàm getId() nữa vì class cha Entity đã có sẵn)
    public String getTenSanPham() { return tenSanPham; }
    public double getGiaBan() { return giaBan; }
    public int getSoLuong() { return soLuong; }

    // Các hàm Setter
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}