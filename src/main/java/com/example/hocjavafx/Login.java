package com.example.hocjavafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

public class Login implements Initializable {
    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void dangNhap(ActionEvent event) {
        try {
            String usernameInput = nickname.getText();
            String passwordInput = password.getText();

            if (usernameInput == null || usernameInput.isEmpty() || passwordInput == null || passwordInput.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            }

            Account validAccount = new Account(usernameInput);
            Password validPassword = new Password(passwordInput);

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/hocjavafx/fxml/trangchu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.show();

        } catch (InvalidFormatAccount e) {
            showErrorDialog("Lỗi định dạng: " + e.getMessage());
        } catch (InvalidFormatPassword e) {
            showErrorDialog("Lỗi mật khẩu: Mật khẩu phải có ít nhất 8 ký tự!");
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Có lỗi xảy ra: " + e.getMessage());
        }
    }
    public void dangKy(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/example/hocjavafx/fxml/dangki.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void quenMatKhau(ActionEvent event) throws IOException {
    }

    public void dangNhapAdmin(ActionEvent event) throws IOException {
    }

    // 5. HÀM TIỆN ÍCH ĐỂ HIỂN THỊ CỬA SỔ BÁO LỖI
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi Đăng Nhập");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}