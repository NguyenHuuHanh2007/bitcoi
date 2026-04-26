package com.example.hocjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Session {
    public static String tenNguoiDung = "User VIP";
    public static double soDu = 50000.0;

    // Thêm cuốn sổ ghi chép lịch sử giao dịch:
    public static ObservableList<String> lichSuGiaoDich = FXCollections.observableArrayList();
}