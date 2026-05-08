package com.example.hocjavafx;

public class Password { private String password;
    public Password(String password) throws InvalidFormatPassword{
        this.password=password;
        if (password.length()<8){
            throw new InvalidFormatPassword("Mật khẩu phải dài hơn 8 kí tự");
        }
    }

    public String getPassword() {
        return password;
    }
}
