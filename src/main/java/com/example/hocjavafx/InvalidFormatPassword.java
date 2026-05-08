package com.example.hocjavafx;

public class InvalidFormatPassword extends Exception{
    public InvalidFormatPassword(String message){
        super("Mật khẩu phải dài hơn 8 kí tự");
    }
}
