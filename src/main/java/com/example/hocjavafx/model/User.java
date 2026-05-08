package com.example.hocjavafx.model;

import com.example.hocjavafx.Account;
import com.example.hocjavafx.Password;

public abstract class User extends Entity {
    protected Account account;
    protected Password password;

    public User(int id, Account account, Password password) {
        super(id); // Gọi constructor của Entity
        this.account = account;
        this.password = password;
    }
    public Account getAccount() { return account; }
    public Password getPassword() { return password; }
    public abstract void hienThiQuyenHan();
}
