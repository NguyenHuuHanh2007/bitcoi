package com.example.hocjavafx;

public class Account {
    private String account;

    public Account(String account) throws InvalidFormatAccount {
        this.account = account;
        boolean isGmail = account.endsWith("@gmail.com") && account.length() > 10;
        boolean isPhone = false;

        // Logic kiểm tra SĐT bằng vòng lặp của bạn
        if (account.length() == 10 && account.startsWith("0")) {
            isPhone = true;
            for (int i = 0; i < account.length(); i++) {
                char c = account.charAt(i);
                if (!Character.isDigit(c)) {
                    isPhone = false;
                    break;
                }
            }
        }
        if (!isGmail && !isPhone) {
            throw new InvalidFormatAccount("Tài khoản phải là Số điện thoại 10 số hoặc địa chỉ @gmail.com hợp lệ!");
        }
    }

    public String getAccount() {
        return account;
    }
}
