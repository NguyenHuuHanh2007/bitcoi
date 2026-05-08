package com.example.hocjavafx.model;
import com.example.hocjavafx.*;
public class Seller extends User{
    public Seller(int id, Account account, Password password) {
        super(id, account, password);
    }
    @Override
    public void hienThiQuyenHan() {
        System.out.println("Seller");
    }
}
