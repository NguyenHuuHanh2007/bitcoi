package com.example.hocjavafx;

public class SanPham {
    private int id;
    private String tenSanPham;
    private double giaBan;
    private int soLuong;

    public SanPham(int id, String tenSanPham, double giaBan, int soLuong) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    // CÁC HÀM GETTER (Lấy dữ liệu ra để hiển thị)
    public int getId() { return id; }
    public String getTenSanPham() { return tenSanPham; }
    public double getGiaBan() { return giaBan; }
    public int getSoLuong() { return soLuong; }

    // CÁC HÀM SETTER (Ghi đè dữ liệu mới khi bấm nút Sửa)
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}