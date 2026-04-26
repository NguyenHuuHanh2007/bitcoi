package com.example.hocjavafx;

import java.time.LocalDateTime; // THƯ VIỆN MỚI

public class PhienDauGia {
    private int id;
    private String tenSanPham;
    private double giaKhoiDiem;
    private double giaHienTai;
    private String thoiGian;
    private String trangThai;
    private int soLuong;
    private String tinhTrang;
    private String duongDanAnh;

    // --- THUỘC TÍNH MỚI LƯU MỐC THỜI GIAN ĐỂ ĐẾM NGƯỢC CHUẨN ---
    private LocalDateTime thoiGianKetThuc;

    public PhienDauGia(int id, String tenSanPham, double giaKhoiDiem, double giaHienTai, String thoiGian, String trangThai, int soLuong, String tinhTrang, String duongDanAnh) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaKhoiDiem = giaKhoiDiem;
        this.giaHienTai = giaHienTai;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.duongDanAnh = duongDanAnh;
    }

    // --- GETTER & SETTER ---
    public LocalDateTime getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public double getGiaKhoiDiem() { return giaKhoiDiem; }
    public void setGiaKhoiDiem(double giaKhoiDiem) { this.giaKhoiDiem = giaKhoiDiem; }
    public double getGiaHienTai() { return giaHienTai; }
    public void setGiaHienTai(double giaHienTai) { this.giaHienTai = giaHienTai; }
    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
    public String getDuongDanAnh() { return duongDanAnh; }
    public void setDuongDanAnh(String duongDanAnh) { this.duongDanAnh = duongDanAnh; }
}