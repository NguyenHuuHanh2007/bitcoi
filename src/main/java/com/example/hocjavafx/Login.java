package com.example.hocjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    // ==========================================
    // 1. KHAI BÁO CÁC Ô NHẬP LIỆU (Đã khôi phục)
    // ==========================================
    @FXML
    private TextField nickname;

    @FXML
    private PasswordField password; // Dùng PasswordField để mã hóa dấu ***

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code chạy khi màn hình vừa load lên
    }

    public void taiKhoan(ActionEvent event) {
    }

    public void matKhau(ActionEvent event) {
    }

    // ==========================================
    // 2. XỬ LÝ ĐĂNG NHẬP CHÍNH (Đã ráp lại try-catch của bạn)
    // ==========================================
    @FXML
    public void dangNhap(ActionEvent event) {
        try {
            // Lấy dữ liệu người dùng nhập
            String usernameInput = nickname.getText();
            String passwordInput = password.getText();

            // Bước 1: Kiểm tra rỗng
            if (usernameInput == null || usernameInput.isEmpty() ||
                    passwordInput == null || passwordInput.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            }

            // Bước 2: Kiểm tra định dạng (Dùng class bạn đã viết)
            Account acc = new Account(usernameInput);
            Password pass = new Password(passwordInput);

            // Bước 3: Nếu dữ liệu chuẩn, cho phép chuyển trang
            System.out.println("Đăng nhập thành công! Đang chuyển trang...");
            chuyenTrang(event, "/com/example/hocjavafx/fxml/trangchu.fxml");

        } catch (InvalidFormatAccount | InvalidFormatPassword e) {
            // Lỗi do định dạng không khớp (Từ class Account/Password)
            showErrorDialog("Sai định dạng: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Lỗi do bỏ trống
            showErrorDialog(e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Có lỗi xảy ra trong quá trình chuyển trang: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void dangNhapAdmin(ActionEvent event) {
        try {
            String usernameInput = nickname.getText();
            String passwordInput = password.getText();

            if (usernameInput == null || usernameInput.isEmpty() ||
                    passwordInput == null || passwordInput.isEmpty()) {
                throw new IllegalArgumentException("Admin vui lòng nhập đầy đủ thông tin!");
            }

            // Tái sử dụng kiểm tra định dạng
            Account acc = new Account(usernameInput);
            Password pass = new Password(passwordInput);

            // TODO: Ở đây sau này sẽ gọi Database để lấy Role. Tạm thời cho qua trang chủ.
            System.out.println("Đăng nhập quyền Admin...");
            chuyenTrang(event, "/com/example/hocjavafx/fxml/trangchu.fxml");

        } catch (InvalidFormatAccount | InvalidFormatPassword e) {
            showErrorDialog("Lỗi định dạng Admin: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Lỗi: " + e.getMessage());
        }
    }

    public void dangKy(ActionEvent event) throws IOException {
        chuyenTrang(event, "/com/example/hocjavafx/fxml/dangki.fxml");
    }

    public void quenMatKhau(ActionEvent event) throws IOException {
    }

    // ==========================================
    // 3. CÁC HÀM TIỆN ÍCH DÙNG CHUNG
    // ==========================================
    private void chuyenTrang(ActionEvent event, String duongDanFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFXML));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();

        // Ép giao diện mới phải bành trướng to bằng Căn phòng hiện tại TRƯỚC KHI xuất hiện
        if (root instanceof Region) {
            Region newRegion = (Region) root;
            newRegion.setPrefSize(scene.getWidth(), scene.getHeight());
        }
        scene.setRoot(root);
    }

    // Khôi phục hàm báo lỗi Popup
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Thông báo lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}