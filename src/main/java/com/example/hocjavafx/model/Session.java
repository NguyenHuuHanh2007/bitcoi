package com.example.hocjavafx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Session {
    // 1. CHUẨN SINGLETON: Khởi tạo một phiên duy nhất
    private static Session instance;

    // 2. CHUYỂN TẤT CẢ VỀ PRIVATE (Tính đóng gói)
    // Tạm thời giữ nguyên các biến của Hiếu nhưng đóng gói nó lại
    private String tenNguoiDung;
    private double soDu;
    private ObservableList<String> lichSuGiaoDich;

    // TODO: Sau này bạn nên thay 2 biến tenNguoiDung và soDu bằng class User của bạn:
    // private User currentUser;

    // Constructor private: Ngăn không cho tạo Session vô tội vạ bằng từ khóa 'new'
    private Session() {
        this.tenNguoiDung = "User VIP"; // Dữ liệu giả lập của Hiếu
        this.soDu = 50000.0;
        this.lichSuGiaoDich = FXCollections.observableArrayList();
    }

    // Cổng duy nhất để lấy Session ra dùng
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // 3. CÁC HÀM GETTER / SETTER ĐỂ GIAO TIẾP (Kiểm soát dữ liệu)
    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public double getSoDu() {
        return soDu;
    }

    public void setSoDu(double soDu) {
        this.soDu = soDu;
    }

    public ObservableList<String> getLichSuGiaoDich() {
        return lichSuGiaoDich;
    }

    // Hàm tiện ích để thêm lịch sử an toàn
    public void themLichSu(String giaoDich) {
        this.lichSuGiaoDich.add(giaoDich);
    }

    // Hàm xóa dữ liệu khi Đăng xuất
    public void clearSession() {
        this.tenNguoiDung = "";
        this.soDu = 0;
        this.lichSuGiaoDich.clear();
    }
}