package com.example.hocjavafx.model;
import com.example.hocjavafx.*;
public class Bidder extends User{
    public Bidder(int id, Account account, Password password) {
        super(id, account, password);
    }
    @Override
    public void hienThiQuyenHan() {
        System.out.println("BIDDER");
    }
}
