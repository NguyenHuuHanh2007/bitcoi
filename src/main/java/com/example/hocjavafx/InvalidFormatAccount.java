package com.example.hocjavafx;

public class InvalidFormatAccount extends Exception{
    public InvalidFormatAccount(String message){
        super("Tài khoản phải là Số điện thoại 10 số hoặc địa chỉ @gmail.com hợp lệ!");
    }
}
