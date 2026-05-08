package com.example.hocjavafx.model;
import com.example.hocjavafx.*;
public class Admin extends User{
    public Admin(int id, Account account, Password password) {
        super(id, account, password);
    }
    @Override
    public void hienThiQuyenHan() {
        System.out.println("ADMIN");
    }
}
